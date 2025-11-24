package com.qy.system.app.loginlog.dto;

import com.qy.security.permission.action.Action;
import com.qy.system.app.comment.dto.ArkBaseDTO;
import com.qy.system.app.loginlog.enums.OperateType;
import com.qy.system.app.util.DateUtils;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 系统登录日志
 *
 * @author wwd
 * @since 2024-04-18
 */
@Data
public class LoginLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
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

    @ApiModelProperty(value = "用户")
    private String userName;

    @ApiModelProperty(value = "操作时间")
    private String operateTimeName;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    private String extraData;

    private Integer isException;

    private String isExceptionName;

    public String getIsExceptionName() {
        if (isException != null){
            isExceptionName = isException == 1 ? "异常" : "正常";
        }
        return isExceptionName;
    }

    public String getOperateTimeName() {
        if (operateTime != null) {
            return DateUtils.localDateTimeToDateStr(operateTime, DateUtils.dateTimeFormat);
        }
        return operateTimeName;
    }

    public String getTypeName() {
        if (type != null) {
            OperateType operateType = OperateType.getById(type);
            return operateType == null ? "" : operateType.getName();
        }
        return typeName;
    }

    private List<Action> actions;
}
