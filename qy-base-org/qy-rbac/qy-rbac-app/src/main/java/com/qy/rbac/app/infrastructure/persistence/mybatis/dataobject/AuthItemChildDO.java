package com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限认证项子父关系
 * </p>
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_rbac_auth_item_child")
public class AuthItemChildDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父项
     */
    private String parent;

    /**
     * 子项
     */
    private String child;

    /**
     * 规则
     */
    private String rule;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    @TableField(exist = false)
    private Long systemId;
}
