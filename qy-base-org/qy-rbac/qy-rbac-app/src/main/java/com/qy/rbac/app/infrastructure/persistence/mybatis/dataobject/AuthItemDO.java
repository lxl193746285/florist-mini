package com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限认证项
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_rbac_auth_item")
public class AuthItemDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 认证项
     */
    private String name;

    /**
     * 类型: 角色: 1 权限节点: 2
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
