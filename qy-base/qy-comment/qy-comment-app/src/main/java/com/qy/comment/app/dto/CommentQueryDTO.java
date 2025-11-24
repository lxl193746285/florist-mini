package com.qy.comment.app.dto;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

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
    * 创建日期-开始(2021-01-01)
    */
    private String startCreateDate;

    /**
    * 创建日期-结束(2021-01-01)
    */
    private String endCreateDate;
}
