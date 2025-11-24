package com.qy.system.app.dismodel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 二维码配置模板
 * </p>
 *
 * @author sxj
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_qrcode_model")
public class QrcodeModelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 模板id
     */
    private String modelId;

    /**
     * 场景
     */
    private String scene;
    /**
     * 模板名称
     */
    private String name;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private Long creatorId;

    /**
     * 创建人名称
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private Long updatorId;

    /**
     * 更新人名称
     */
    private String updatorName;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    private Long deletorId;

    /**
     * 删除人名称
     */
    private String deletorName;

    private Integer isDeleted;
    private Integer sort;
    private Integer status;
    private String remark;


}
