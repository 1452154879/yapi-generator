package com.rkyao.yapi.generator.init;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rkyao.yapi.generator.config.YapiGeneratorConfig;
import com.rkyao.yapi.generator.entity.InsertQtInterfaceDto;
import com.rkyao.yapi.generator.entity.template.ApiInfo;
import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import com.rkyao.yapi.generator.enums.GeneratingPatterns;
import com.rkyao.yapi.generator.service.ClassGeneratorService;
import com.rkyao.yapi.generator.service.ClassInfoTransformService;
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
    @Autowired
    private RestTemplate restTemplate;
    @PostConstruct
    public void init() {
        log.info("开始添加前置接口配置");
        if (!initSelect.equals(initComponentName)){
            return;
        }
        restTemplateMessageConvertConfig();
        // 获取接口id列表
        List<String> interfaceIds = getInterfaceIds();

        // 类信息
        List<ServiceInfo> serviceInfoList = classInfoTransformService.getServiceInfoList(interfaceIds, GeneratingPatterns.valueOf(yapiGeneratorConfig.getClassPatterns()));

        //请求前置接口添加配置
        requestUrlByApi(serviceInfoList);

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
    @Value("${yapi.request.url}")
    private String requestUrl;

    /**
     * 请求对应url携带yapi接口信息
     * @param serviceInfoList
     */
    private void requestUrlByApi(List<ServiceInfo> serviceInfoList){
        if (CollectionUtils.isEmpty(serviceInfoList)){
            return;
        }
        ServiceInfo serviceInfo = serviceInfoList.get(0);
        List<ApiInfo> apiList = serviceInfo.getApiList();
        //根据接口路径进行排序
        apiList = apiList.stream().sorted(Comparator.comparing(ApiInfo::getPath)).collect(Collectors.toList());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhZG1pbiIsImlzcyI6ImFkbWluIiwiand0VG9rZW4iOiJ7XCJhZG1pblR5cGVcIjpcIjAxXCIsXCJhdmF0YXJcIjpcIlwiLFwiZGVwdElkXCI6XCIxMDBcIixcImxvZ2luRGF0ZVwiOlwiMjAyNC0wMy0yNiAxMDowMToxNFwiLFwibG9naW5JcFwiOlwiMTAuMTY5LjkwLjIxN1wiLFwibG9naW5OYW1lXCI6XCJjaGFueWFuMDBcIixcIm1hbmFnZUFyZWFcIjpcIjBcIixcIm1hbmFnZVByb2R1Y3RcIjpcIjBcIixcInB3ZFVwZGF0ZURhdGVcIjpcIlwiLFwicmVtYXJrXCI6XCJcIixcInNlY3JldEl2Vm9cIjp7XCJyZXFJdlwiOlwiMWFzNDd1M2I1MG90NTE4S1wiLFwicmVxU2VjcmV0XCI6XCI2dGdoYjdpbjIyNXJ0MThLXCIsXCJyc3BJdlwiOlwidW42MG9kZTMxcWEzZjE4S1wiLFwicnNwU2VjcmV0XCI6XCJxNXk3OTM2ZHA3amRyMThLXCJ9LFwidXNlckVtYWlsXCI6XCJcIixcInVzZXJJZFwiOlwiMWI2MDQ5NDY1ZGI0NDkxZWFiNDhkNDVlOWUzMzcxN2RcIixcInVzZXJOYW1lXCI6XCJjaGFueWFuMDDkuK3mlodcIixcInVzZXJTZXhcIjpcIlwiLFwidXNlclNvdXJjZVwiOlwiXCIsXCJ1c2VyVGVsXCI6XCIxNzgwMDAwMDAwMFwiLFwidXNlclR5cGVcIjpcIjAwXCJ9IiwiZXhwIjoxNzExNDIwMzA5LCJpYXQiOjE3MTE0MTg1MDl9.y9nmWSJ2bLEr59sokdujWNucvTsSc2aJdYM20gh9Qyk");
        for (ApiInfo apiInfo : apiList) {
            InsertQtInterfaceDto body = new InsertQtInterfaceDto(apiInfo.getDesc(), "00", apiInfo.getPath(), apiInfo.getDesc());
            HttpEntity<String> httpEntity = new HttpEntity<>(JSONObject.toJSONString(body),httpHeaders);
            Map result = restTemplate.postForObject(requestUrl, httpEntity, Map.class);
            log.info("{}接口新增结果为:{}",apiInfo.getDesc(),JSON.toJSONString(result));
        }
    }

    private void restTemplateMessageConvertConfig(){
        // 中文乱码，主要是 StringHttpMessageConverter的默认编码为ISO导致的
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter converter : list) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
                break;
            }
        }
    }
}
