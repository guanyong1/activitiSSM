package com.activitiSSM.controller;

import com.activitiSSM.service.IWorkFlowservice;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @Author:guang yong
 * Description:工作流controller
 * @Date:Created in 10:48 2018/8/14
 * @Modified By:
 */
@Controller
public class WorkFlowController {

    @Autowired
    private IWorkFlowservice workFlowservice;

    /**
     * 根本bpmn文件部署流程
     * @param inputCandidatesFile
     * @param processName
     * @return
     */
    @RequestMapping(value ="/uploadBPMN")
    @ResponseBody
    public boolean uploadBPMN(MultipartFile inputCandidatesFile,String processName){
        String fileName =inputCandidatesFile.getOriginalFilename();
        if(!"zip".equals(fileName.split("\\.")[1])){
            return false;
        }
        CommonsMultipartFile cFile = (CommonsMultipartFile) inputCandidatesFile;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();;
        InputStream inputStream = null;
        try {
            inputStream = fileItem.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream == null){
            return false;
        }
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        return workFlowservice.deploy(zipInputStream,processName);
    }

    /**
     * 获取部署信息列表
     * @return
     */
    @RequestMapping(value = "/getDeployPage")
    @ResponseBody
    public Map getDeployPage(String pageNum, String pageSize){
        int num = pageNum == null?1:Integer.parseInt(pageNum);
        int size = pageSize == null?10:Integer.parseInt(pageSize);
        Map map = workFlowservice.getDeployPage(num,size);
        return map;
    }

    /**
     * 删除部署
     * @return
     */
    @RequestMapping(value = "/deleteDeploy")
    @ResponseBody
    public boolean deleteDeploy(String deployId){
        return workFlowservice.deleteDeploy(deployId);
    }

    /**
     * 获取流程定义信息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getProcessDeployPage")
    @ResponseBody
    public Map getProcessDeployPage(String pageNum, String pageSize){
        int num = pageNum == null?1:Integer.parseInt(pageNum);
        int size = pageSize == null?10:Integer.parseInt(pageSize);
        Map map = workFlowservice.getProcessDeployPage(num,size);
        return map;
    }

    /**
     * 获取流程图
     * @param deployId
     * @return
     */
    @RequestMapping(value = "/getProcessPng")
    @ResponseBody
    public byte[] getProcessPng(String deployId){
        return workFlowservice.getProcessPng(deployId);
    }
}
