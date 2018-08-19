package com.activitiSSM.controller;

import com.activitiSSM.bean.LeaveBillBean;
import com.activitiSSM.service.ILeavaBillService;
import com.activitiSSM.utils.LeaveStatusEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:guang yong
 * Description:请假业务controller
 * @Date:Created in 10:19 2018/8/14
 * @Modified By:
 */
@Controller
public class LeaveBillController {

    @Autowired
    private ILeavaBillService leavaBillService;

    /**
     * 获取请假列表
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping(value = "/getLeave")
    @ResponseBody
    public Map getAllLeave(String pageNum, String pageSize,HttpServletRequest request){
        int num = pageNum == null?1:Integer.parseInt(pageNum);
        int size = pageSize == null?10:Integer.parseInt(pageSize);
        PageInfo pageInfo =leavaBillService.getAllLeave(num,size,request.getSession().getAttribute("userId")==null?"":request.getSession().getAttribute("userId").toString());
        Map map = new HashMap();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

    /**
     * 保存&修改请假信息
     * @param request
     * @param type
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate")
    @ResponseBody
    public boolean saveOrUpdateLeave(HttpServletRequest request,String type){
        LeaveBillBean bean = null;
        if("add".equals(type)){
            bean = new LeaveBillBean();
            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bean.setLeaveDate(dateFormat.format(date));
            bean.setUserId(request.getSession().getAttribute("userId").toString());
            bean.setDays(Integer.parseInt(request.getParameter("days")));
            bean.setReason(request.getParameter("reason"));
            bean.setRemark(request.getParameter("remark"));
            bean.setStatus(LeaveStatusEnum.START.value());
           if(leavaBillService.addLeave(bean) > 0){
               return true;
           }
        }else if("update".equals(type)){
            bean = leavaBillService.getLeaveById(request.getParameter("leaveId"));
            if(bean == null){
                return false;
            }
            bean.setDays(Integer.parseInt(request.getParameter("days")));
            bean.setReason(request.getParameter("reason"));
            bean.setRemark(request.getParameter("remark"));
            if(leavaBillService.updateLeave(bean) > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 删除请假单
     * @param id
     * @return
     */
    @RequestMapping(value = "deleteLeave")
    @ResponseBody
    public int deleteLeave(String id){
        return leavaBillService.deleteLeave(id);
    }

    /**
     * 根据id获取请假详情
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/getLeaveById")
    @ResponseBody
    public Map getLeaveById(String id,String type){
        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("leave",leavaBillService.getLeaveById(id));
        return map;
    }

    /**
     * 启动流程实例
     * @return
     */
    @RequestMapping(value = "/applyLeave")
    @ResponseBody
    public boolean applyLeave(String leaveId,HttpServletRequest request){
        //启动流程时，第一个节点为提交申请，审批人为申请人自己
        String handleUser = request.getSession().getAttribute("userName").toString();
        return leavaBillService.applyLeave(leaveId,handleUser);
    }

    /**
     * 办理任务
     * @return
     */
    @RequestMapping(value = "/handleLeave")
    @ResponseBody
    public boolean handleLeave(String taskId,String leaveId,String msg,HttpServletRequest request){
        String userName =request.getSession().getAttribute("userName").toString();
        return leavaBillService.handleLeave(taskId,msg,userName,leaveId);
    }

    /**
     * 获取当前登录人的办理任务
     * @return
     */
    @RequestMapping(value = "/leaveTask")
    @ResponseBody
    public Map leaveTask(String pageNum, String pageSize,HttpServletRequest request){
        int num = pageNum == null?1:Integer.parseInt(pageNum);
        int size = pageSize == null?10:Integer.parseInt(pageSize);
        String user = request.getSession().getAttribute("userName").toString();
        return leavaBillService.leaveTask(num,size,user);
    }

    /**
     * 获取任务的详细信息
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/getTask")
    @ResponseBody
    public Map getTask(String taskId){
        return leavaBillService.getTask(taskId);
    }

    /**
     * 获取批注信息
     * @return
     */
    @RequestMapping(value = "/getAnnotation")
    @ResponseBody
    public Map getAnnotation(String taskId){
        return leavaBillService.getAnnotation(taskId);
    }

    /**
     * 查询审核记录
     * @param leaveId
     * @return
     */
    @RequestMapping(value = "/getRecord")
    @ResponseBody
    public Map getRecord(String leaveId){
        return leavaBillService.getRecord(leaveId);
    }
}
