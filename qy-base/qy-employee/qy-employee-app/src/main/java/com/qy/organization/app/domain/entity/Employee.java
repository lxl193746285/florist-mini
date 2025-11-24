package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.rest.exception.ValidationException;
import com.qy.organization.app.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工
 *
 * @author legendjw
 */
@Getter
@Builder
public class Employee implements Entity {
    /**
     * 员工id
     */
    private EmployeeId id;

    /**
     * 用户id
     */
    private UserId userId;

    /**
     * 组织id
     */
    private OrganizationId organizationId;

    /**
     * 部门
     */
    private Department department;

    /**
     * 岗位
     */
    private List<Job> jobs;

    /**
     * 姓名
     */
    private PinyinName name;

    /**
     * 头像
     */
    private Avatar avatar;

    /**
     * 手机号
     */
    private PhoneNumber phone;

    /**
     * 邮箱
     */
    private Email email;

    /**
     * 性别id
     */
    private Gender gender;

    /**
     * 工号
     */
    private String number;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 直属领导id
     */
    private Supervisor supervisor;

    /**
     * 在职状态
     */
    private JobStatus jobStatus;

    /**
     * 组织身份类型
     */
    private EmployeeIdentityType identityType;

    /**
     * 权限类型
     */
    private EmployeePermissionType permissionType;

    /**
     * 授权项
     */
    private String authItem;

    /**
     * 创建人
     */
    private User creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private User updator;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 会员id
     */
    private MemberId memberId;

    /**
     * 修改员工头像
     *
     * @param avatar
     */
    public void modifyAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * 修改员工部门
     *
     * @param department
     */
    public void modifyDepartment(Department department) {
        this.department = department;
    }

    /**
     * 修改员工岗位
     *
     * @param jobs
     */
    public void modifyJob(List<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     * 修改员工上级领导
     *
     * @param supervisor
     */
    public void modifySupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * 修改身份类型
     *
     * @param identityType
     */
    public void modifyIdentityType(EmployeeIdentityType identityType) {
        if (this.identityType.equals(EmployeeIdentityType.CREATOR)) {
            throw new ValidationException("组织创始人不能更改身份");
        }
        if (identityType.equals(EmployeeIdentityType.CREATOR)) {
            throw new ValidationException("员工不能更改为组织创始人身份");
        }
        this.identityType = identityType;
    }

    /**
     * 修改权限类型
     *
     * @param permissionType
     */
    public void modifyPermissionType(EmployeePermissionType permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * 取消创始人身份
     */
    public void cancelCreatorIdentity() {
        if (!this.identityType.equals(EmployeeIdentityType.CREATOR)) {
            throw new ValidationException("不是创始人无法取消创始人身份");
        }
        this.identityType = EmployeeIdentityType.EMPLOYEE;
    }

    /**
     * 转为创始人身份
     */
    public void changeToCreatorIdentity() {
        this.identityType = EmployeeIdentityType.CREATOR;
    }

    /**
     * 绑定一个账号
     *
     * @param userId
     */
    public void bindAccount(Long userId) {
        this.userId = new UserId(userId);
    }

    /**
     * 绑定一个会员
     *
     * @param memberId
     */
    public void bindMember(Long memberId) {
        this.memberId = new MemberId(memberId);
    }

    /**
     * 是否已经创建账号
     *
     * @return
     */
    public boolean hasCreateAccount() {
        return this.userId != null && this.userId.getId() != 0L;
    }

    /**
     * 绑定一个授权项
     *
     * @param authItem
     */
    public void bindAuthItem(String authItem) {
        this.authItem = authItem;
    }

    /**
     * 生成一个授权项
     *
     * @return
     */
    public String generateAuthItem() {
        return String.format("employee_%s", getId().getId());
    }
}