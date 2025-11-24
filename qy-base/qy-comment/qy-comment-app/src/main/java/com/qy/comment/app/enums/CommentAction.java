//package com.qy.comment.app.enums;
//
//import com.qy.uims.security.action.PermissionAction;
//import com.fasterxml.jackson.annotation.JsonFormat;
//
///**
// * 评论动作
// */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//public enum CommentAction  implements IArkEnumAction {
//    LIST("index", "列表", "mall/comments/index"),
//    VIEW("view", "查看", "mall/comments/view"),
//    CREATE("create", "创建", "mall/comments/create"),
//    EDIT("update", "编辑", "mall/comments/update"),
//    DELETE("delete", "删除", "mall/comments/delete");
//
//    private CommentAction(String id, String name, String permission) {
//        this.permissionAction = PermissionAction.create(id, name, permission);
//    }
//
//    PermissionAction permissionAction;
//
//    @Override
//    public PermissionAction getPermissionAction() {
//        return permissionAction;
//    }
//}
