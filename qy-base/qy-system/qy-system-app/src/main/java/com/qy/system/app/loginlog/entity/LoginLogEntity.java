package com.qy.system.app.loginlog.entity;

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
 * 系统登录日志
 *
 * @author wwd
 * @since 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_login_log")
public class LoginLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 客户端
     */
    @ApiModelProperty(value = "客户端")
    private String clientId;
    /**
     * 手机型号
     */
    @ApiModelProperty(value = "手机型号")
    private String phoneModel;
    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;
    /**
     * 操作ip
     */
    @ApiModelProperty(value = "操作ip")
    private String operateIp;
    /**
     * 设备唯一码
     */
    @ApiModelProperty(value = "设备唯一码")
    private String uniqueCode;
    /**
     * 类型，1.登录，2.退出
     */
    @ApiModelProperty(value = "类型，1.登录，2.退出")
    private Integer type;
    /**
     * 用户代理
     */
    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    private String extraData;

    private Integer isException;
}
