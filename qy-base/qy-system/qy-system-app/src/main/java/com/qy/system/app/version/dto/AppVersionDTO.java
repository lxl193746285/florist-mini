package com.qy.system.app.version.dto;

import com.qy.system.app.comment.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * APP版本
 *
 * @author syf
 * @since 2024-05-21
 */
@Data
public class AppVersionDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键id
     */
    @ApiModelProperty(value = "自增主键id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Long typeId;
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 项目类型
     */
    @ApiModelProperty(value = "类型")
    private Long itemTypeId;
    @ApiModelProperty(value = "项目类型")
    private String itemType;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer version;

    /**
     * 是否强制更新
     */
    @ApiModelProperty(value = "是否强制更新")
    private Integer forceUpdate;

    /**
     * 包名
     */
    @ApiModelProperty(value = "包名")
    private String packName;

    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址")
    private String url;

    /**
     * 更新说明
     */
    @ApiModelProperty(value = "更新说明")
    private String updateRemark;

}
