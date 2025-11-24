package com.qy.organization.app.application.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qy.security.session.MemberIdentity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改组织命令
 *
 * @author legendjw
 */
@Data
public class OrgDatasourceCommand implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 数据源名称
     */
    @ApiModelProperty("数据源名称")
    @NotBlank(message = "请输入数据源名称")
    private String datasourceName;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    @NotNull(message = "请选择组织")
    private Long orgId;

    @JsonIgnore
    private MemberIdentity user;
}