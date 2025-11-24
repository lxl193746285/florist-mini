package com.qy.member.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qy.security.interfaces.GetOrganization;
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
@TableName("ark_mbr_member_system_organization")
public class MemberSystemOrganizationDO implements GetOrganization, Serializable {

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
     * 会员系统id
     */
    private Long systemId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 来源（1创建2授权）
     */
    private Integer source;
}
