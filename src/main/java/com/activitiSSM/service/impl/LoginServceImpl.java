package com.activitiSSM.service.impl;

import com.activitiSSM.bean.UserBean;
import com.activitiSSM.dao.UserDao;
import com.activitiSSM.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:02 2018/8/16
 * @Modified By:
 */
@Service
public class LoginServceImpl implements ILoginService{

    @Autowired
    private UserDao userDao;
    @Override
    public UserBean getUserByNameAndPwd(String userName, String pwd) {
        return userDao.getUserByNameAndPwd(userName,pwd);
    }
}
