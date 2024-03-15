package ${basePackage}.controller;

import ${basePackage}.entity.*;
import ${basePackage}.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${serviceDesc}
 *
 * @author ${classAuthor}
 * @date ${.now?string("yyyy/MM/dd")}
 */
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@FeignClient(name = "${serviceName?uncap_first}Client", url = "${r'${params.gatewayAppUrl}'}")
public class ${serviceName}Client {

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