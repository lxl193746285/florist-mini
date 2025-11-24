package com.qy.workflow.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作流作废返回值显示的运行节点
 *
 * @author iFeng
 * @since 2023-08-14
 */
@Data
public class WfInvalidRunNodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 执行节点ID
     *
     */
    private Long runNodeId;

    private Integer sort;

    /**
     * 节点名称
     *
     */
    private String name;

    private String arriveTimeName;

    private String approveResultName;

    private String approveComments;


}
