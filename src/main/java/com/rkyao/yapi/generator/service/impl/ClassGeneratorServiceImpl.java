package com.rkyao.yapi.generator.service.impl;

import com.rkyao.yapi.generator.constant.GeneratorConstant;
import com.rkyao.yapi.generator.entity.template.ApiInfo;
import com.rkyao.yapi.generator.entity.template.EntityInfo;
import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import com.rkyao.yapi.generator.service.ClassGeneratorService;
import com.rkyao.yapi.generator.util.FileUtil;
import com.rkyao.yapi.generator.util.FreemarkerGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;

/**
 * 生成接口类文件
 *
 * @author yaorongke
 * @date 2022/5/22
 */
@Service
public class ClassGeneratorServiceImpl implements ClassGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(ClassGeneratorServiceImpl.class);
    @Value("${yapi.generator.class.defaultName:My}")
    private String classDefaultName;
    @Autowired
    private FreemarkerGenerator freemarkerGenerator;

    @Override
    public void createClassFile(List<ServiceInfo> serviceInfoList) {
        if (CollectionUtils.isEmpty(serviceInfoList)) {
            logger.error("接口信息不能为空!");
            return;
        }
        // 先删除
        FileUtil.delFile(new File(GeneratorConstant.OUTPUT));
        for (ServiceInfo serviceInfo : serviceInfoList) {
            String basePath = serviceInfo.getBasePackage().replaceAll("\\.", "\\\\");
            // 输出目录初始化
            classDefaultName=classDefaultName.substring(0,1).toLowerCase()+classDefaultName.substring(1);
            initDirectory(basePath,classDefaultName);

            // 生成controller、service、impl类文件
            String controllerPath = String.format(GeneratorConstant.CONTROLLER_PATH, basePath) + GeneratorConstant.SEPARATOR + serviceInfo.getServiceName() + "Controller.java";
            String feignPath = String.format(GeneratorConstant.FEIGN_PATH, basePath) + GeneratorConstant.SEPARATOR + serviceInfo.getServiceName() + "Client.java";
            String servicePath = String.format(GeneratorConstant.SERVICE_PATH, basePath) + GeneratorConstant.SEPARATOR + serviceInfo.getServiceName() + "Service.java";
            String implPath = String.format(GeneratorConstant.IMPL_PATH, basePath) + GeneratorConstant.SEPARATOR + serviceInfo.getServiceName() + "ServiceImpl.java";

            freemarkerGenerator.createFile(GeneratorConstant.CONTROLLER_FTL, controllerPath, serviceInfo);
            freemarkerGenerator.createFile(GeneratorConstant.FEIGN_FTL, feignPath, serviceInfo);
            freemarkerGenerator.createFile(GeneratorConstant.SERVICE_FTL, servicePath, serviceInfo);
            freemarkerGenerator.createFile(GeneratorConstant.IMPL_FTL, implPath, serviceInfo);

            // 生成entity类文件
            for (ApiInfo apiInfo : serviceInfo.getApiList()) {
                for (EntityInfo entityInfo : apiInfo.getEntityInfoList()) {
                    String entityBasePath = String.format(GeneratorConstant.ENTITY_PATH, basePath);
                    if (entityInfo.getClassName().endsWith("Dto")){
                        entityBasePath=String.format(GeneratorConstant.ENTITY_DTO_PATH, basePath,classDefaultName);
                    }else if(entityInfo.getClassName().endsWith("Vo")){
                        entityBasePath=String.format(GeneratorConstant.ENTITY_VO_PATH, basePath,classDefaultName);
                    }
                    String entityPath = entityBasePath + GeneratorConstant.SEPARATOR + entityInfo.getClassName() + ".java";
                    freemarkerGenerator.createFile(GeneratorConstant.ENTITY_FTL, entityPath, entityInfo);
                }
            }
            logger.info("服务 {} 类文件生成完毕.", serviceInfo.getServiceName());
        }
    }

    private void initDirectory(String basePath,String classDefaultName) {
        new File(String.format(GeneratorConstant.CONTROLLER_PATH, basePath)).mkdirs();
        new File(String.format(GeneratorConstant.FEIGN_PATH, basePath)).mkdirs();
        new File(String.format(GeneratorConstant.SERVICE_PATH, basePath)).mkdirs();
        new File(String.format(GeneratorConstant.IMPL_PATH, basePath)).mkdirs();
        new File(String.format(GeneratorConstant.ENTITY_PATH, basePath)).mkdirs();
        new File(String.format(GeneratorConstant.ENTITY_DTO_PATH, basePath,classDefaultName)).mkdirs();
        new File(String.format(GeneratorConstant.ENTITY_VO_PATH, basePath,classDefaultName)).mkdirs();
    }

}
