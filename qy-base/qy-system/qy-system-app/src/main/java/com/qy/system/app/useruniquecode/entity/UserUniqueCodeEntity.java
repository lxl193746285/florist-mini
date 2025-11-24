package com.qy.system.app.useruniquecode.entity;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_user_unique_code")
public class UserUniqueCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updatorId;
    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private String clientId;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    private Long organizationId;
    /**
     * 1.经销商，2.公司员工
     */
    @ApiModelProperty(value = "1.经销商，2.公司员工")
    private Integer groupId;
    /**
     * 是否忽略验证，1.忽略，0.不忽略
     */
    @ApiModelProperty(value = "是否忽略验证，1.忽略，0.不忽略")
    private Integer isIgnore;

    @ApiModelProperty(value = "极光推送唯一码")
    private String registrationId;
}
