package com.activitiSSM.dao;

import com.activitiSSM.bean.LeaveBillBean;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:20 2018/8/14
 * @Modified By:
 */
public interface LeaveBillDao {

    Page getAllLeave();

    int deleteLeava(@Param("id") String id);

    int addLeava(LeaveBillBean leava);
}
