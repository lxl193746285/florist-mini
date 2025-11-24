package com.qy.member.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganization;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员系统
 * </p>
 *
 * @author legendjw
 * @since 2021-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_mbr_member_system")
public class MemberSystemDO implements GetOrganization, Serializable {

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
     * 系统类型id
     */
    private Integer typeId;

    /**
     * 系统类型名称
     */
    private String typeName;

    /**
     * 会员类型id
     */
    private Integer memberTypeId;

    /**
     * 会员类型名称
     */
    private String memberTypeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 会员注册是否需要审核
     */
    private Byte isMemberAudit;

    /**
     * 是否是默认的会员系统
     */
    private Byte isDefault;

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
