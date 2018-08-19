package com.activitiSSM.utils;

/**
 * 请假单审批状态枚举类，0：初始录入；1，审批中；2.审批通过
 */
public enum  LeaveStatusEnum {
    START(0),EXAMINE(1),END(2);

    private int value = 0;

    LeaveStatusEnum(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
