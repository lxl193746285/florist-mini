package com.qy.member.app.domain.entity;

import com.qy.member.app.domain.enums.AccountType;
import com.qy.member.app.domain.enums.AuditStatus;
import com.qy.member.app.domain.valueobject.*;
import com.qy.member.app.domain.valueobject.*;
import com.qy.rest.enums.EnableDisableStatus;
import com.qy.rest.exception.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 会员账号
 *
 * @author legendjw
 */
@Getter
@Builder
public class MemberAccount {
    /**
     * 会员id
     */
    private MemberId memberId;

    /**
     * 账号id
     */
    private AccountId accountId;

    /**
     * 会员系统id
     */
    private MemberSystemId memberSystemId;

    /**
     * 账号类型
     */
    private AccountType type;

    /**
     * 员工id
     */
    private EmployeeId employeeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号
     */
    private PhoneNumber phone;

    /**
     * 密码
     */
    private Password password;

    /**
     * 头像
     */
    private Avatar avatar;

    /**
     * 状态
     */
    private EnableDisableStatus status;

    /**
     * 手机唯一id
     */
    private String mobileId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private User creator;

    /**
     * 会员状态
     */
    private EnableDisableStatus memberStatus;

    /**
     * 审核状态
     */
    private AuditStatus auditStatus;

    private OrganizationId organizationId;

    /**
     * 修改名称
     *
     * @param name
     */
    public void modifyName(String name) {
        this.name = name;
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
     * 修改会员id
     *
     * @param memberId
     */
    public void modifyMemberId(MemberId memberId) {
        this.memberId = memberId;
    }

    /**
     * 修改会员id
     *
     * @param organizationId
     */
    public void modifyOrganizationId(OrganizationId organizationId) {
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
     * 修改密码
     *
     * @param password
     */
    public void modifyPassword(String password) {
        this.password = new Password(password);
    }

    /**
     * 修改手机唯一id
     *
     * @param mobileId
     */
    public void modifyMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    /**
     * 修改状态
     *
     * @param status
     */
    public void modifyStatus(EnableDisableStatus status) {
        this.status = status;
    }
}