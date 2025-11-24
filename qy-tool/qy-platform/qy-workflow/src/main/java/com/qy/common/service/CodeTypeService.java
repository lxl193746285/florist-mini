package com.qy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class CodeTypeService {

    @Autowired
    private ArkCodeTableService arkCodeTableService;

    public String getCodeNameByInt(String code,Integer type){
        LinkedHashMap<Integer, String> target = arkCodeTableService.getSystemListByCode(code);
        return target.get(type);
    }

}
