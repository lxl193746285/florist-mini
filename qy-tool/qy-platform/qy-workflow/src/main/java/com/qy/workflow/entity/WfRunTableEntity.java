package com.qy.workflow.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_节点_表单
 *
 * @author iFeng
 * @since 2022-11-24
 */
@Data
//@EqualsAndHashCode(callSuper = false)
//@TableName("wf_def_node_table")
public class WfRunTableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private Integer showType;

    /**
     * 表ID
     */
    private String tableId;

}
