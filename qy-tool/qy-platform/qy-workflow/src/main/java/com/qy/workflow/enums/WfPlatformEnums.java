package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_平台
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfPlatformEnums {
    //平台 1-全平台，2-PC 3-uniapp（混合移动端） 3-微信小程序 4-APP  wf_def_node_table.platform
    ALL("1.ALL",1),
    PC("2.PC",2),
    WeixinApp("3.微信小程序",3),
    App("4.APP",4),
    WeixinMP("5.微信公众号",5),
    ;
    private String name;
    private  int id;
    private WfPlatformEnums(String name, int id) {
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
