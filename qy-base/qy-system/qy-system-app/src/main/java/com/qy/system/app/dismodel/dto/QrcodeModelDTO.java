package com.qy.system.app.dismodel.dto;

import com.qy.system.app.comment.dto.ArkBaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 二维码配置模板
 *
 * @author sxj
 * @since 2022-03-17
 */
@Data
public class QrcodeModelDTO extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 模板id
     */
    private String modelId;

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
     * 场景
     */
    private String scene;
    /**
     * 删除人
     */
    private Long deletorId;
    private String remark;
    private Integer sort;
    /**
     * 删除人名称
     */
    private String deletorName;

    private Integer isDeleted;
    private Integer status;
    private String createTimeName;

    private String statusName;
    private String updateTimeName;

    public String getCreateTimeName() {
        return createTimeName;
    }

    public void setCreateTimeName(LocalDateTime createTime) {
        if (createTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(createTime);
            this.createTimeName = format;
        }

    }

    public String getUpdateTimeName() {
        return updateTimeName;
    }

    public void setUpdateTimeName(LocalDateTime updateTime) {
        if (updateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = formatter.format(updateTime);
            this.updateTimeName = format;
        }

    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(Integer status) {
        switch (status) {
            case 0:
                this.statusName = "禁用";
                break;
            case 1:
                this.statusName = "启用";
                break;

        }

    }
}
