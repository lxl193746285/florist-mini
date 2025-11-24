package com.qy.system.app.useruniquecode.dto;

import com.qy.security.permission.action.Action;
import com.qy.system.app.comment.dto.ArkBaseDTO;
import com.qy.system.app.loginlog.enums.OperateType;
import com.qy.system.app.useruniquecode.enums.GroupType;
import com.qy.system.app.useruniquecode.enums.IsIgnore;
import com.qy.system.app.util.DateUtils;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@Data
public class UserUniqueCodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    private String phone;

    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;

    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private String clientId;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;

    /**
     * 1.经销商，2.公司员工
     */
    @ApiModelProperty(value = "1.经销商，2.公司员工")
    private Integer groupId;

    /**
     * 是否忽略验证，1.忽略，0.不忽略
     */
    @ApiModelProperty(value = "是否忽略验证，1.忽略，0.不忽略")
    private Integer isIgnore;

    @ApiModelProperty(value = "是否忽略验证，1.忽略，0.不忽略")
    private String isIgnoreName;

    @ApiModelProperty(value = "1.经销商，2.公司员工")
    private String groupName;

    @ApiModelProperty(value = "组织")
    private String organizationName;

    @ApiModelProperty(value = "用户")
    private String userName;
    /**
     * 创建时间
     */
    public LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新时间
     */
    public LocalDateTime updateTime;

    /**
     * 操作权限
     */
    private List<Action> actions = new ArrayList<>();

    public String updateTimeName;
    public String createTimeName;

    @ApiModelProperty(value = "极光推送唯一码")
    private String registrationId;

    public String getCreateTimeName() {
        if (createTime != null) {
            return DateUtils.localDateTimeToDateStr(createTime, DateUtils.dateTimeFormat);
        }
        return this.createTimeName;
    }

    public String getUpdateTimeName() {
        if (updateTime != null) {
            return DateUtils.localDateTimeToDateStr(updateTime, DateUtils.dateTimeFormat);
        }
        return this.updateTimeName;

    }

//    public String getIsIgnoreName() {
//        if (isIgnore != null) {
//            IsIgnore ignore = IsIgnore.getById(isIgnore);
//            return ignore == null ? "" : ignore.getName();
//        }
//        return isIgnoreName;
//    }

    public String getGroupName() {
        if (groupId != null) {
            GroupType groupType = GroupType.getById(groupId);
            return groupType == null ? "" : groupType.getName();
        }
        return groupName;
    }
}
