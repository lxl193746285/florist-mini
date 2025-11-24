package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_执行_办理人类型
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfSelectUserRuleEnums {
    //1指定人员;2部门主管;3直属主管;4角色;5发起人自选;6发起人自己;7表单内的联系人
    ToUser("1指定人员",1),
    DeptManager("2部门主管",2),
    Superior("3直属主管",3),
    Role("4角色",4),
    ApplySelect("5发起人自选",5),
    ApplyUser("6发起人自己",6),
    Form("7表单内的联系人",7),
    Exp("表达式",20),
    ;

    private String name;
    private  int id;
    private WfSelectUserRuleEnums(String name, int id) {
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
