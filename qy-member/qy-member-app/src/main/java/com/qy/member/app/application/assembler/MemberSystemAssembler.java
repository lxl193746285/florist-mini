package com.qy.member.app.application.assembler;

import com.qy.member.app.application.dto.MemberSystemBasicDTO;
import com.qy.member.app.application.dto.MemberSystemDTO;
import com.qy.member.app.application.dto.MemberSystemDetailDTO;
import com.qy.member.app.application.dto.MemberSystemWeixinAppDTO;
import com.qy.member.app.application.repository.MemberSystemDataRepository;
import com.qy.member.app.application.security.MemberPermission;
import com.qy.member.app.application.security.MemberSystemPermission;
import com.qy.member.app.application.service.MemberSystemWeixinAppQueryService;
import com.qy.member.app.infrastructure.persistence.mybatis.dataobject.MemberSystemDO;
import com.qy.organization.api.dto.OrganizationBasicDTO;
import com.qy.rbac.api.client.AppClient;
import com.qy.rbac.api.dto.AppBasicDTO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import com.qy.util.CharacterUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员系统汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class MemberSystemAssembler {
    @Autowired
    private MemberSystemDataRepository memberSystemDataRepository;
    @Autowired
    private MemberSystemPermission memberSystemPermission;
    @Autowired
    private MemberPermission memberPermission;
    @Autowired
    private MemberSystemWeixinAppQueryService memberSystemWeixinAppQueryService;
    @Autowired
    private AppClient appClient;

    public abstract MemberSystemBasicDTO toBasicDTO(MemberSystemDO memberSystemDO);

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "memberSystemDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "memberSystemDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "memberSystemDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract MemberSystemDTO toDTO(
            MemberSystemDO memberSystemDO,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context Identity identity
    );

    @Mapping(source = "organizationId", target = "organizationName", qualifiedByName = "mapOrganizationName")
    @Mapping(source = "memberSystemDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "memberSystemDO", target = "weixinApps", qualifiedByName = "mapWeixinApps")
    @Mapping(source = "memberSystemDO", target = "apps", qualifiedByName = "mapApps")
    @Mapping(source = "memberSystemDO.createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "memberSystemDO.updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract MemberSystemDetailDTO toDetailDTO(
            MemberSystemDO memberSystemDO,
            @Context List<OrganizationBasicDTO> organizationDTOS,
            @Context Identity identity
    );

    @Named("mapOrganizationName")
    public String mapOrganizationName(Long organizationId, @Context List<OrganizationBasicDTO> organizationDTOS) {
        if (organizationDTOS == null) { return ""; }
        return organizationDTOS.stream().filter(p -> p.getId().equals(organizationId)).findFirst().map(
                o -> StringUtils.isNotBlank(o.getShortName()) ? o.getShortName() : o.getName()
        ).orElse("");
    }

    @Named("mapActions")
    protected List<Action> mapActions(MemberSystemDO memberSystemDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        if (identity == null) { return actions; }
        if (memberSystemPermission.hasPermission(identity, MemberSystemPermission.VIEW, memberSystemDO.getId())) {
            actions.add(MemberSystemPermission.VIEW.toAction());
        }
        if (memberSystemPermission.hasPermission(identity, MemberSystemPermission.MEMBER_LIST, memberSystemDO.getId())) {
           actions.add(MemberSystemPermission.MEMBER_LIST.toAction());
        }
        if (memberSystemPermission.hasPermission(identity, MemberSystemPermission.ROLE_LIST, memberSystemDO.getId())) {
            actions.add(MemberSystemPermission.ROLE_LIST.toAction());
        }
        if (memberSystemPermission.hasPermission(identity, MemberSystemPermission.UPDATE, memberSystemDO.getId())) {
            actions.add(MemberSystemPermission.UPDATE.toAction());
        }
        if (memberSystemPermission.hasPermission(identity, MemberSystemPermission.DELETE, memberSystemDO.getId())) {
            actions.add(MemberSystemPermission.DELETE.toAction());
        }
        return actions;
    }

    @Named("mapWeixinApps")
    protected List<MemberSystemWeixinAppDTO> mapWeixinApps(MemberSystemDO memberSystemDO, @Context Identity identity) {
        return memberSystemWeixinAppQueryService.getMemberSystemWeixinAppBySystemId(memberSystemDO.getId());
    }

    @Named("mapApps")
    protected List<AppBasicDTO> mapApps(MemberSystemDO memberSystemDO) {
        return appClient.getAppByIds(new ArrayList<>());
    }
}