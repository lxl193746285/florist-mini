package com.qy.system.app.comment.enums;

import java.time.LocalDateTime;

public interface IArkCreateEntity {
    /**
     * 创建人Id
     * @param creatorId
     */
    void setCreatorId(Long creatorId);

    /**
     * 创建人名称
     * @param creatorName
     */
    void setCreatorName(String creatorName);

    /**
     * 创建时间
     * @param localDateTime
     */
    void setCreateTime(LocalDateTime localDateTime);
}
