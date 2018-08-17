package com.activitiSSM.controller;

import com.activitiSSM.bean.LeaveBillBean;
import com.activitiSSM.service.ILeavaBillService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "/getLeave")
    @ResponseBody
    public Map getAllLeave(String pageNum, String pageSize){
        int num = pageNum == null?1:Integer.parseInt(pageNum);
        int size = pageSize == null?10:Integer.parseInt(pageSize);
        PageInfo pageInfo =leavaBillService.getAllLeave(num,size);
        Map map = new HashMap();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }
}
