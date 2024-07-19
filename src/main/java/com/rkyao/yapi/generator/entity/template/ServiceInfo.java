package com.rkyao.yapi.generator.entity.template;

import com.rkyao.yapi.generator.entity.PackageEntity;
import com.rkyao.yapi.generator.util.PathUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * 服务信息
 *
 * @author yaorongke
 * @date 2022/5/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 服务描述信息
     */
    private String serviceDesc = "ClassDesc";

    /**
     * 接口类基础路径
     */
    private String basePackage;

    /**
     * 各类包路径
     */
    private PackageEntity packageInfo;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 接口列表
     */
    private List<ApiInfo> apiList;

    public void fillPackageInfo(){
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setControllerPath(PathUtils.CONTROLLER.getPath(this));
        packageEntity.setFeignPath(PathUtils.FEIGN.getPath(this));
        packageEntity.setServicePath(PathUtils.SERVICE.getPath(this));
        packageEntity.setImplPath(PathUtils.IMPL.getPath(this));
        packageEntity.setEntityDtoPath(PathUtils.ENTITY_DTO.getPath(this));
        packageEntity.setEntityVoPath(PathUtils.ENTITY_VO.getPath(this));
        this.setPackageInfo(packageEntity);
    }
}
