package com.qy.crm.customer.app.application.dto;

import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.organization.api.dto.RoleBasicDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 开户信息
 *
 * @author legendjw
 */
@Data
public class CustomerOpenAccountInfoDTO {
    /**
     * 开户组织
     */
    private OrganizationDTO organization;

    /**
     * 开户组织超管
     */
    private ContactDTO superAdmin;

    /**
     * 开户授权的权限组
     */
    private List<RoleBasicDTO> roles = new ArrayList<>();
}
