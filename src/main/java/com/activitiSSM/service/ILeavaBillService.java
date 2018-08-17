package com.activitiSSM.service;

import com.activitiSSM.bean.LeaveBillBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:50 2018/8/17
 * @Modified By:
 */
public interface ILeavaBillService {

    PageInfo getAllLeave(int pageNumber, int pageSize);
}
