package com.qy.system.app.useruniquecode.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModelProperty;

    import java.time.LocalDateTime;
    import java.io.Serializable;
/**
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@Data
public class UserUniqueCodeQueryDTO implements Serializable {
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
    @ApiModelProperty(value = "组织id")
    private Long organizationId;
    private String userName;
    private String phone;
    private Integer isIgnore;

}
