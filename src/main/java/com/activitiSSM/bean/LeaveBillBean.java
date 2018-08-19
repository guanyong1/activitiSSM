package com.activitiSSM.bean;

import com.activitiSSM.utils.LeaveStatusEnum;
import lombok.Data;

import java.util.UUID;

/**
 * @Author:guang yong
 * Description:请假实体类
 * @Date:Created in 17:07 2018/8/15
 * @Modified By:
 */
@Data
public class LeaveBillBean {
    private String id = UUID.randomUUID().toString();
    /**
     * 请假人ID
     */
    private String userId;

    /**
     * 请假人姓名
     */
    private String userName;

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
    private Integer status;

    private String statusString;

    public String getStatusString() {
        switch (status){
            case 0:
                statusString = "初始录入";
                break;
            case 1:
                statusString = "审批中";
                break;
            case 2:
                statusString = "审核通过";
                break;
            default:
                statusString ="";
                break;
        }
        return statusString;
    }
}
