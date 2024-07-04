package com.rkyao.yapi.generator.init;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rkyao.yapi.generator.config.YapiGeneratorConfig;
import com.rkyao.yapi.generator.entity.InsertQtInterfaceDto;
import com.rkyao.yapi.generator.entity.RequestEntityDto;
import com.rkyao.yapi.generator.entity.template.ApiInfo;
import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import com.rkyao.yapi.generator.enums.GeneratingPatterns;
import com.rkyao.yapi.generator.service.ClassGeneratorService;
import com.rkyao.yapi.generator.service.ClassInfoTransformService;
import com.rkyao.yapi.generator.service.RequestService;
import com.rkyao.yapi.generator.service.YapiOpenapiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成前台接口配置
 * @author A
 */
@Component
@Slf4j
public class InterfaceMsgRequestInit {
    @Autowired
    private YapiGeneratorConfig yapiGeneratorConfig;

    @Autowired
    private YapiOpenapiService yapiOpenapiService;

    @Autowired
    private ClassGeneratorService classGeneratorService;

    @Autowired
    private ClassInfoTransformService classInfoTransformService;

    @Value("${yapi.init.component.select}")
    String initSelect;
    private final String  initComponentName="request";
    @Resource
    RequestService requestService;
    @PostConstruct
    public void init() {
        log.info("开始添加前置接口配置");
        if (!initSelect.equals(initComponentName)){
            return;
        }
        // 获取接口id列表
        List<String> interfaceIds = getInterfaceIds();

        // 类信息
        List<ServiceInfo> serviceInfoList = classInfoTransformService.getServiceInfoList(interfaceIds, GeneratingPatterns.valueOf(yapiGeneratorConfig.getClassPatterns()));

        //请求前置接口添加配置
        requestService.request(RequestEntityDto.builder().serviceInfoList(serviceInfoList).interfaceIds(interfaceIds).build());

        log.info("添加前置接口配置成功.");
    }


    private List<String> getInterfaceIds() {
        Set<String> interfaceIdSet = new HashSet<>();

        String catId = yapiGeneratorConfig.getCatId();
        List<String> catList = Arrays.asList(catId.split(","));
        if (!CollectionUtils.isEmpty(catList)) {
            for (String cat : catList) {
                List<String> ids = yapiOpenapiService.listCatIds(cat);
                if (!CollectionUtils.isEmpty(ids)) {
                    interfaceIdSet.addAll(ids);
                }
            }
        }

        String idsStr = yapiGeneratorConfig.getIntefaceIds();
        if (!StringUtils.isEmpty(idsStr)) {
            List<String> ids = Arrays.asList(idsStr.split(","));
            interfaceIdSet.addAll(ids);
        }

        if (CollectionUtils.isEmpty(interfaceIdSet)) {
            log.error("获取接口id失败, 请检查配置文件 [yapi-generator.properties], [yapi.api.interface.ids] 和 [yapi.api.cat.id] 至少配置一个");
            throw new RuntimeException("获取接口id失败, 请检查配置!");
        }
        return new ArrayList<>(interfaceIdSet);
    }




}
