package com.qy.workflow.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_查询表结构
 *
 * @author iFeng
 * @since 2023-08-05
 */
@Data
public class WfDefNodeTableDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     *显示时机1详情使用 2办理使用
     */
    private Integer showType;

    /**
     * 平台 1-PC 2-uniapp（混合移动端） 3-微信小程序 4-APP  wf_def_node_table.platform
     */
    private Integer platform;

    /**
     * 表单ID
     */
    private String tableId;



}
