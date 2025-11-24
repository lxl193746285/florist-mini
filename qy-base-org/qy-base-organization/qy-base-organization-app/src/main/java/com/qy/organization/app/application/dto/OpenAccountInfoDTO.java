package com.qy.organization.app.application.dto;

import com.qy.identity.api.dto.UserBasicDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 开户信息
 *
 * @author legendjw
 */
@Data
public class OpenAccountInfoDTO {
    /**
     * 开户组织
     */
    private OrganizationDTO organization;

    /**
     * 开户组织超管
     */
    private UserBasicDTO superAdmin;

    /**
     * 开户授权的权限组
     */
    private List<RoleBasicDTO> roles = new ArrayList<>();
}
