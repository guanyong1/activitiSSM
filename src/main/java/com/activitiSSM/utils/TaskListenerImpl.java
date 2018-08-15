package com.activitiSSM.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 11:02 2018/8/15
 * @Modified By:
 */
@SuppressWarnings("unused")
public class TaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //指定个人任务的办理人，也可以指定组任务的办理人
        //个人任务：通过类去查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee()的方法指定任务的办理人
        delegateTask.setAssignee("蛮王");

    }
}
