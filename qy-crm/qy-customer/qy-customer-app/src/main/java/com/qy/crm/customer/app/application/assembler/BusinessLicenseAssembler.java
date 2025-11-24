package com.qy.crm.customer.app.application.assembler;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.crm.customer.app.application.dto.BusinessLicenseDTO;
import com.qy.crm.customer.app.application.security.BusinessLicensePermission;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.BusinessLicenseDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 营业执照汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class BusinessLicenseAssembler {
    @Autowired
    private AttachmentClient attachmentClient;
    @Autowired
    private BusinessLicensePermission businessLicensePermission;

    @Mapping(source = "imageId", target = "image", qualifiedByName = "mapImage")
    @Mapping(source = "businessLicenseDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract BusinessLicenseDTO toDTO(BusinessLicenseDO businessLicenseDO, @Context Identity identity);

    @Named("mapImage")
    public AttachmentBasicDTO mapImage(Long imageId) {
        if (imageId == null) {
            return null;
        }
        return attachmentClient.getBasicAttachment(imageId);
    }

    @Named("mapActions")
    public List<Action> mapActions(BusinessLicenseDO businessLicenseDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (businessLicensePermission.hasPermission(identity, BusinessLicensePermission.UPDATE, businessLicenseDO.getId())) {
            actions.add(BusinessLicensePermission.UPDATE.toAction());
        }
        if (businessLicensePermission.hasPermission(identity, BusinessLicensePermission.DELETE, businessLicenseDO.getId())) {
            actions.add(BusinessLicensePermission.DELETE.toAction());
        }
        return actions;
    }
}