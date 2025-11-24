package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流-审批结果
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfNodeStatus {

    //办理状态 wf_node_status  1发起,2办理中3已办理
    UnDeal("无",0),
    Start("已发起",1),
    Dealing("办理中",2),
    Finish("已办理",3),

    ;

    private String name;
    private  Integer id;
    private WfNodeStatus(String name, Integer id) {
        this.name=name;
        this.id = id;
    }
     public Integer getId() {
            return id;
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
