package com.rkyao.yapi.generator.util;

import com.rkyao.yapi.generator.entity.template.ServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PathUtils {
    CONTROLLER() {
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".controller."+serviceInfo.getProjectName();
        }
    },
    SERVICE() {
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".service."+serviceInfo.getProjectName();
        }
    },
    IMPL() {
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".service."+serviceInfo.getProjectName()+".impl";
        }
    },
    FEIGN() {
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".feign."+serviceInfo.getProjectName()+serviceInfo.getServiceName().toLowerCase();
        }
    },
    ENTITY_DTO(){
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".entity."+ "dto."+serviceInfo.getProjectName()+"."+serviceInfo.getServiceName().toLowerCase();
        }
    },
    ENTITY_VO(){
        @Override
        public String getPath(ServiceInfo serviceInfo) {
            return serviceInfo.getBasePackage()+".entity."+ "vo."+serviceInfo.getProjectName()+"."+serviceInfo.getServiceName().toLowerCase();
        }
    }
    ;
    public abstract String getPath(ServiceInfo serviceInfo);
}
