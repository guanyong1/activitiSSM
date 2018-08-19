package com.activitiSSM.dao;

import com.activitiSSM.bean.LeaveBillBean;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:20 2018/8/14
 * @Modified By:
 */
public interface LeaveBillDao {

    Page getAllLeave(@Param("userId") String userId);

    LeaveBillBean getLeaveById(@Param("id") String id);

    int deleteLeave(@Param("id") String id);

    int addLeave(LeaveBillBean leave);

    int updateLeave(LeaveBillBean leave);

}
