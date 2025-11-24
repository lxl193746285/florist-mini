package com.qy.common.dto;

import com.qy.security.permission.action.Action;
import com.qy.utils.DateUtils;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArkBaseDTO {

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建时间
     */
    public LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新时间
     */
    public LocalDateTime updateTime;

    /**
     * 操作权限
     */
    private List<Action> actions = new ArrayList<>();

    public String updateTimeName;
    public String createTimeName;

    public String getCreateTimeName() {
        if (createTime != null) {
            return DateUtils.localDateTimeToDateStr(createTime, DateUtils.dateTimeFormat);
        }
        return this.createTimeName;
    }

    public String getUpdateTimeName() {
        if (updateTime != null) {
            return DateUtils.localDateTimeToDateStr(updateTime, DateUtils.dateTimeFormat);
        }
        return this.updateTimeName;

    }

}