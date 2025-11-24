package com.qy.common;

import com.qy.common.enums.IArkPermissionAction;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;

@Data
public class QyOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    public static Action fromIEnumAction(IArkPermissionAction iEnumAction) {
        return new Action(iEnumAction.getPermissionAction().getId(), iEnumAction.getPermissionAction().getName());
    }
}
