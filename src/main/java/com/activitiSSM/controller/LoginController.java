package com.activitiSSM.controller;

import com.activitiSSM.bean.UserBean;
import com.activitiSSM.service.ILoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 9:29 2018/8/16
 * @Modified By:
 */
@Controller
public class LoginController {

    @Autowired
    private ILoginService loginService;

    /**
     * 登录
     * @param userName
     * @param pwd
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public boolean login(String userName, String pwd, HttpServletRequest request){
        if(StringUtils.isBlank(userName)){
            return false;
        }
        if(StringUtils.isBlank(pwd)){
            return false;
        }
        UserBean userBean = loginService.getUserByNameAndPwd(userName,pwd);
        if (userBean == null){
            return false;
        }
        request.getSession().setAttribute("userName",userBean.getUserName());
        return true;
    }

    @RequestMapping(value = "/getUser",produces = "text/html; charset=utf-8")
    @ResponseBody
    public String getUser(HttpServletRequest request){
        return request.getSession().getAttribute("userName") == null?"失败":request.getSession().getAttribute("userName").toString();
    }
}
