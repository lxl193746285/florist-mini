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
 * 组织邀请码
 * </p>
 *
 * @author legendjw
 * @since 2021-09-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_org_invitation_code")
public class InvitationCodeDO implements Serializable {

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
     * 类型
     */
    private String type;

    /**
     * 邀请码
     */
    private String code;

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
     * 失效时间
     */
    private LocalDateTime failureTime;


}
