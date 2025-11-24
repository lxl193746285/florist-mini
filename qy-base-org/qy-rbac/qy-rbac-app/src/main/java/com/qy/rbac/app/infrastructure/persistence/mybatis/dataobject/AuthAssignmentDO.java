package com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色用户关联
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_rbac_auth_assignment")
public class AuthAssignmentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 认证项
     */
    private String itemName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 会员系统
     */
    private Long systemId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
