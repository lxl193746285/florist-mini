package com.qy.organization.app.infrastructure.persistence.mybatis.converter;

import com.qy.organization.app.domain.entity.EmployeeInfo;
import com.qy.organization.app.domain.entity.Department;
import com.qy.organization.app.domain.entity.Employee;
import com.qy.organization.app.domain.entity.Job;
import com.qy.organization.app.domain.enums.EmployeeIdentityType;
import com.qy.organization.app.domain.enums.EmployeePermissionType;
import com.qy.organization.app.domain.enums.JobStatus;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.domain.valueobject.*;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeDO;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.EmployeeInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 员工转化器
 *
 * @author legendjw
 */
@Mapper
public interface EmployeeConverter {
    /**
     * 员工实体转化为DO类
     *
     * @param employee
     * @return
     */
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "userId.id", target = "userId"),
            @Mapping(source = "organizationId.id", target = "organizationId"),
            @Mapping(source = "department.id.id", target = "departmentId"),
            @Mapping(source = "name.name", target = "name"),
            @Mapping(source = "name.namePinyin", target = "namePinyin"),
            @Mapping(source = "avatar.url", target = "avatar"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "email.address", target = "email"),
            @Mapping(source = "gender.id", target = "genderId"),
            @Mapping(source = "gender.name", target = "genderName"),
            @Mapping(source = "supervisor.id", target = "supervisorId"),
            @Mapping(source = "supervisor.name", target = "supervisorName"),
            @Mapping(source = "jobStatus.id", target = "jobStatusId"),
            @Mapping(source = "jobStatus.name", target = "jobStatusName"),
            @Mapping(source = "identityType.id", target = "identityTypeId"),
            @Mapping(source = "identityType.name", target = "identityTypeName"),
            @Mapping(source = "permissionType.id", target = "permissionTypeId", defaultValue = "0"),
            @Mapping(source = "permissionType.name", target = "permissionTypeName", defaultValue = ""),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
            @Mapping(source = "updator.id", target = "updatorId"),
            @Mapping(source = "updator.name", target = "updatorName"),
            @Mapping(source = "memberId.id", target = "memberId"),
    })
    EmployeeDO toDO(Employee employee);

    /**
     * 员工资料实体转化为DO类
     *
     * @param employeeInfo
     * @return
     */
    @Mappings({
            @Mapping(source = "id.id", target = "id"),
            @Mapping(source = "userId.id", target = "userId"),
            @Mapping(source = "organizationId.id", target = "organizationId"),
            @Mapping(source = "department.id.id", target = "departmentId"),
            @Mapping(source = "name.name", target = "name"),
            @Mapping(source = "name.namePinyin", target = "namePinyin"),
            @Mapping(source = "avatar.url", target = "avatar"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "email.address", target = "email"),
            @Mapping(source = "gender.id", target = "genderId"),
            @Mapping(source = "gender.name", target = "genderName"),
            @Mapping(source = "supervisor.id", target = "supervisorId"),
            @Mapping(source = "supervisor.name", target = "supervisorName"),
            @Mapping(source = "jobStatus.id", target = "jobStatusId"),
            @Mapping(source = "jobStatus.name", target = "jobStatusName"),
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "identityType.id", target = "identityTypeId"),
            @Mapping(source = "identityType.name", target = "identityTypeName"),
            @Mapping(source = "permissionType.id", target = "permissionTypeId", defaultValue = "0"),
            @Mapping(source = "permissionType.name", target = "permissionTypeName", defaultValue = ""),
            @Mapping(source = "creator.id", target = "creatorId"),
            @Mapping(source = "creator.name", target = "creatorName"),
            @Mapping(source = "updator.id", target = "updatorId"),
            @Mapping(source = "updator.name", target = "updatorName"),
            @Mapping(source = "memberId.id", target = "memberId"),
    })
    EmployeeDO toDO(EmployeeInfo employeeInfo);

    /**
     * 员工转换为员工信息类
     *
     * @param employeeInfo
     * @return
     */
    @Mappings({
            @Mapping(source = "id.id", target = "employeeId"),
            @Mapping(source = "idNumber.idNumber", target = "idNumber"),
            @Mapping(source = "address.provinceId", target = "provinceId"),
            @Mapping(source = "address.provinceName", target = "provinceName"),
            @Mapping(source = "address.cityId", target = "cityId"),
            @Mapping(source = "address.cityName", target = "cityName"),
            @Mapping(source = "address.areaId", target = "areaId"),
            @Mapping(source = "address.areaName", target = "areaName"),
            @Mapping(source = "address.streetId", target = "streetId"),
            @Mapping(source = "address.streetName", target = "streetName"),
            @Mapping(source = "address.address", target = "address"),
            @Mapping(source = "leaveOperator.id", target = "leaveOperatorId"),
            @Mapping(source = "leaveOperator.name", target = "leaveOperatorName"),
    })
    EmployeeInfoDO toInfoDO(EmployeeInfo employeeInfo);

    /**
     * DO类转化为员工实体
     *
     * @param employeeDO
     * @return
     */
    default Employee toEntity(EmployeeDO employeeDO, Department department, List<Job> jobs) {
        return Employee.builder()
                .id(new EmployeeId(employeeDO.getId()))
                .userId(employeeDO.getUserId().longValue() != 0L ? new UserId(employeeDO.getUserId()) : null)
                .organizationId(new OrganizationId(employeeDO.getOrganizationId()))
                .department(department)
                .jobs(jobs)
                .name(new PinyinName(employeeDO.getName(), employeeDO.getNamePinyin()))
                .avatar(new Avatar(employeeDO.getAvatar()))
                .phone(new PhoneNumber(employeeDO.getPhone()))
                .email(StringUtils.isNotBlank(employeeDO.getEmail()) ? new Email(employeeDO.getEmail()) : null)
                .gender(new Gender(employeeDO.getGenderId(), employeeDO.getGenderName()))
                .number(employeeDO.getNumber())
                .jobTitle(employeeDO.getJobTitle())
                .supervisor(employeeDO.getSupervisorId().longValue() != 0L ? new Supervisor(employeeDO.getSupervisorId(), employeeDO.getSupervisorName()) : null)
                .jobStatus(JobStatus.getById(employeeDO.getJobStatusId()))
                .identityType(EmployeeIdentityType.getById(employeeDO.getIdentityTypeId()))
                .permissionType(employeeDO.getPermissionTypeId().intValue() != 0 ? EmployeePermissionType.getById(employeeDO.getPermissionTypeId()) : null)
                .creator(new User(employeeDO.getCreatorId(), employeeDO.getCreatorName()))
                .createTime(employeeDO.getCreateTime())
                .updator(employeeDO.getUpdatorId().longValue() != 0L ? new User(employeeDO.getUpdatorId(), employeeDO.getUpdatorName()) : null)
                .updateTime(employeeDO.getUpdateTime())
                .memberId(employeeDO.getMemberId().longValue() != 0L ? new MemberId(employeeDO.getMemberId()) : null)
                .build();
    }

    /**
     * DO类转化为员工实体
     *
     * @param employeeDO
     * @return
     */
    default EmployeeInfo toEntity(EmployeeDO employeeDO, EmployeeInfoDO employeeInfoDO, Department department, List<Job> jobs) {
        return EmployeeInfo.builder()
                .id(new EmployeeId(employeeDO.getId()))
                .userId(employeeDO.getUserId().longValue() != 0L ? new UserId(employeeDO.getUserId()) : null)
                .organizationId(new OrganizationId(employeeDO.getOrganizationId()))
                .department(department)
                .jobs(jobs)
                .name(new PinyinName(employeeDO.getName(), employeeDO.getNamePinyin()))
                .avatar(new Avatar(employeeDO.getAvatar()))
                .phone(new PhoneNumber(employeeDO.getPhone(),true))
                .email(StringUtils.isNotBlank(employeeDO.getEmail()) ? new Email(employeeDO.getEmail(),true) : null)
                .gender(new Gender(employeeDO.getGenderId(), employeeDO.getGenderName()))
                .number(employeeDO.getNumber())
                .jobTitle(employeeDO.getJobTitle())
                .supervisor(employeeDO.getSupervisorId().longValue() != 0L ? new Supervisor(employeeDO.getSupervisorId(), employeeDO.getSupervisorName()) : null)
                .jobStatus(JobStatus.getById(employeeDO.getJobStatusId()))
                .category(new EmployeeCategory(employeeDO.getCategoryId(), employeeDO.getCategoryName()))
                .isShowBook(employeeDO.getIsShowBook())
                .identityType(EmployeeIdentityType.getById(employeeDO.getIdentityTypeId()))
                .permissionType(employeeDO.getPermissionTypeId().intValue() != 0 ? EmployeePermissionType.getById(employeeDO.getPermissionTypeId()) : null)
                .creator(new User(employeeDO.getCreatorId(), employeeDO.getCreatorName()))
                .createTime(employeeDO.getCreateTime())
                .updator(employeeDO.getUpdatorId().longValue() != 0L ? new User(employeeDO.getUpdatorId(), employeeDO.getUpdatorName()) : null)
                .updateTime(employeeDO.getUpdateTime())
                .entryTime(employeeInfoDO.getEntryTime())
                .conversionTime(employeeInfoDO.getConversionTime())
                .leaveTime(employeeInfoDO.getLeaveTime())
                .leaveReasonId(employeeInfoDO.getLeaveReasonId())
                .leaveReasonName(employeeInfoDO.getLeaveReasonName())
                .leaveOperator(new User(employeeInfoDO.getLeaveOperatorId(), employeeInfoDO.getLeaveOperatorName()))
                .leaveHandoverContent(employeeInfoDO.getLeaveHandoverContent())
                .leaveRemark(employeeInfoDO.getLeaveRemark())
                .qq(employeeInfoDO.getQq())
                .wechat(employeeInfoDO.getWechat())
                .idNumber(StringUtils.isNotBlank(employeeInfoDO.getIdNumber()) ? new IdNumber(employeeInfoDO.getIdNumber()) : null)
                .birthday(employeeInfoDO.getBirthday())
                .nation(employeeInfoDO.getNation())
                .politicalStatus(employeeInfoDO.getPoliticalStatus())
                .maritalStatus(employeeInfoDO.getMaritalStatus())
                .passportNo(employeeInfoDO.getPassportNo())
                .internalExperience(employeeInfoDO.getInternalExperience())
                .workingExperience(employeeInfoDO.getWorkingExperience())
                .emergencyContactPerson(employeeInfoDO.getEmergencyContactPerson())
                .emergencyContact(employeeInfoDO.getEmergencyContact())
                .emergencyContactRelationship(employeeInfoDO.getEmergencyContactRelationship())
                .memberOfFamily(employeeInfoDO.getMemberOfFamily())
                .educationExperience(employeeInfoDO.getEducationExperience())
                .address(Address.builder()
                        .provinceId(employeeInfoDO.getProvinceId())
                        .provinceName(employeeInfoDO.getProvinceName())
                        .cityId(employeeInfoDO.getCityId())
                        .cityName(employeeInfoDO.getCityName())
                        .areaId(employeeInfoDO.getAreaId())
                        .areaName(employeeInfoDO.getAreaName())
                        .streetId(employeeInfoDO.getStreetId())
                        .streetName(employeeInfoDO.getStreetName())
                        .address(employeeInfoDO.getAddress())
                        .build())
                .remark(employeeInfoDO.getRemark())
                .memberId(employeeDO.getMemberId().longValue() != 0L ? new MemberId(employeeDO.getMemberId()) : null)
                .build();
    }
}