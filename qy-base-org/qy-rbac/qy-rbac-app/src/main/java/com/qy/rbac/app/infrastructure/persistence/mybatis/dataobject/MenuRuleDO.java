package com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 菜单规则
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_menu_rule")
public class MenuRuleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 名称
     */
    private String name;

    /**
     * 规则范围id
     */
    private String scopeId;

    /**
     * 排序
     */
    private Integer sort;


}
