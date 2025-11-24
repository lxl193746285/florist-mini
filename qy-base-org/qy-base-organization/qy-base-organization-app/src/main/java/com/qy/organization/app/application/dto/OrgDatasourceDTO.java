package com.qy.organization.app.application.dto;

import com.qy.security.permission.action.Action;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织DTO
 *
 * @author legendjw
 */
@Data
public class OrgDatasourceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 数据库配置
     */
    @ApiModelProperty("数据库配置")
    private String datasourceName;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long orgId;

    /**
     * 组织
     */
    @ApiModelProperty("组织")
    private String orgName;

    /**
     * 创建人id
     */
    private Long creatorId;

    private String creatorName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;
    private String updatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    private String updateTimeName;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 操作
     */
    @ApiModelProperty("操作")
    private List<Action> actions = new ArrayList<>();

    public String getCreateTimeName() {
        if (createTime != null){
            return createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return createTimeName;
    }

    public String getUpdateTimeName() {
        if (updateTime != null){
            return updateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return updateTimeName;
    }
}