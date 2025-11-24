package com.qy.comment.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论
 * </p>
 *
 * @author huangh
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_comment")
public class CommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 公司ID
     */
    private Long companyId;
    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型
     */
    private String linkType;

    /**
     * 关联数据ID
     */
    private Long linkId;

    /**
     * 点赞数量
     */
    private Integer praiseNum;

    /**
     * 关注数量
     */
    private Integer concernsNum;

    /**
     * 主评论ID
     */
    private Long primaryCommentId;

    /**
     * 回复评论ID
     */
    private Long replyCommentId;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 系统类别1-办公 系统，2会员系统
     */
    private Integer systemType;

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
    /**
     * 回复名称
     */
    private String replyName;

}
