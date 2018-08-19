package com.activitiSSM.service.impl;

import com.activitiSSM.bean.LeaveBillBean;
import com.activitiSSM.dao.LeaveBillDao;
import com.activitiSSM.service.ILeavaBillService;
import com.activitiSSM.utils.LeaveStatusEnum;
import com.activitiSSM.utils.ProcessKeySystem;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:51 2018/8/17
 * @Modified By:
 */
@Service
public class LeaveBillServiceImpl implements ILeavaBillService{
    @Autowired
    private LeaveBillDao leaveBillDao;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;
    @Override
    public PageInfo getAllLeave(int pageNumber, int pageSize, String userId) {
        Page page= PageHelper.startPage(pageNumber,pageSize);
        List<LeaveBillBean> list = leaveBillDao.getAllLeave(userId);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }

    @Override
    public LeaveBillBean getLeaveById(String id) {
        return leaveBillDao.getLeaveById(id);
    }

    @Override
    public int addLeave(LeaveBillBean leave) {
        return leaveBillDao.addLeave(leave);
    }

    @Override
    public int updateLeave(LeaveBillBean leave) {
        return leaveBillDao.updateLeave(leave);
    }

    @Override
    public int deleteLeave(String id) {
        return leaveBillDao.deleteLeave(id);
    }

    /**
     * 启动请假流程实例
     * @param leaveId
     * @return
     */
    @Override
    public boolean applyLeave(String leaveId,String handleUser) {
        try{
            LeaveBillBean billBean = leaveBillDao.getLeaveById(leaveId);
            //关联业务数据
            String business = ProcessKeySystem.LEAVE+"."+billBean.getId();
            /**使用流程变量设置下一个任务的办理人*/
            Map<String,Object> map = new HashMap<>();
            map.put("userName",handleUser);
            //使用流程定义的key启动流程实例
            runtimeService.startProcessInstanceByKey(ProcessKeySystem.LEAVE,business,map);
            billBean.setStatus(LeaveStatusEnum.EXAMINE.value());
            //更改请假单状态
            leaveBillDao.updateLeave(billBean);
            return true;
        }catch (Exception e){}
        return false;
    }

    /**
     * 办理任务
     * @param taskId
     * @param msg
     * @return
     */
    @Override
    public boolean handleLeave(String taskId,String msg,String userName,String leaveId) {
        try{
            /**添加批注信息*/
            Task task = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult();
            //使用task对象获取流程实例ID
            String processInstanceId = task.getProcessInstanceId();
            //添加批注人
            Authentication.setAuthenticatedUserId(userName);
            taskService.addComment(taskId,processInstanceId,msg);
            /**办理任务*/
            taskService.complete(taskId);
            //判断流程是否结束
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                            .processInstanceId(processInstanceId)
                            .singleResult();
            //流程结束，更新请假单状态
            if(processInstance == null){
                LeaveBillBean bean = leaveBillDao.getLeaveById(leaveId);
                bean.setStatus(LeaveStatusEnum.END.value());
                leaveBillDao.updateLeave(bean);
            }
            return true;
        }catch (Exception e){}
        return false;
    }

    /**
     * 获取当前办理人的任务
     * @param pageNumber
     * @param pageSize
     * @param user
     * @return
     */
    @Override
    public Map leaveTask(int pageNumber, int pageSize, String user) {
        Map<String,Object> map =new HashMap<>();
        List<Task> list = taskService.createTaskQuery()
                                        .taskAssignee(user)//指定个人任务查询
                                        .orderByTaskCreateTime().asc()
                                        .listPage((pageNumber -1)*pageSize,pageNumber * pageSize);
        List dataList = new ArrayList();
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(list != null && list.size()> 0){
            for (Task t:list) {
                Map<String,String> dataMap = new HashMap<>();
                dataMap.put("id",t.getId());
                dataMap.put("name",t.getName());
                dataMap.put("time",bf.format(t.getCreateTime()));
                dataMap.put("user",t.getAssignee());
                dataList.add(dataMap);
            }
        }
        Long total = taskService.createTaskQuery().taskAssignee(user).count();
        map.put("rows",dataList);
        map.put("total",total);
        return map;
    }

    /**
     * 获取办理任务的详情
     * @param taskId
     * @return
     */
    @Override
    public Map getTask(String taskId) {
        Task task = taskService.createTaskQuery()
                                .taskId(taskId)
                                .singleResult();
        //使用task对象获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //使用流程实例ID获取流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                                                            .processInstanceId(processInstanceId)
                                                            .singleResult();
        //使用流程实例ID对象获取business_key关联业务数据
        String leaveId = processInstance.getBusinessKey().split("\\.")[1];
        //获取请假单详情
        LeaveBillBean bean = leaveBillDao.getLeaveById(leaveId);
        Map<String,Object> map = new HashMap<>();
        map.put("leave",bean);
        return map;
    }

    /**
     * 获取批注信息
     * @return
     */
    @Override
    public Map getAnnotation(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        //使用task对象获取流程实例ID
        String processInstanceId = task.getProcessInstanceId();
        //获取批注信息（没有分页方法）
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",list.size());
        return map;
    }

    /**
     * 查询审核记录
     * @param leaveId
     * @return
     */
    @Override
    public Map getRecord(String leaveId) {
        String businessKey = ProcessKeySystem.LEAVE+"."+leaveId;
        HistoricProcessInstance historicProcessInstance= historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .singleResult();
        String processInstanceId = historicProcessInstance.getId();
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",list.size());
        return map;
    }
}
