package com.qy.organization.app.application.command;

import com.qy.organization.app.application.dto.MenuCheckWithPermissionDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 设置员工身份权限命令
 *
 * @author legendjw
 */
@Data
public class SetEmployeeIdentityCommand implements Serializable {

    @ApiModelProperty("员工id集合")
    private List<Long> ids;
    /**
     * 员工id
     */
    @ApiModelProperty("员工id")
    private Long id;

    /**
     * 组织身份类型id
     */
    @ApiModelProperty("组织身份类型id")
    @NotNull(message = "请选择身份类型")
    private Integer identityTypeId;

    /**
     * 权限类型id
     */
    @ApiModelProperty("权限类型id")
    private Integer permissionTypeId;

    /**
     * 权限组id集合
     */
    @ApiModelProperty("权限组id集合")
    private List<Long> roleIds;

    /**
     * 授权的权限节点
     */
    @ApiModelProperty("授权的权限节点")
    private List<MenuCheckWithPermissionDTO> menuPermissions = new ArrayList<>();
}