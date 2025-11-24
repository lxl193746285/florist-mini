package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_节点动作
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfNodeType {
    Start("开始",1),
    Deal("办理",2),
    Finish("完成",3),
    ;

    private String name;
    private  int id;
    private WfNodeType(String name, int id) {
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
