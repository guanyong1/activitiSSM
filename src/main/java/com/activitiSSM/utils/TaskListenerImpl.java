package com.activitiSSM.utils;

import com.activitiSSM.bean.UserBean;
import com.activitiSSM.dao.UserDao;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author:guang yong
 * Description:使用类动态设置任务办理人
 * @Date:Created in 11:02 2018/8/15
 * @Modified By:
 */
@SuppressWarnings("serial")
@Component
public class TaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //指定个人任务的办理人，也可以指定组任务的办理人
        //个人任务：通过类去查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee()的方法指定任务的办理人
        //通过ContextLoader获取bean
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        UserDao userDao =(UserDao) wac.getBean("userDao");
        //从session中获取当前用户
        HttpSession session= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        UserBean user = userDao.getLeader(session.getAttribute("userId").toString());
        //将当前用户的领导设置到办理人中
        delegateTask.setAssignee(user.getUserName());
    }
}
