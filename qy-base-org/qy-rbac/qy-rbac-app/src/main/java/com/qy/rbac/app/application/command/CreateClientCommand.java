package com.qy.rbac.app.application.command;

import com.qy.security.session.Identity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 创建客户端命令
 *
 * @author legendjw
 */
@Data
public class CreateClientCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前用户
     */
    @JsonIgnore
    private Identity identity;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    /**
     * 会员系统id
     */
    @ApiModelProperty("会员系统id")
    private Long systemId;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}