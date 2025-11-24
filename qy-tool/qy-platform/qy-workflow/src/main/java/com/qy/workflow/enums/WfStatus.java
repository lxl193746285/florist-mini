package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流-审批结果
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfStatus {

    //工作流状态 代码表 wf_status  1发起,2办理中3结束
    UnDeal("无",0),
    Start("发起",1),
    Deal("办理",2),
    Finish("结束",3),

    ;

    private String name;
    private  Integer id;
    private WfStatus(String name, Integer id) {
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

    public static final WfStatus getInstance(final int i){
        for(WfStatus dt: WfStatus.values()){
            if(dt.getId() == i){
                return dt;
            }
        }
        return null;
    }
}
