package com.qy.member.app.application.assembler;

import com.qy.audit.api.client.AuditLogClient;
import com.qy.audit.api.dto.AuditLogDTO;
import com.qy.audit.api.query.AuditLogQuery;
import com.qy.member.app.application.dto.MemberBasicDTO;
import com.qy.member.app.application.dto.MemberDTO;
import com.qy.member.app.application.dto.MemberDetailDTO;
import com.qy.member.app.application.repository.AccountDataRepository;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.security.MemberPermission;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.Module;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberAccountDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberDO;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.organization.api.client.OrganizationClient;
import com.qy.organization.api.client.RoleManageClient;
import com.qy.organization.api.dto.OrganizationDTO;
import com.qy.organization.api.dto.RoleBasicDTO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class MemberAssembler {
    @Autowired
    private MemberPermission memberPermission;
    @Autowired
    private RoleManageClient roleManageClient;
    @Autowired
    private AccountDataRepository accountDataRepository;
    @Autowired
    private MemberSystemDataRepository memberSystemDataRepository;
    @Autowired
    private AuditLogClient auditLogClient;
    @Autowired
    private OrganizationClient organizationClient;


    @Mapping(source = "memberDO", target = "systemName", qualifiedByName = "mapSystemName")
    @Mapping(source = "memberDO", target = "organizationName", qualifiedByName = "mapOrganizationName")
    public abstract MemberBasicDTO toBasicDTO(MemberDO memberDO);

    @Mapping(source = "memberDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "memberDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "memberDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "memberDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract MemberDTO toDTO(MemberDO memberDO, @Context Identity identity);

    @Mapping(source = "memberDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "memberDO", target = "roles", qualifiedByName = "mapRoles")
    @Mapping(source = "memberDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "memberDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract MemberDetailDTO toDetailDTO(MemberDO memberDO, @Context Identity identity);

    @Named("mapActions")
    protected List<Action> mapActions(MemberDO memberDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (identity.hasPermission(MemberPermission.VIEW)) {
            actions.add(MemberPermission.VIEW.toAction());
        }
        //是否平台审核通过
        boolean isPlatformAuditPass = memberDO.getAuditStatusId().intValue() == AuditStatus.PLATFORM_PASSED.getId();
        if (identity.hasPermission(MemberPermission.STORE_AUDIT)) {
            boolean isWaitAudit = memberDO.getAuditStatusId().intValue() == AuditStatus.WAITING_AUDIT.getId();
            if (isWaitAudit) {
                actions.add(MemberPermission.STORE_AUDIT.toAction());
            }
        }
        if (identity.hasPermission(MemberPermission.PLATFORM_AUDIT)) {
            boolean isWaitAudit = memberDO.getAuditStatusId().intValue() == AuditStatus.STORE_PASSED.getId();
            if (isWaitAudit) {
                actions.add(MemberPermission.PLATFORM_AUDIT.toAction());
            }
        }
        if (identity.hasPermission(MemberPermission.AUDIT_LOG)) {
            actions.add(MemberPermission.AUDIT_LOG.toAction());
        }

        if (isPlatformAuditPass) {
            if (identity.hasPermission(MemberPermission.LEVEL)) {
                actions.add(MemberPermission.LEVEL.toAction());
            }
            if (identity.hasPermission(MemberPermission.STATUS)) {
                actions.add(MemberPermission.STATUS.toAction());
            }
            if (identity.hasPermission(MemberPermission.AUTHORIZE)) {
                actions.add(MemberPermission.AUTHORIZE.toAction());
            }
        }

        return actions;
    }

    @Named("mapRoles")
    protected List<RoleBasicDTO> mapRoles(MemberDO memberDO) {
        List<RoleBasicDTO> roles = new ArrayList<>();
        MemberAccountDO memberAccountDO = accountDataRepository.findMemberPrimaryAccount(memberDO.getId());
        if (memberAccountDO == null) { return roles; }
        return roleManageClient.getRolesByUser(memberDO.getOrganizationId(), memberAccountDO.getId(), memberDO.getSystemId());
    }

    @Named("mapSystemName")
    protected String mapSystemName(MemberDO memberDO) {
        MemberSystemDO memberSystemDO = memberSystemDataRepository.findById(memberDO.getSystemId());
        return memberSystemDO != null ? memberSystemDO.getName() : "";
    }

    @Named("mapOrganizationName")
    protected String mapOrganizationName(MemberDO memberDO) {
        OrganizationDTO organizationDTO = organizationClient.getOrganizationById(memberDO.getOrganizationId());
        return organizationDTO != null ? organizationDTO.getName() : "";
    }

    @AfterMapping
    protected void fillAudit(MemberDO memberDO, @MappingTarget MemberDTO memberDTO) {
        AuditLogQuery query = new AuditLogQuery();
        query.setOrganizationId(memberDO.getOrganizationId());
        query.setModuleId(Module.MEMBER.getId());
        query.setDataId(memberDO.getId());
        AuditLogDTO auditLogDTO = auditLogClient.getLatestAuditLog(query);
        if (auditLogDTO != null) {
            memberDTO.setAuditorId(auditLogDTO.getAuditorId());
            memberDTO.setAuditorName(auditLogDTO.getAuditorName());
            memberDTO.setAuditReason(auditLogDTO.getReason());
            memberDTO.setAuditRemark(auditLogDTO.getRemark());
            memberDTO.setAuditTimeName(auditLogDTO.getAuditTime());
        }
    }

    @AfterMapping
    protected void fillAudit(MemberDO memberDO, @MappingTarget MemberDetailDTO memberDetailDTO) {
        AuditLogQuery query = new AuditLogQuery();
        query.setOrganizationId(memberDO.getOrganizationId());
        query.setModuleId(Module.MEMBER.getId());
        query.setDataId(memberDO.getId());
        AuditLogDTO auditLogDTO = auditLogClient.getLatestAuditLog(query);
        if (auditLogDTO != null) {
            memberDetailDTO.setAuditorId(auditLogDTO.getAuditorId());
            memberDetailDTO.setAuditorName(auditLogDTO.getAuditorName());
            memberDetailDTO.setAuditReason(auditLogDTO.getReason());
            memberDetailDTO.setAuditRemark(auditLogDTO.getRemark());
            memberDetailDTO.setAuditTimeName(auditLogDTO.getAuditTime());
        }
    }
}