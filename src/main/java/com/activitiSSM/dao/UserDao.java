package com.activitiSSM.dao;

import com.activitiSSM.bean.UserBean;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 9:44 2018/8/16
 * @Modified By:
 */
public interface UserDao {

    /**
     * 根据用户名，密码查询用户
     * @param userName
     * @param pwd
     * @return
     */
    public UserBean getUserByNameAndPwd(@Param("userName") String userName,@Param("pwd")String pwd);
}
