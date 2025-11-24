package com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 附件关系
 * </p>
 *
 * @author legendjw
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_attachment_relation")
public class AttachmentRelationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 附件id
     */
    private Long attachmentId;

    /**
     * 关联模块id
     */
    private String moduleId;

    /**
     * 关联数据id
     */
    private Long dataId;

    /**
     * 类型
     */
    private String type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
