package com.qy.member.api.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员DTO
 *
 * @author legendjw
 */
@Data
public class MemberDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称拼音
     */
    private String namePinyin;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别id
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 等级id
     */
    private Integer levelId;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 省份代码
     */
    private Integer provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市代码
     */
    private Integer cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地区代码
     */
    private Integer areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 街道代码
     */
    private Integer streetId;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 状态id
     */
    private Integer statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 审核状态id
     */
    private Integer auditStatusId;

    /**
     * 审核状态名称
     */
    private String auditStatusName;

    /**
     * 审核人id
     */
    private Long auditorId;

    /**
     * 审核人名称
     */
    private String auditorName;

    /**
     * 审核时间
     */
    private String auditTimeName;

    /**
     * 审核拒绝原因
     */
    private String auditReason;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 是否已开户 1: 已开户 0: 未开户
     */
    private Byte isOpenAccount;

    /**
     * 开户组织id
     */
    private Long openAccountId;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 备注
     */
    private String remark;

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
    private String createTimeName;

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
    private String updateTimeName;

    private Integer memberType;

    /**
     * 权限组
     */
    private List<RoleBasicDTO> roles = new ArrayList<>();

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
