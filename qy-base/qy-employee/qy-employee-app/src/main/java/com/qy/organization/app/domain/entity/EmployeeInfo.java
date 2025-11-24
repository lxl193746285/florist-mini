package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Aggregate;
import com.qy.organization.app.application.command.UpdateEmployeeCommand;
import com.qy.organization.app.application.command.UpdateIdentityEmployeeCommand;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.security.session.EmployeeIdentity;
import com.qy.organization.app.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工资料
 *
 * @author legendjw
 */
@Getter
@Setter
@Builder
public class EmployeeInfo implements Aggregate {
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
     * 分类
     */
    private EmployeeCategory category;

    /**
     * 是否在通讯录显示: 1: 显示 0: 隐藏
     */
    private Byte isShowBook;

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
     * 入职时间
     */
    private LocalDate entryTime;

    /**
     * 转正时间
     */
    private LocalDate conversionTime;

    /**
     * 离职时间
     */
    private LocalDate leaveTime;

    /**
     * 离职原因id
     */
    private Integer leaveReasonId;

    /**
     * 离职原因名称
     */
    private String leaveReasonName;

    /**
     * 离职经办人
     */
    private User leaveOperator;

    /**
     * 离职交接内容
     */
    private String leaveHandoverContent;

    /**
     * 离职备注
     */
    private String leaveRemark;

    /**
     * QQ
     */
    private String qq;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 身份证号
     */
    private IdNumber idNumber;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 政治面貌
     */
    private String politicalStatus;

    /**
     * 婚否
     */
    private String maritalStatus;

    /**
     * 护照号
     */
    private String passportNo;

    /**
     * 内部任职经历
     */
    private String internalExperience;

    /**
     * 从业经历
     */
    private String workingExperience;

    /**
     * 紧急联系人
     */
    private String emergencyContactPerson;

    /**
     * 紧急联系方式
     */
    private String emergencyContact;

    /**
     * 紧急联系人与本人关系
     */
    private String emergencyContactRelationship;

    /**
     * 家庭成员
     */
    private String memberOfFamily;

    /**
     * 教育经历
     */
    private String educationExperience;

    /**
     * 家庭住址
     */
    private Address address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 来源类型（0:蓝牙员工,1:人事员工）
     */
    private Integer sourceType;


    /**
     * 员工在职状态 1 在职 0离职
     */
    private Integer jobStatusId;


    /**
     * 员工在职状态 1 在职 0离职
     */
    private String jobStatusName;

    private String username;

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
     * 修改在职离职状态
     */
    public void modifyJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * 修改在职离职状态
     */
    public void modifyJobStatus(
            JobStatus jobStatus,
            LocalDate leaveTime,
            Integer leaveReasonId,
            String leaveReasonName,
            User leaveOperator,
            String leaveHandoverContent,
            String leaveRemark
    ) {
        this.jobStatus = jobStatus;
        this.leaveTime = leaveTime;
        this.leaveOperator = leaveOperator;
        this.leaveReasonId = leaveReasonId;
        this.leaveReasonName = leaveReasonName;
        this.leaveHandoverContent = leaveHandoverContent;
        this.leaveRemark = leaveRemark;
    }

    /**
     * 修改员工信息
     *
     * @param command
     */
    public void modifyInfo(UpdateEmployeeCommand command) {
        EmployeeIdentity identity = command.getEmployee();

        this.name = new PinyinName(command.getName());
        this.phone = new PhoneNumber(command.getPhone());
        this.email = StringUtils.isNotBlank(command.getEmail()) ? new Email(command.getEmail()) : null;
        this.gender = command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null;
        this.category = command.getCategoryId() != null ? new EmployeeCategory(command.getCategoryId(), command.getCategoryName()) : null;
        this.number = command.getNumber();
        this.jobTitle = command.getJobTitle();
        this.supervisor = command.getSupervisorId().longValue() != 0L ? new Supervisor(command.getSupervisorId(), command.getSupervisorName()) : null;
        this.isShowBook = command.getIsShowBook();
        this.entryTime = command.getEntryTime();
        this.conversionTime = command.getConversionTime();
        this.leaveTime = command.getLeaveTime();
        this.leaveReasonId = command.getLeaveReasonId();
        this.leaveReasonName = command.getLeaveReasonName();
        this.leaveOperator = new User(command.getLeaveOperatorId(), command.getLeaveOperatorName());
        this.leaveHandoverContent = command.getLeaveHandoverContent();
        this.leaveRemark = command.getLeaveRemark();
        this.qq = command.getQq();
        this.wechat = command.getWechat();
        this.idNumber = StringUtils.isNotBlank(command.getIdNumber()) ? new IdNumber(command.getIdNumber()) : null;
        this.birthday = command.getBirthday();
        this.nation = command.getNation();
        this.politicalStatus = command.getPoliticalStatus();
        this.maritalStatus = command.getMaritalStatus();
        this.passportNo = command.getPassportNo();
        this.internalExperience = command.getInternalExperience();
        this.workingExperience = command.getWorkingExperience();
        this.emergencyContactPerson = command.getEmergencyContactPerson();
        this.emergencyContact = command.getEmergencyContact();
        this.emergencyContactRelationship = command.getEmergencyContactRelationship();
        this.memberOfFamily = command.getMemberOfFamily();
        this.educationExperience = command.getEducationExperience();
        this.jobStatusId=command.getJobStatusId();
        this.jobStatusName=command.getJobStatusName();
        this.address = Address.builder()
                .provinceId(command.getProvinceId())
                .provinceName(command.getProvinceName())
                .cityId(command.getCityId())
                .cityName(command.getCityName())
                .areaId(command.getAreaId())
                .areaName(command.getAreaName())
                .streetId(command.getStreetId())
                .streetName(command.getStreetName())
                .address(command.getAddress())
                .build();
        this.remark = command.getRemark();
        this.updateTime = LocalDateTime.now();
        this.updator = new User(identity.getId(), identity.getName());
        this.username = command.getUsername();
    }

    /**
     * 修改基本信息
     *
     * @param command
     */
    public void modifyInfo(UpdateIdentityEmployeeCommand command) {
        EmployeeIdentity identity = command.getIdentity();
        this.name = new PinyinName(command.getName());
        //this.phone = new PhoneNumber(command.getPhone());
        //this.email = StringUtils.isNotBlank(command.getEmail()) ? new Email(command.getEmail()) : null;
        this.gender = command.getGenderId() != null ? new Gender(command.getGenderId(), command.getGenderName()) : null;
        this.updateTime = LocalDateTime.now();
        this.updator = new User(identity.getId(), identity.getName());
    }

    /**
     * 是否已经创建账号
     *
     * @return
     */
    public boolean hasCreateAccount() {
        return this.userId != null && this.userId.getId() != 0L;
    }
}