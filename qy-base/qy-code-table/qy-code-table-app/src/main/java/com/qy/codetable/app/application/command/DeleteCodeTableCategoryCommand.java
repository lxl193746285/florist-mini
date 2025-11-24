package com.qy.codetable.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 删除代码表分类命令
 *
 * @author legendjw
 */
@Data
public class DeleteCodeTableCategoryCommand {
    /**
     * 当前登录用户
     */
    @JsonIgnore
    private Identity user;

    /**
     * id
     */
    private Long id;
}
