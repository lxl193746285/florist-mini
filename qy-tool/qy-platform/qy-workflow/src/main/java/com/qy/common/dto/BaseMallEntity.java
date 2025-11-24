package com.qy.common.dto;

import com.qy.utils.DateUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BaseMallEntity {
    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Integer createTime;

    /**
     * 创建时间显示
     */
    @TableField(exist = false)
    private String createTimeName;

    /**
     * 更新人id
     */
    private Long updatorId;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新时间
     */
    private Integer updateTime;

    /**
     * 更新时间显示
     */
    @TableField(exist = false)
    private String updateTimeName;

    /**
     * 删除人id
     */
    private Long deletorId;

    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除时间
     */
    private Integer deleteTime;

    /**
     * 是否删除0.未删除1.已删除
     */
    private Integer isDeleted;


    public String getCreateTimeName() {
        createTimeName = DateUtils.TimestampTransitionDatestrToMin(createTime);
        return createTimeName;
    }

    public String getUpdateTimeName() {
        updateTimeName = DateUtils.TimestampTransitionDatestrToMin(updateTime);
        return updateTimeName;
    }
}
