package com.rkyao.yapi.generator.entity;

import lombok.Data;

@Data
public class PackageEntity {
    /**
     * Controller路径
     */
    private String controllerPath;
    /**
     * feignPath路径
     */
    private String feignPath;
    /**
     * service路径
     */
    private String servicePath;
    /**
     * implPath路径
     */
    private String implPath;
    /**
     * entityDto路径
     */
    private String entityDtoPath;
    /**
     * entityVo路径
     */
    private String entityVoPath;
}
