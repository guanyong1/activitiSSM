package com.activitiSSM.service.impl;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * @Author:guang yong
 * Description:工作流service实现类
 * @Date:Created in 10:23 2018/8/14
 * @Modified By:
 */
@Service
public class workFlowServiceImpl {

    /**
     * 仓库service,用于管理流程仓库，例如：部署，删除，读取流程
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 运行时service，用于处理正在运行的流程实例，任务等
     */
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 任务service，用于处理任务
     */
    @Autowired
    private TaskService taskService;

    /**
     * 历史service，用于查询所有历史信息
     */
    @Autowired
    private HistoryService historyService;

    /**
     * 根据bpmn文件部署流程文件
     * @return
     */
    public boolean deploy(File file,String name){
        try{
            InputStream inputStream = null;
            Deployment deployment = repositoryService.createDeployment()
                                                        .name(name)
                                                        .addInputStream(file.getName(),inputStream)
                                                        .deploy();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 获取部署信息
     */
    public void getDeployInfo(){

    }
}
