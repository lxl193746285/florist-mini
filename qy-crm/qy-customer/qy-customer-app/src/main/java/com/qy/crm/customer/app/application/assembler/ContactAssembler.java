package com.qy.crm.customer.app.application.assembler;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.crm.customer.app.application.dto.ContactBasicDTO;
import com.qy.crm.customer.app.application.dto.ContactDTO;
import com.qy.crm.customer.app.application.security.ContactPermission;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户联系人汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class ContactAssembler {
    @Autowired
    private ContactPermission contactPermission;
    @Autowired
    private AttachmentClient attachmentClient;

    @Mapping(source = "idNumberFront", target = "idNumberFront", qualifiedByName = "mapAttachment")
    @Mapping(source = "idNumberBack", target = "idNumberBack", qualifiedByName = "mapAttachment")
    @Mapping(source = "contactDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract ContactDTO toDTO(ContactDO contactDO, @Context Identity identity);

    public abstract ContactBasicDTO toBasicDTO(ContactDO contactDO);

    @Named("mapAttachment")
    public AttachmentBasicDTO mapAttachment(String attachmentId) {
        if (StringUtils.isBlank(attachmentId)) {
            return null;
        }
        return attachmentClient.getBasicAttachment(Long.valueOf(attachmentId));
    }

    @Named("mapActions")
    public List<Action> mapActions(ContactDO contactDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (contactPermission.hasPermission(identity, ContactPermission.UPDATE, contactDO.getId())) {
            actions.add(ContactPermission.VIEW.toAction());
        }
        if (contactPermission.hasPermission(identity, ContactPermission.UPDATE, contactDO.getId())) {
            actions.add(ContactPermission.UPDATE.toAction());
        }
        if (contactPermission.hasPermission(identity, ContactPermission.DELETE, contactDO.getId())) {
            actions.add(ContactPermission.DELETE.toAction());
        }
        return actions;
    }
}