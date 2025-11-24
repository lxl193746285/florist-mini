package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_显示方式
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfShowModeEnums {
    //显示方式 1.直接显示 2.显示按钮链接 wf_def_node_table.show_mode
    Show("1.直接显示",1),
    Link("2.显示按钮链接",2),
    ;
    private String name;
    private  int id;
    private WfShowModeEnums(String name, int id) {
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
