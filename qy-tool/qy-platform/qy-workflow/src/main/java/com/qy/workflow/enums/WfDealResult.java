package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流-办理意见
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfDealResult {

    //查找办理权限 办理类型 1-同意，2拒绝，3-退回，4-作废，5撤回 6办理
    Start("发起",0),
    Agree("同意",1),
    Refuse("拒绝",2),
    Return("退回",3),
    Invalid("作废",4),
    Withdraw("撤回",5),
    Deal("办理",6),
    Revoke("撤销",7),
    Repulse("打回",8)
    ;

    private String name;
    private  Integer id;
    private WfDealResult(String name, Integer id) {
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
