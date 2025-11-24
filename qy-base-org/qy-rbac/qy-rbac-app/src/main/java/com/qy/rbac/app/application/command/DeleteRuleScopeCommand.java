package com.qy.rbac.app.application.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除规则范围命令
 *
 * @author legendjw
 */
@Data
public class DeleteRuleScopeCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
}