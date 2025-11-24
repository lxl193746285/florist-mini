package com.qy.system.app.comment.enums;

import java.time.LocalDateTime;

public interface IArkDeleteEntity {
    /**
     * 删除人id
     *
     * @param deletorId
     */
    void setDeletorId(Long deletorId);

    /**
     * 删除人名称
     *
     * @param deletorName
     */
    void setDeletorName(String deletorName);

    /**
     * 删除时间
     *
     * @param deleteTime
     */
    void setDeleteTime(LocalDateTime deleteTime);

    /**
     * 删除标示
     * @param status
     */
    void setIsDeleted(Byte status);
}
