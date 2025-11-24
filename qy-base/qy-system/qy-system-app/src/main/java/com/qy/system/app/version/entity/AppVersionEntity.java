package com.qy.system.app.version.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * APP版本
 *
 * @author syf
 * @since 2024-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_app_version")
public class AppVersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private String type;
    /**
     * 项目类型
     */
    @ApiModelProperty(value = "项目类型")
    private Long itemTypeId;
    /**
     * 项目类型
     */
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
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long creatorId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long updatorId;
    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deleteTime;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private Long deletorId;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updatorName;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String creatorName;
    /**
     * 删除人
     */
    @ApiModelProperty(value = "删除人")
    private String deletorName;
}
