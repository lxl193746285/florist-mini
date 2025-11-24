package com.qy.comment.api.dto;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论
 *
 * @author huangh
 * @since 2022-04-02
 */
@Data
public class CommentQueryDTO extends PageQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 系统类别1-办公 系统，2会员系统
     */
    private Integer systemType;
    /**
     * 系统类别1-办公 系统，2会员系统
     */
    private String systemTypeName;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 公司ID
     */
    private Long companyId;

    /**
    * 内容
    */
    private String content;

    /**
    * 类型
    */
    private String linkType;
    /**
     * 类型
     */
    private String linkTypeName;
    /**
    * 关联数据ID
    */
    private Long linkId;

    /**
    * 点赞数量
    */
    private Integer praiseNum;

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
    private Integer isDeleted;

    /**
    * 创建日期-开始(2021-01-01)
    */
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    private String endCreateDate;
}
