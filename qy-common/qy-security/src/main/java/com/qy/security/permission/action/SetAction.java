package com.qy.security.permission.action;

import java.util.List;

/**
 * 可设置操作的
 *
 * @author legendjw
 */
public interface SetAction {
    /**
     * 获取操作
     *
     * @return
     */
    List<Action> getActions();

    /**
     * 设置操作
     *
     * @param actions
     */
    void setActions(List<Action> actions);
}