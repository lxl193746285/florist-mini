package com.qy.common.enums;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArkOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Action fromIEnumAction(IArkPermissionAction iEnumAction) {
        return new Action(iEnumAction.getPermissionAction().getId(), iEnumAction.getPermissionAction().getName());
    }
}
