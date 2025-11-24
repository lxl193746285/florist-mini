package com.qy.crm.customer.app.application.service;

import com.qy.crm.customer.app.application.command.*;
import com.qy.crm.customer.app.application.dto.BusinessLicenseIdDTO;
import com.qy.crm.customer.app.application.command.*;

/**
 * 营业执照命令服务
 *
 * @author legendjw
 */
public interface BusinessLicenseCommandService {
    /**
     * 创建营业执照命令
     *
     * @param command
     * @return
     */
    BusinessLicenseIdDTO createBusinessLicense(CreateBusinessLicenseCommand command);

    /**
     * 编辑营业执照命令
     *
     * @param command
     */
    void updateBusinessLicense(UpdateBusinessLicenseCommand command);

    /**
     * 保存营业执照命令
     *
     * @param command
     * @return
     */
    BusinessLicenseIdDTO saveBusinessLicense(SaveBusinessLicenseCommand command);

    /**
     * 删除关联信息的联系人命令
     *
     * @param command
     */
    void deleteRelatedBusinessLicense(DeleteRelatedDataCommand command);

    /**
     * 删除营业执照命令
     *
     * @param command
     */
    void deleteBusinessLicense(DeleteBusinessLicenseCommand command);
}
