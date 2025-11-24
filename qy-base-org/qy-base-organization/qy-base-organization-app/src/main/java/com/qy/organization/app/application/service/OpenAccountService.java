package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.OpenAccountCommand;
import com.qy.organization.app.application.command.UpdateOpenAccountCommand;
import com.qy.organization.app.application.dto.OpenAccountInfoDTO;
import com.qy.organization.app.domain.valueobject.OrganizationId;

/**
 * 开户服务
 *
 * @author legendjw
 */
public interface OpenAccountService {
    /**
     * 获取指定来源模块开户的信息
     *
     * @param source
     * @param sourceDataId
     * @return
     */
    OpenAccountInfoDTO getOpenAccountInfo(String source, Long sourceDataId);

    /**
     * 获取指定组织的开户信息
     *
     * @param organizationId
     * @return
     */
    OpenAccountInfoDTO getOpenAccountInfo(Long organizationId);

    /**
     * 组织开户操作
     *
     * @param command
     * @return
     */
    OrganizationId openAccount(OpenAccountCommand command);

    /**
     * 修改开户信息
     *
     * @param command
     * @return
     */
    void updateOpenAccount(UpdateOpenAccountCommand command);
}
