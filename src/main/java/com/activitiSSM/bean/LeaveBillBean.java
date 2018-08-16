package com.activitiSSM.bean;

import lombok.Data;

/**
 * @Author:guang yong
 * Description:请假实体类
 * @Date:Created in 17:07 2018/8/15
 * @Modified By:
 */
@Data
public class LeaveBillBean {
    private String id;
    /**
     * 请假人
     */
    private String userId;

    /**
     * 请假天数
     */
    private int days;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请假时间
     */
    private String leaveDate;

    /**
     * 请假状态
     */
    private int status;
}
