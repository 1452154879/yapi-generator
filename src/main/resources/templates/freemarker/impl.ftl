package ${basePackage}.service.impl;

import ${packageInfo.entityDtoPath}.*;
import ${packageInfo.entityVoPath}.*;
import ${packageInfo.servicePath}.*;
import ${packageInfo.feignPath}.*;
import com.postar.gtzt.common.model.ResultData;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ${serviceName}Feign ${serviceName?uncap_first}Feign;

   <#list apiList as api>
    @Override
    public ${api.responseType} ${api.methodName}(<#list api.paramList as param>${param.type} ${param.name}<#if param_has_next>, </#if></#list>) {
        <#if api.responseType != "void">
        return ${serviceName?uncap_first}Feign.${api.methodName}(<#list api.paramList as param>${param.name}<#if param_has_next>, </#if></#list>);
        </#if>
    }

   </#list>
}