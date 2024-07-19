package ${basePackage}.entity;

import ${entityDtoPath}.*;
import ${entityVoPath}.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;

/**
 * ${desc}
 *
 * @author ${classAuthor}
 * @date ${.now?string("yyyy/MM/dd")}
 */
@Data
@Builder
<#if fieldList.size()!=0>
@NoArgsConstructor
@AllArgsConstructor
</#if>
public class ${className}{

<#list fieldList as field>
    /**
     * ${field.desc}
     */
  <#list field.checkAnnotationList as annotation>
    ${annotation}
  </#list>
    private ${field.type} ${field.name};

</#list>
}
