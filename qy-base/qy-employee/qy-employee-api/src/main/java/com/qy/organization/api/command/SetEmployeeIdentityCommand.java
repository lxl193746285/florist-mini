package com.qy.organization.api.command;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 设置员工身份权限命令
 *
 * @author legendjw
 */
@Data
public class SetEmployeeIdentityCommand implements Serializable {
    /**
     * 员工id
     */
    private Long id;

    /**
     * 组织身份类型id
     */
    @NotNull(message = "请选择身份类型")
    private Integer identityTypeId;

    /**
     * 权限类型id
     */
    private Integer permissionTypeId;

    /**
     * 权限组id集合
     */
    private List<Long> roleIds;

    /**
     * 盛昊删除操作(true:未删除，false:删除)
     */
    private Boolean isDeletedOperation = true;
}