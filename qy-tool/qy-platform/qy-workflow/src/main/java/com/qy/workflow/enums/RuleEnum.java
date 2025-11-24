package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 执行方式
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RuleEnum {

    Http("Http", 1),
    SQL("SQL", 2),
    JS("JS", 3)
    ;

    private String name;
    private  Integer id;
    private RuleEnum(String name, Integer id) {
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
