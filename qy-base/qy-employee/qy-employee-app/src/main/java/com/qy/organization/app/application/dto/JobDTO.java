package com.qy.organization.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织岗位
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Data
public class JobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organizationId;

    @ApiModelProperty("岗位编码")
    private String jobCode;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organizationName;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 岗位描述
     */
    @ApiModelProperty("岗位描述")
    private String description;

    /**
     * 包含员工
     */
    @ApiModelProperty("包含员工")
    private List<EmployeeBasicDTO> employees;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 状态id
     */
    @ApiModelProperty("状态id")
    private Integer statusId;

    /**
     * 状态名称
     */
    @ApiModelProperty("状态名称")
    private String statusName;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private Long creatorId;

    /**
     * 创建人名称
     */
    @ApiModelProperty("创建人名称")
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private String createTimeName;

    /**
     * 更新人id
     */
    @ApiModelProperty("更新人id")
    private Long updatorId;

    /**
     * 更新人名称
     */
    @ApiModelProperty("更新人名称")
    private String updatorName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private String updateTimeName;

    /**
     * 操作
     */
    private List<Action> actions = new ArrayList<>();
}
