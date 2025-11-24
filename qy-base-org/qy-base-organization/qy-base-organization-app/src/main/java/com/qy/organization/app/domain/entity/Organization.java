package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.organization.app.application.command.UpdateOrganizationCommand;
import com.qy.organization.app.domain.enums.OpenAccountStatus;
import com.qy.organization.app.domain.enums.OrganizationStatus;
import com.qy.organization.app.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

/**
 * 组织实体
 *
 * @author legendjw
 */
@Getter
@Builder
public class Organization implements Entity {
    /**
     * 组织id
     */
    private OrganizationId id;
    /**
     * 组织名称
     */
    private PinyinName name;
    /**
     * 组织简称
     */
    private PinyinName shortName;
    /**
     * 组织简称
     */
    private ParentId parentId;
    /**
     * 组织logo
     */
    private String logo;
    /**
     * 组织电话
     */
    private String tel;
    /**
     * 组织联系人
     */
    private String contactName;
    /**
     * 组织邮箱
     */
    private String email;
    /**
     * 组织主页
     */
    private String homepage;
    /**
     * 组织地址
     */
    private String address;
    /**
     * 组织行业
     */
    private OrganizationIndustry industry;
    /**
     * 组织规模
     */
    private OrganizationScale scale;
    /**
     * 状态
     */
    private OrganizationStatus status;
    /**
     * 来源
     */
    private OrganizationSourceData sourceData;
    /**
     * 创建人
     */
    private User creator;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改人
     */
    private User updator;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 开户状态
     */
    private OpenAccountStatus openStatus;

    /**
     * 修改组织信息
     *
     * @param command
     */
    public void modifyInfo(UpdateOrganizationCommand command) {
        //if (!isCreator(command.getUpdatorId())) {
        //    throw new ValidationException("您没有权限修改组织信息");
        //}
        this.name = new PinyinName(command.getName());
        this.shortName = StringUtils.isNotBlank(command.getShortName()) ? new PinyinName(command.getShortName()) : null;
        if (StringUtils.isNotBlank(command.getLogo())) {
            this.logo = command.getLogo();
        }
        this.tel = command.getTel();
        this.contactName = command.getContactName();
        this.email = command.getEmail();
        this.homepage = command.getHomepage();
        this.address = command.getAddress();
        this.industry = new OrganizationIndustry(command.getIndustryId(), command.getIndustryName());
        this.scale = new OrganizationScale(command.getScaleId(), command.getScaleName());
        this.updateTime = LocalDateTime.now();
        this.updator = command.getUpdatorId() != null ? new User(command.getUpdatorId(), command.getUpdatorName()) : null;
        this.parentId = command.getParentId() != null ? new ParentId(command.getParentId()) : null;
    }

    /**
     * 是否是创建人
     *
     * @param userId
     * @return
     */
    public boolean isCreator(Long userId) {
        return userId.equals(this.getCreator().getId());
    }
}