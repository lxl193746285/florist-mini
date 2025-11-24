package com.qy.system.app.config.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModelProperty;

    import java.io.Serializable;
/**
 * 配置类别
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
public class SystemConfigCategoryQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 创建日期-开始(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-开始", name = "start_create_date")
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    @ApiModelProperty(value = "创建日期-结束", name = "end_create_date")
    private String endCreateDate;

    /**
    * 配置类别名称
    */
    @ApiModelProperty(value = "配置类别名称", name = "name")
    private String name;

    /**
    * 状态：0 禁用 1开启 默认开启
    */
    @ApiModelProperty(value = "状态：0 禁用 1开启 默认开启", name = "status")
    private Integer status;

    /**
     * 标识符
     */
    @ApiModelProperty(value = "标识符")
    private String identifier;

}
