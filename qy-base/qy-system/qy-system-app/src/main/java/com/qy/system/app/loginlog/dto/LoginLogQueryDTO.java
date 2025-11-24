package com.qy.system.app.loginlog.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModelProperty;

    import java.time.LocalDateTime;
    import java.io.Serializable;
/**
 * 系统登录日志
 *
 * @author wwd
 * @since 2024-04-18
 */
@Data
public class LoginLogQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 创建日期-开始(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-开始")
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-结束")
    private String endCreateDate;

    private Integer type;

}
