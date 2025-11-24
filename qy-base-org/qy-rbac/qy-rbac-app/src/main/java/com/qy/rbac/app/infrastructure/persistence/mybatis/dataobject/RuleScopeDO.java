package com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限规则范围
 * </p>
 *
 * @author legendjw
 * @since 2021-08-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_rbac_rule_scope")
public class RuleScopeDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否选择数据 1: 是 0: 否
     */
    private Byte isSelectData;

    /**
     * 数据源类型 1: 自定义 2: sql取值
     */
    private Byte dataSource;

    /**
     * 数据取值sql
     */
    private String dataSourceSql;

    /**
     * 展示形式 1: 多选框 2: 树形多选框
     */
    private Byte selectShowForm;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
