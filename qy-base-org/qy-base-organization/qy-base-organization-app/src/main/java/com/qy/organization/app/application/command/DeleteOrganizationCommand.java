package com.qy.organization.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除组织命令
 *
 * @author legendjw
 */
@Data
public class DeleteOrganizationCommand implements Serializable {
    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity user;

    /**
     * id
     */
    private Long id;
}