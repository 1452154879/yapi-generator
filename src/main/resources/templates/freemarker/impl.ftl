package ${basePackage}.service.impl;

import ${basePackage}.entity.*;
import ${basePackage}.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${serviceDesc}
 *
 * @author ${classAuthor}
 * @date ${.now?string("yyyy/MM/dd")}
 */
@Service
public class ${serviceName}ServiceImpl implements ${serviceName}Service {

    @Autowired
    private ${serviceName}Client ${serviceName?uncap_first}Client;

   <#list apiList as api>
    @Override
    public ${api.responseType} ${api.methodName}(<#list api.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>) {
        <#if api.responseType != "void">
        return ${serviceName?uncap_first}Client.${api.methodName}(<#list api.paramList as param>${param.name}<#if param_has_next>, </#if></#list>);
        </#if>
    }

   </#list>
}