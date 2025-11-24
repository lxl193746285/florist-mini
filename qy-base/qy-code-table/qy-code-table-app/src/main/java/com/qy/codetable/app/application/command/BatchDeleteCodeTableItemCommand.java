package com.qy.codetable.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * 删除代码表默认项命令
 *
 * @author legendjw
 */
@Data
public class BatchDeleteCodeTableItemCommand {
    /**
     * 当前登录用户
     */
    @JsonIgnore
    private Identity user;

    /**
     * id集合
     */
    private List<Long> ids;
}