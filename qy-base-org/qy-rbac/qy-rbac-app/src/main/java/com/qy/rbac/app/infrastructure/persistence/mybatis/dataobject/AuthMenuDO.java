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
 * 菜单
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_auth_menu")
public class AuthMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 模块id
     */
    private Long moduleId;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单别名
     */
    private String aliasName;

    /**
     * 功能标示
     */
    private String code;

    /**
     * 权限菜单类型
     */
    private Integer authTypeId;

    /**
     * 权限菜单类型名称
     */
    private String authTypeName;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 外链
     */
    private String externalLink;

    /**
     * 认证项
     */
    private String authItem;

    /**
     * 权限分类
     */
    private String authItemCategory;

    /**
     * 权限说明
     */
    private String authItemExplain;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 额外数据
     */
    private String extraData;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
