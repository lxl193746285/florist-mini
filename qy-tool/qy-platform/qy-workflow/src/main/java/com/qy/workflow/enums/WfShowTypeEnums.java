package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_显示时机
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfShowTypeEnums {
    //显示时机1详情使用 2办理使用
    Detail("1.详情使用",1),
    Deal("2.办理使用",2),
    ;
    private String name;
    private  int id;
    private WfShowTypeEnums(String name, int id) {
        this.name=name;
        this.id = id;
    }
     public int getId() {
            return id;
        }
     public void setId(int index) {
            this.id = index;
     }

    public  String getName(){
           return  name;
    }
    public  void  setName(String name){
        this.name= name;
    }
}
