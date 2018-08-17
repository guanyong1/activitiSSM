package com.activitiSSM.service.impl;

import com.activitiSSM.service.IWorkFlowservice;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @Author:guang yong
 * Description:工作流service实现类
 * @Date:Created in 10:23 2018/8/14
 * @Modified By:
 */
@Service
public class WorkFlowServiceImpl implements IWorkFlowservice{

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
    @Override
    public boolean deploy(ZipInputStream file,String name) {
        try{
            Deployment deployment = repositoryService.createDeployment()
                    .name(name)
                    .addZipInputStream(file)
                    .deploy();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 获取流程部署信息列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Map getDeployPage(int pageNumber, int pageSize) {
        Map map = new HashMap();
        long total= repositoryService.createDeploymentQuery().count();
        List<Deployment> list =repositoryService.createDeploymentQuery()
                .orderByDeploymenTime().desc()
                .listPage((pageNumber -1)*pageSize,pageNumber * pageSize);
        List dataList = new ArrayList();
        DateFormat bf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Deployment d:list) {
            Map dataMap = new HashMap();
            dataMap.put("id",d.getId());
            dataMap.put("name",d.getName());
            dataMap.put("time",bf.format(d.getDeploymentTime()));
            dataList.add(dataMap);
        }
        map.put("rows",dataList);
        map.put("total",total);
//        map.put("pageNumber",pageNumber);
//        map.put("pageSize",pageSize);
        return map;
    }

    /**
     * 删除部署
     * @param deployId
     * @return
     */
    @Override
    public boolean deleteDeploy(String deployId) {
        try{
            repositoryService.deleteDeployment(deployId,true);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 获取流程定义信息列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Map getProcessDeployPage(int pageNumber, int pageSize) {
        Map map = new HashMap();
        long total = repositoryService.createProcessDefinitionQuery().count();
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                                        .orderByProcessDefinitionVersion().desc()
                                        .listPage((pageNumber -1)*pageSize,pageNumber * pageSize);
        List dataList = new ArrayList();
        for (ProcessDefinition p:list) {
            Map dataMap = new HashMap();
            dataMap.put("id",p.getId());
            dataMap.put("name",p.getName());
            dataMap.put("key",p.getKey());
            dataMap.put("version",p.getVersion());
            dataMap.put("bpmn",p.getResourceName());
            dataMap.put("png",p.getDiagramResourceName());
            dataMap.put("deploye",p.getDeploymentId());
            dataList.add(dataMap);
        }
        map.put("rows",dataList);
        map.put("total",total);
//        map.put("pageNumber",pageNumber);
//        map.put("pageSize",pageSize);
        return map;
    }

    /**
     * 获取流程图
     * @param deployId
     * @return
     */
    @Override
    public byte[] getProcessPng(String deployId) {
        //获取图片资源名称
        List<String> list =repositoryService.getDeploymentResourceNames(deployId);
        //定义图片资源的名称
        String resourceName ="";
        if(list != null && list.size()>0){
            for (String name:list) {
                if(name.indexOf(".png") >= 0){
                    resourceName = name;
                }
            }
        }
        InputStream in = repositoryService.getResourceAsStream(deployId,resourceName);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        try {
            while (-1 != (n = in.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }


}
