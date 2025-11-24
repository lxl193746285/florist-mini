package com.qy.workflow.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_执行_获取办理表
 *
 * @author iFeng
 * @since 2022-12-01
 */
@Data
public class WfWfDealTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 表ID
     */
    private Long table_id;

    /**
     * 表单类型 1查询表单 取wf_def_query_table表配置，2.工作流表单，3.系统开发界面 调用菜单功能  4.IDE设计器功能，5.跳转链接  6.调用菜单功能 代码表 wf_def_query_table.table_type
     */
    private Integer table_type;


    /**
     * 表路径
     */
    private String table_path;

    /**
     * 功能ID
     */
    private String ideFunId;
    /**
     * 界面ID
     */
    private String ideFormId;


}
