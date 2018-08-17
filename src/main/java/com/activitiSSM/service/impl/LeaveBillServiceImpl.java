package com.activitiSSM.service.impl;

import com.activitiSSM.bean.LeaveBillBean;
import com.activitiSSM.dao.LeaveBillDao;
import com.activitiSSM.service.ILeavaBillService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:51 2018/8/17
 * @Modified By:
 */
@Service
public class LeaveBillServiceImpl implements ILeavaBillService{
    @Autowired
    private LeaveBillDao leaveBillDao;
    @Override
    public PageInfo getAllLeave(int pageNumber, int pageSize) {
        Page page= PageHelper.startPage(pageNumber,pageSize);
        List<LeaveBillBean> list = leaveBillDao.getAllLeave();
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }
}
