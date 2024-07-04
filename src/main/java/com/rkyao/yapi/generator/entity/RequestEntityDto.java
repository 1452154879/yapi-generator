package com.rkyao.yapi.generator.entity;

import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class RequestEntityDto {
    List<String> interfaceIds;
    List<ServiceInfo> serviceInfoList;
}
