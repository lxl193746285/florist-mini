package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 事件前事件后
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TriggerTypeEnum {

    BeforeEvent("事件前", 1),
    AfterEvent("事件后", 2),
    ;

    private String name;
    private  Integer id;
    private TriggerTypeEnum(String name, Integer id) {
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
