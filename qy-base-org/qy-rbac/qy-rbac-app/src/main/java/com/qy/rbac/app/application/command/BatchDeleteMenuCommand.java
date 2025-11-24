package com.qy.rbac.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量删除菜单命令
 *
 * @author legendjw
 */
@Data
public class BatchDeleteMenuCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * id
     */
    private List<Long> ids;
}