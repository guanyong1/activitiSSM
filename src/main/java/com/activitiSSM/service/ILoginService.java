package com.activitiSSM.service;

import com.activitiSSM.bean.UserBean;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:00 2018/8/16
 * @Modified By:
 */
public interface ILoginService {

    public UserBean getUserByNameAndPwd(String userName,String pwd);
}
