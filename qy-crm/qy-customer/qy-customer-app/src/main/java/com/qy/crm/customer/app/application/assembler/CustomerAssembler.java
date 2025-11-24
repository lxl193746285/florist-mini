package com.qy.crm.customer.app.application.assembler;

import com.qy.audit.api.enums.AuditStatus;
import com.qy.crm.customer.app.application.dto.*;
import com.qy.crm.customer.app.application.enums.Module;
import com.qy.crm.customer.app.application.security.CustomerPermission;
import com.qy.crm.customer.app.application.service.BusinessLicenseQueryService;
import com.qy.crm.customer.app.application.service.ContactQueryService;
import com.qy.crm.customer.app.application.service.OpenBankQueryService;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.CustomerDO;
import com.qy.crm.customer.app.application.dto.*;
import com.qy.organization.api.dto.OrganizationBasicDTO;
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
 * 客户汇编器
 *
 * @author legendjw
 */
@Mapper(componentModel="spring")
public abstract class CustomerAssembler {
    @Autowired
    private CustomerPermission customerPermission;
    @Autowired
    private ContactQueryService contactQueryService;
    @Autowired
    private BusinessLicenseQueryService businessLicenseQueryService;
    @Autowired
    private OpenBankQueryService openBankQueryService;

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "customerDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract CustomerDTO toDTO(CustomerDO customerDO, @Context List<OrganizationBasicDTO> organizationDTOS, @Context Identity identity);

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "id", target = "contacts", qualifiedByName = "mapContacts")
    @Mapping(source = "id", target = "businessLicense", qualifiedByName = "mapBusinessLicense")
    @Mapping(source = "id", target = "openBanks", qualifiedByName = "mapOpenBanks")
    @Mapping(source = "customerDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract CustomerDetailDTO toDetailDTO(CustomerDO customerDO, @Context List<OrganizationBasicDTO> organizationDTOS, @Context Identity identity);

    public abstract CustomerBasicDTO toBasicDTO(CustomerDO customerDO);

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) { return ""; }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }

    @Named("mapContacts")
    public List<ContactDTO> mapContacts(Long customerId) {
        return contactQueryService.getContactsByRelatedModule(Module.CUSTOMER.getId(), customerId);
    }

    @Named("mapBusinessLicense")
    public BusinessLicenseDTO mapBusinessLicense(Long customerId) {
        return businessLicenseQueryService.getBusinessLicenseByRelatedModule(Module.CUSTOMER.getId(), customerId);
    }

    @Named("mapOpenBanks")
    public List<OpenBankDTO> mapOpenBanks(Long customerId) {
        return openBankQueryService.getOpenBanksByRelatedModule(Module.CUSTOMER.getId(), customerId);
    }

    @Named("mapActions")
    protected List<Action> mapActions(CustomerDO customerDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (customerPermission.hasPermission(identity, CustomerPermission.VIEW, customerDO.getId())) {
            actions.add(CustomerPermission.VIEW.toAction());
        }
        if (customerPermission.hasPermission(identity, CustomerPermission.AUDIT_LOG, customerDO.getId())) {
            actions.add(CustomerPermission.AUDIT_LOG.toAction());
        }
        if (customerDO.getStatusId().intValue() == AuditStatus.DRAFT.getId() && customerPermission.hasPermission(identity, CustomerPermission.SUBMIT_AUDIT, customerDO.getId())) {
            actions.add(CustomerPermission.SUBMIT_AUDIT.toAction());
        }
        if (customerDO.getStatusId().intValue() == AuditStatus.WAITING_AUDIT.getId() && customerPermission.hasPermission(identity, CustomerPermission.AUDIT, customerDO.getId())) {
            actions.add(CustomerPermission.AUDIT.toAction());
        }
        if (customerDO.getStatusId().intValue() == AuditStatus.PASSED.getId()) {
            if (customerPermission.hasPermission(identity, CustomerPermission.OPEN_ACCOUNT, customerDO.getId())) {
                if (customerDO.getIsOpenAccount().intValue() == 0) {
                    actions.add(CustomerPermission.OPEN_ACCOUNT.toAction());
                } else {
                    actions.add(CustomerPermission.MODIFY_ADMIN.toAction());
                }
            }
            //if (customerPermission.hasPermission(identity, CustomerPermission.OPEN_MEMBER, customerDO.getId())) {
            //    if (customerDO.getIsOpenMember().intValue() == 0) {
            //        actions.add(CustomerPermission.OPEN_MEMBER.toAction());
            //    } else {
            //        actions.add(CustomerPermission.MODIFY_MEMBER.toAction());
            //    }
            //}
        }
        if (customerDO.getStatusId().intValue() != AuditStatus.WAITING_AUDIT.getId()
                && customerPermission.hasPermission(identity, CustomerPermission.UPDATE, customerDO.getId())) {
            actions.add(CustomerPermission.UPDATE.toAction());
        }
        if (customerDO.getStatusId().intValue() != AuditStatus.WAITING_AUDIT.getId()
                && customerDO.getStatusId().intValue() != AuditStatus.PASSED.getId()
                && customerPermission.hasPermission(identity, CustomerPermission.DELETE, customerDO.getId())) {
            actions.add(CustomerPermission.DELETE.toAction());
        }
        return actions;
    }
}