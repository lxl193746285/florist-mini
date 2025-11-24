package com.qy.system.app.config.dto;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModelProperty;

    import java.io.Serializable;
import java.util.List;

/**
 * 配置
 *
 * @author hh
 * @since 2024-07-09
 */
@Data
public class SystemConfigQueryDTO implements Serializable {
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
    * 配置名称
    */
    @ApiModelProperty(value = "配置名称", name = "title")
    private String title;

    /**
     * 分类标识
     */
    @ApiModelProperty(value = "分类标识")
    private String categoryIdentifier;

    /**
     * 分类标识数组
     */
    @ApiModelProperty(value = "分类标识数组")
    private List<String> categoryIdentifiers;

}
