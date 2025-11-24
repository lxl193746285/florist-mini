package com.qy.attachment.api.command;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 关联单附件命令
 *
 * @author legendjw
 */
@Data
public class RelateAttachmentsCommand implements Serializable {
    /**
     * 附件id集合
     */
    @NotNull(message = "关联附件不能为空")
    private List<Long> attachmentIds;

    /**
     * 关联模块id
     */
    @NotBlank(message = "关联模块不能为空")
    private String moduleId;

    /**
     * 关联数据id
     */
    @NotNull(message = "关联数据不能为空")
    private Long dataId;

    /**
     * 类型
     */
    private String type;
}
