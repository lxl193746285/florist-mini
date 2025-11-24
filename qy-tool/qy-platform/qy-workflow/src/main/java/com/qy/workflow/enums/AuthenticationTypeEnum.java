package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 查询方式
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuthenticationTypeEnum {

    None("无",0),
    /**
     * BEARER_TOKEN
     */
    BEARER_TOKEN("BEARER_TOKEN",1),
    /**
     * API_KEY
     */
    API_KEY("API_KEY",2),
    /**
     * JWT
     */
    JWT("JWT",3),
    /**
     * basic认证
     */
    BASIC_AUTH("BASIC_AUTH",4),

    /*
     * 签名认证
     */
    SIGN("SIGN",5),
    /**
     * 内部token 逻辑上是BEARER_TOKEN的自动版本
     */
    Inner_Token("Inner_Token",6),
    ;

    private String name;
    private  Integer id;
    private AuthenticationTypeEnum(String name, Integer id) {
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
