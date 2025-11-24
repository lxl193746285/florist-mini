package com.qy.organization.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author legendjw
 * @since 2021-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_org_organization")
public class OrganizationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称拼音
     */
    private String namePinyin;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 简称拼音
     */
    private String shortNamePinyin;

    /**
     * 组织logo
     */
    private String logo;

    /**
     * 组织电话
     */
    private String tel;

    /**
     * 组织联系人
     */
    private String contactName;

    /**
     * 组织邮箱
     */
    private String email;

    /**
     * 组织主页
     */
    private String homepage;

    /**
     * 组织地址
     */
    private String address;

    /**
     * 组织行业id
     */
    private Integer industryId;

    /**
     * 组织行业名称
     */
    private String industryName;

    /**
     * 组织规模id
     */
    private Integer scaleId;

    /**
     * 组织规模名称
     */
    private String scaleName;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 来源类型
     */
    private String source;

    /**
     * 来源类型名称
     */
    private String sourceName;

    /**
     * 来源关联id
     */
    private Long sourceDataId;

    /**
     * 来源关联名称
     */
    private String sourceDataName;

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

    /**
     * 开户状态id
     */
    private Integer openStatusId;

    /**
     * 开户状态名称
     */
    private String openStatusName;
}
