package com.qy.extend.app.enums;//package com.qy.extend.app.enums;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
///**
// * 评论扩展动作
// */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//public enum CommentExtendAction  implements IArkEnumAction {
//    LIST("index", "列表", "mall/ark-system-comment-extends/index"),
//    VIEW("view", "查看", "mall/ark-system-comment-extends/view"),
//    CREATE("create", "创建", "mall/ark-system-comment-extends/create"),
//    EDIT("update", "编辑", "mall/ark-system-comment-extends/update"),
//    DELETE("delete", "删除", "mall/ark-system-comment-extends/delete");
//
//    private ArkSystemCommentExtendAction(String id, String name, String permission) {
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