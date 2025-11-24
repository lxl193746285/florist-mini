package com.qy.attachment.app.application.command;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RepairThumbImageCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 需要修复的附件id
     */
    private List<Long> attachmentIds;

    /**
     * 模块id
     */
    private String moduleId;
}
