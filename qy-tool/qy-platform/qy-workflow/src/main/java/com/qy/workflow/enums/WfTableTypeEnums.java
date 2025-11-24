package com.qy.workflow.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 工作流_表单类型
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WfTableTypeEnums {
    //表单类型 1查询表单取wf_def_query_table表配置，2.工作流表单 3.系统开发界面 调用菜单功能  4.IDE设计器功能，5.跳转链接 6.调用菜单功能 代码表 wf_node_table
    QueryTable("1.查询表单",1),
    WFTable("2.工作流表单",2),
    SystemFun("3.系统开发界面",3),
    IDEFun("4.IDE设计器功能",4),
    Link("5.跳转链接",5),
    Menu("6.调用菜单功能",6),
    ;
    private String name;
    private  int id;
    private WfTableTypeEnums(String name, int id) {
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
