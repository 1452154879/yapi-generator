package ${basePackage}.controller;

import ${packageInfo.entityDtoPath}.*;
import ${packageInfo.entityVoPath}.*;
import com.postar.gtzt.common.model.ResultData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${serviceDesc}
 *
 * @author ${classAuthor}
 * @date ${.now?string("yyyy/MM/dd")}
 */
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@FeignClient(name = "${serviceName?uncap_first}Feign", url = "${r'${params.gatewayAppUrl}'}")
public interface ${serviceName}Feign {

    <#list apiList as api>
    /**
     * ${api.desc}
     *
    <#list api.paramList as param>
     * @param ${param.name} ${param.desc}
    </#list>
    <#if api.responseType != "void">
     * @return ${api.responseType?uncap_first}
    </#if>
     */
    @PostMapping(value = "${api.path}")
    ${api.responseType} ${api.methodName}(<#list api.paramList as param><#if param.annotation??>${param.annotation} </#if>${param.type} ${param.name}<#if param_has_next>, </#if></#list>);

    </#list>
}