package com.qy.extend.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论扩展
 * </p>
 *
 * @author huangh
 * @since 2022-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_comment_extend")
public class CommentExtendEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 关联id
     */
    private Long linkId;

    /**
     * 类型
     */
    private String type;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 系统别  1-办公 系统，2会员系统
     */
    private Integer systemType;

    /**
     * 1点赞，2关注
     */
    private Integer category;

    /**
     * 创建人
     */
    private String creatorName;


    /**
     * 创建人Id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新人Id
     */
    private Long updatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除人Id
     */
    private Long deletorId;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private Byte isDeleted;


}
