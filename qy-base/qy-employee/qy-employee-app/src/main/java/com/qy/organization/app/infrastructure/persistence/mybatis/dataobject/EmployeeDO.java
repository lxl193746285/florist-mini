package com.qy.organization.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.security.interfaces.GetOrganizationDepartmentCreator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 组织员工
 * </p>
 *
 * @author legendjw
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_uims_org_employee")
public class EmployeeDO implements Serializable, GetOrganizationDepartmentCreator {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String namePinyin;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别id: 1: 男性 0: 女性
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

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
    private Long supervisorId;

    /**
     * 直属领导名称
     */
    private String supervisorName;

    /**
     * 在职状态id: 1: 在职 0: 离职
     */
    private Integer jobStatusId;

    /**
     * 在职状态名称
     */
    private String jobStatusName;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 是否在通讯录显示: 1: 显示 0: 隐藏
     */
    private Byte isShowBook;

    /**
     * 组织身份类型id: 0: 员工 1: 创建人 2: 管理员 3: 操作员
     */
    private Integer identityTypeId;

    /**
     * 组织身份类型名称
     */
    private String identityTypeName;

    /**
     * 权限类型id: 1: 权限组 2: 独立权限
     */
    private Integer permissionTypeId;

    /**
     * 权限类型名称
     */
    private String permissionTypeName;

    /**
     * 授权项
     */
    private String authItem;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


    private String username;

    /**
     * 会员id
     */
    private Long memberId;

}
