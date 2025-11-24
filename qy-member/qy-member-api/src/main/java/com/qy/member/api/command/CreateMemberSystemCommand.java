package com.qy.member.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建会员系统命令
 *
 * @author legendjw
 */
@Data
public class CreateMemberSystemCommand implements Serializable {
    /**
     * 组织id
     */
    @NotNull(message = "请选择组织")
    private Long organizationId;

    /**
     * 系统类型id 系统代码表: member_system_type
     */
    @NotNull(message = "请选择系统类型")
    private Integer typeId;

    /**
     * 会员类型id 系统代码表: member_type
     */
    @NotNull(message = "请选择会员类型")
    private Integer memberTypeId;

    /**
     * 名称
     */
    @NotBlank(message = "请输入系统名称")
    private String name;

    /**
     * 会员注册是否需要审核
     */
    private Byte isMemberAudit;

    /**
     * 状态id 系统代码表: common_status
     */
    @NotNull(message = "请选择状态")
    private Integer statusId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 绑定的微信应用
     */
    private List<MemberSystemWeixinAppForm> weixinApps = new ArrayList<>();
}