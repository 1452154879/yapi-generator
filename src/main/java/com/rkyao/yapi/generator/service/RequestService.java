package com.rkyao.yapi.generator.service;

import com.alibaba.fastjson.JSONObject;
import com.rkyao.yapi.generator.entity.RequestEntityDto;

public interface RequestService {
    void request(RequestEntityDto dto);
}
