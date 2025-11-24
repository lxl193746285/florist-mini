package com.qy.organization.app.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 会员权限组信息
 *
 * @author lxl
 */
@Data
public class SystemRoleDTO implements Serializable {
    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 会员系统名称
     */
    private String systemName;

    /**
     * 权限组ID集合
     */
    private List<RoleBasicDTO> roleIds;
}
