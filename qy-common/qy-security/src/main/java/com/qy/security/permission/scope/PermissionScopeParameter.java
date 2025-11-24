package com.qy.security.permission.scope;

/**
 * 权限范围参数
 *
 * @author legendjw
 */
public class PermissionScopeParameter {
    /**
     * 是否可以选择数据
     */
    private boolean isSelectable;

    /**
     * 可选数据的展示形式，支持：列表：LIST 树形：TREE
     */
    private String selectShowForm;

    public PermissionScopeParameter(boolean isSelectable, String selectShowForm) {
        this.isSelectable = isSelectable;
        this.selectShowForm = selectShowForm;
    }

    public boolean isSelectable() {
        return isSelectable;
    }

    public String getSelectShowForm() {
        return selectShowForm;
    }
}