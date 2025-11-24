package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息系统消息
 * </p>
 *
 * @author legendjw
 * @since 2021-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_msg_message")
public class MessageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 一级关联模块id
     */
    private String primaryModuleId;

    /**
     * 一级关联模块名称
     */
    private String primaryModuleName;

    /**
     * 一级关联数据id
     */
    private Long primaryDataId;

    /**
     * 二级关联模块id
     */
    private String secondaryModuleId;

    /**
     * 二级关联模块名称
     */
    private String secondaryModuleName;

    /**
     * 二级关联数据id
     */
    private Long secondaryDataId;

    /**
     * 链接
     */
    private String link;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否删除
     */
    private Byte isDeleted;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;


}
