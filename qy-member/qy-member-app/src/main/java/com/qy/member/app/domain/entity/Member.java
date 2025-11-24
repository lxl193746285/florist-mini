package com.qy.member.app.domain.entity;

import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.enums.MemberType;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.domain.valueobject.*;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 会员
 *
 * @author legendjw
 */
@Getter
@Builder
public class Member {
    /**
     * 会员id
     */
    private MemberId memberId;

    /**
     * 组织id
     */
    private OrganizationId organizationId;

    /**
     * 会员系统id
     */
    private MemberSystemId memberSystemId;

    /**
     * 名称
     */
    private PinyinName name;

    /**
     * 手机号
     */
    private PhoneNumber phone;

    /**
     * 头像
     */
    private Avatar avatar;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 等级
     */
    private MemberLevel level;

    /**
     * 地址
     */
    private Address address;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 状态
     */
    private EnableDisableStatus status;

    /**
     * 审核状态
     */
    private AuditStatus auditStatus;

    /**
     * 是否已开户
     */
    private boolean isOpenAccount;

    /**
     * 开户组织id
     */
    private Long openAccountId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 主账号
     */
    private MemberAccount primaryAccount;

    /**
     * 会员类型
     */
    private MemberType memberType;

    /**
     * 修改名称
     *
     * @param name
     */
    public void modifyName(String name) {
        this.name = new PinyinName(name);
    }

    /**
     * 修改性别
     *
     * @param gender
     */
    public void modifyGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 修改邀请码
     *
     * @param invitationCode
     */
    public void modifyInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    /**
     * 修改等级
     *
     * @param level
     */
    public void modifyLevel(MemberLevel level) {
        this.level = level;
    }

    /**
     * 修改地址
     *
     * @param address
     */
    public void modifyAddress(Address address) {
        this.address = address;
    }

    /**
     * 修改头像
     *
     * @param avatar
     */
    public void modifyAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * 修改状态
     *
     * @param status
     */
    public void modifyStatus(EnableDisableStatus status) {
        this.status = status;
    }

    /**
     * 修改审核状态
     *
     * @param auditStatus
     */
    public void modifyAuditStatus(AuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 修改备注
     *
     * @param remark
     */
    public void modifyRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 修改备注
     *
     * @param organizationId
     */
    public void modifyOrganization(OrganizationId organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 更换手机
     *
     * @param phone
     */
    public void changePhone(String phone) {
        PhoneNumber newPhoneNumber = new PhoneNumber(phone);
        if (newPhoneNumber.equals(this.phone)) {
            throw new ValidationException("请更换与旧手机号不同的手机号码");
        }
        this.phone = newPhoneNumber;
    }

    /**
     * 绑定开户组织
     *
     * @param openAccountId
     */
    public void bindOpenAccountId(Long openAccountId) {
        this.openAccountId = openAccountId;
        this.isOpenAccount = openAccountId != null && openAccountId.longValue() > 0L ? true : false;
    }
}