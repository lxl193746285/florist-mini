package com.qy.organization.app.infrastructure.persistence.mybatis.converter;

import com.qy.organization.app.domain.entity.Organization;
import com.qy.organization.app.domain.enums.OpenAccountStatus;
import com.qy.organization.app.domain.enums.OrganizationStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.OrganizationDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 组织转化器
 *
 * @author legendjw
 */
@Mapper
public interface OrganizationConverter {
    /**
     * 组织实体转化为DO类
     *
     * @param organization
     * @return
     */
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "name.name", target = "name"),
            @Mapping(source = "name.namePinyin", target = "namePinyin"),
            @Mapping(source = "shortName.name", target = "shortName"),
            @Mapping(source = "shortName.namePinyin", target = "shortNamePinyin"),
            @Mapping(source = "industry.id", target = "industryId"),
            @Mapping(source = "industry.name", target = "industryName"),
            @Mapping(source = "scale.id", target = "scaleId"),
            @Mapping(source = "parentId.id", target = "parentId"),
            @Mapping(source = "scale.name", target = "scaleName"),
            @Mapping(source = "status.id", target = "statusId"),
            @Mapping(source = "status.name", target = "statusName"),
            @Mapping(source = "sourceData.source", target = "source"),
            @Mapping(source = "sourceData.sourceName", target = "sourceName"),
            @Mapping(source = "sourceData.dataId", target = "sourceDataId"),
            @Mapping(source = "sourceData.dataName", target = "sourceDataName"),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
            @Mapping(source = "updator.id", target = "updatorId"),
            @Mapping(source = "updator.name", target = "updatorName"),
            @Mapping(source = "openStatus.id", target = "openStatusId"),
            @Mapping(source = "openStatus.name", target = "openStatusName"),
    })
    OrganizationDO toDO(Organization organization);

    /**
     * DO类转化为组织实体
     *
     * @param organizationDO
     * @return
     */
    default Organization toEntity(OrganizationDO organizationDO) {
        if (organizationDO == null) {
            return null;
        }
        return Organization.builder()
                .id(new OrganizationId(organizationDO.getId()))
                .name(new PinyinName(organizationDO.getName(), organizationDO.getNamePinyin()))
                .shortName(new PinyinName(organizationDO.getShortName(), organizationDO.getShortNamePinyin()))
                .parentId(new ParentId(organizationDO.getParentId()))
                .logo(organizationDO.getLogo())
                .tel(organizationDO.getTel())
                .email(organizationDO.getEmail())
                .homepage(organizationDO.getHomepage())
                .address(organizationDO.getAddress())
                .industry(new OrganizationIndustry(organizationDO.getIndustryId(), organizationDO.getIndustryName()))
                .scale(new OrganizationScale(organizationDO.getScaleId(), organizationDO.getIndustryName()))
                .status(OrganizationStatus.getById(organizationDO.getStatusId()))
                .sourceData(new OrganizationSourceData(organizationDO.getSource(), organizationDO.getSourceName(), organizationDO.getSourceDataId(), organizationDO.getSourceDataName()))
                .creator(new User(organizationDO.getCreatorId(), organizationDO.getCreatorName()))
                .createTime(organizationDO.getCreateTime())
                .updator(new User(organizationDO.getUpdatorId(), organizationDO.getUpdatorName()))
                .updateTime(organizationDO.getUpdateTime())
                .openStatus(OpenAccountStatus.getById(organizationDO.getOpenStatusId()))

                .build();
    }
}