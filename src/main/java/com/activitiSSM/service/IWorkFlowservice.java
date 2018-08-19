package com.activitiSSM.service;

import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @Author:guang yong
 * Description:工作流业务接口
 * @Date:Created in 10:20 2018/8/14
 * @Modified By:
 */
public interface IWorkFlowservice {

    /**
     * 部署流程
     * @param file
     * @param name
     * @return
     */
    boolean deploy(ZipInputStream file, String name);

    /**
     * 获取流程部署信息列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map getDeployPage(int pageNumber, int pageSize);

    /**
     * 根据id删除流程定义
     * @param deployId
     * @return
     */
    boolean deleteDeploy(String deployId);

    /**
     * 获取流程定义信息列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Map getProcessDeployPage(int pageNumber, int pageSize);

    /**
     * 获取流程图
     * @param deployId
     * @return
     */
    byte[] getProcessPng(String deployId);

}
