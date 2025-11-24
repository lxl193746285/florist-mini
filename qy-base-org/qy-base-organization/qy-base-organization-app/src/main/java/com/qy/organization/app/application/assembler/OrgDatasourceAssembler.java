package com.qy.organization.app.application.assembler;

import com.qy.member.api.client.MemberClient;
import com.qy.member.api.dto.MemberBasicDTO;
import com.qy.organization.app.application.dto.*;
import com.qy.organization.app.infrastructure.persistence.OrganizationDataRepository;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrgDatasourceDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织架构汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class OrgDatasourceAssembler {
    @Autowired
    private MemberClient memberClient;
    @Autowired
    private OrganizationDataRepository organizationDataRepository;

    @Mapping(source = "orgDatasourceDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "orgDatasourceDO", target = "creatorName", qualifiedByName = "mapCreatorName")
    @Mapping(source = "orgDatasourceDO", target = "orgName", qualifiedByName = "mapOrgName")
    @Mapping(source = "orgDatasourceDO", target = "updatorName", qualifiedByName = "mapUpdatorName")
    abstract public OrgDatasourceDTO toDTO(OrgDatasourceDO orgDatasourceDO, @Context MemberIdentity user);


    @Named("mapActions")
    protected List<Action> mapActions(OrgDatasourceDO orgDatasourceDO, @Context MemberIdentity user) {
        List<Action> actions = new ArrayList<>();
        if (user == null) {
            return actions;
        }
        return user.getActions("ark_org_datasource");
    }

    @Named("mapCreatorName")
    protected String mapCreatorName(OrgDatasourceDO orgDatasourceDO) {
        MemberBasicDTO memberBasicDTO = memberClient.getBasicMember(orgDatasourceDO.getCreatorId());
        if (memberBasicDTO == null) {
            return null;
        }
        return memberBasicDTO.getName();
    }

    @Named("mapUpdatorName")
    protected String mapUpdatorName(OrgDatasourceDO orgDatasourceDO) {
        MemberBasicDTO memberBasicDTO = memberClient.getBasicMember(orgDatasourceDO.getUpdatorId());
        if (memberBasicDTO == null) {
            return null;
        }
        return memberBasicDTO.getName();
    }

    @Named("mapOrgName")
    protected String mapOrgName(OrgDatasourceDO orgDatasourceDO) {
        OrganizationDO organizationDO = organizationDataRepository.findById(orgDatasourceDO.getOrgId());
        if (organizationDO == null) {
            return null;
        }
        return organizationDO.getName();
    }

}