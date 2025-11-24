package com.qy.organization.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganizationCreator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织权限组
 * </p>
 *
 * @author legendjw
 * @since 2021-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_org_role")
public class RoleDO implements Serializable, GetOrganizationCreator {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称拼音
     */
    private String namePinyin;

    /**
     * 授权项
     */
    private String authItem;

    /**
     * 岗位描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 默认权限组类型
     */
    private String defaultRole;

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
