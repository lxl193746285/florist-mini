package com.qy.workflow.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 工作流_节点_查询表单
 *
 * @author iFeng
 * @since 2022-11-24
 */
@Data
//@EqualsAndHashCode(callSuper = false)
//@TableName("wf_def_node")
public class WfRunQueryTableEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 查询名称
     */
    private String name;

    /**
     * 表类型
     */
    private Integer style;

    /**
     * 列数量
     */
    private Integer columnCount;

    /**
     * 表规则 1表示查询表达式
     */
    private Integer dataRule;

    /**
     *数据Sql
     */
    private String dataSql;

    /**
     *列名称集合
     */
    private List<String> columnNames;

    /**
     * 数据集合
     */
    private List<Map<String, Object>> dataList;

}
