package com.rkyao.yapi.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InsertQtInterfaceDto {
    private String interfaceName;
    private String platformType;
    private String interfaceAddress;
    private String remark;

}
