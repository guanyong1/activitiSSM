package com.activitiSSM.service;

import com.activitiSSM.bean.LeaveBillBean;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:50 2018/8/17
 * @Modified By:
 */
public interface ILeavaBillService {

    PageInfo getAllLeave(int pageNumber, int pageSize,String userId);

    LeaveBillBean getLeaveById(String id);

    int addLeave(LeaveBillBean leave);

    int updateLeave(LeaveBillBean leave);

    int deleteLeave(String id);

    /**
     * 启动请假流程实例
     * @param leaveId
     * @param handleUser
     * @return
     */
    boolean applyLeave(String leaveId,String handleUser);

    /**
     * 办理任务
     * @param taskId
     * @param msg
     * @return
     */
    boolean handleLeave(String taskId,String msg,String userName,String leaveId);

    /**
     * 获取当前登录人的办理任务
     * @param pageNumber
     * @param pageSize
     * @param user
     * @return
     */
    Map leaveTask(int pageNumber, int pageSize,String user);

    /**
     * 获取办理任务的详情
     * @param taskId
     * @return
     */
    Map getTask(String taskId);

    /**
     * 获取批注信息
     * @return
     */
    Map getAnnotation(String taskId);

    /**
     * 查询审核记录
     * @param leaveId
     * @return
     */
    Map getRecord(String leaveId);
}
