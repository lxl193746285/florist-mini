package com.qy.system.app.comment.enums;

import java.time.LocalDateTime;

public interface IArkUpdateEntity {
    /**
     * 更新人Id
     *
     * @param updatorId
     */
    void setUpdatorId(Long updatorId);

    /**
     * 更新人名称
     *
     * @param updatorName
     */
    void setUpdatorName(String updatorName);

    /**
     * 更新时间
     *
     * @param updateTime
     */
    void setUpdateTime(LocalDateTime updateTime);
}
