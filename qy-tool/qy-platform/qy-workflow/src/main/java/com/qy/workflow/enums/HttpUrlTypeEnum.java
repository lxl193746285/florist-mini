package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 查询方式
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum HttpUrlTypeEnum {

    Relative("相对地址",1),
    Absolutely("绝对地址",2),
    ;

    private String name;
    private  Integer id;
    private HttpUrlTypeEnum(String name, Integer id) {
        this.name=name;
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getIdString() {
        return id.toString();
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public  String getName(){
        return  name;
    }
    public  void  setName(String name){
        this.name= name;
    }

}
