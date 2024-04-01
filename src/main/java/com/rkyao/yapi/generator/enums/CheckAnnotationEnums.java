package com.rkyao.yapi.generator.enums;



import freemarker.template.utility.StringUtil;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static com.rkyao.yapi.generator.enums.FieldType.*;

public enum CheckAnnotationEnums {
    NOT_EMPTY(Arrays.asList(ARRAY),"@NotEmpty(message=\"%s\")"),
    NOT_NULL(Arrays.asList(NUMBER,OBJECT,BOOLEAN,INTEGER),"@NotNull(message=\"%s\")"),
    NOT_BLANK(Arrays.asList(STRING),"@NotBlank(message=\"%s\")");

    ;
    private List<FieldType> type;
    private String annotation;

    /**
     * 根据对应的类型来获取获取对应的注解
     * @param fieldTypeSource
     * @return
     */
    public static CheckAnnotationEnums getEnum(String fieldTypeSource){
        FieldType fieldType = FieldType.getFieldType(fieldTypeSource);
        for (CheckAnnotationEnums enums : CheckAnnotationEnums.values()) {
            if(enums.getType().contains(fieldType)){
                return enums;
            };
        }
        return null;
    }

    private List<FieldType> getType(){
        return type;
    }

    CheckAnnotationEnums(List<FieldType> type,String annotation){
        this.type=type;
        this.annotation=annotation;
    }
    public String getFormatAnnotationStr(String desc){
        int spaceIndex=desc.indexOf(" ");
        if (spaceIndex==-1){
            spaceIndex=desc.length();
        }
        String message=desc.substring(0, spaceIndex);
        if(!StringUtils.isEmpty(message)){
            message+="不能为空";
            String format = String.format(annotation,message);
            return format;
        }
        return null;
    }
}
