package com.rkyao.yapi.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rkyao.yapi.generator.entity.InsertQtInterfaceDto;
import com.rkyao.yapi.generator.entity.RequestEntityDto;
import com.rkyao.yapi.generator.entity.template.ApiInfo;
import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import com.rkyao.yapi.generator.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Slf4j
public class RequestUrlByApiServiceImpl implements RequestService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 请求对应url携带yapi接口信息
     * @param serviceInfoList
     */
    private void requestUrlByApi(List<ServiceInfo> serviceInfoList){
        restTemplateMessageConvertConfig();
        String requestUrl="";
        if (CollectionUtils.isEmpty(serviceInfoList) || StrUtil.isBlank(requestUrl)){
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
            log.info("{}接口新增结果为:{}",apiInfo.getDesc(), JSON.toJSONString(result));
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

    @Override
    public void request(RequestEntityDto dto) {
        requestUrlByApi(dto.getServiceInfoList());
    }
}
