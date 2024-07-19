package ${basePackage}.controller;

import ${basePackage}.entity.*;
import ${basePackage}.service.*;
import com.postar.gtzt.common.model.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@RestController
@RequestMapping("/${serviceName?uncap_first}")
@Slf4j
public class ${serviceName}Controller {


    @Autowired
    private ${serviceName}Service ${serviceName?uncap_first}Service;

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
    @PostMapping("/${api.methodName}")
    public ${api.responseType} ${api.methodName}(<#list api.paramList as param><#if param.annotation??>${param.annotation} @Validated </#if>${param.type} ${param.name}<#if param_has_next>, </#if></#list>) {
    <#if api.responseType != "void">
        return ${serviceName?uncap_first}Service.${api.methodName}(<#list api.paramList as param>${param.name}<#if param_has_next>, </#if></#list>);
    </#if>
    }

    </#list>
}