package com.qy.comment.app.dto;

import com.qy.comment.app.vo.AttachmentVo;
import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论
 *
 * @author huangh
 * @since 2022-04-02
 */
@Data
public class CommentDTO   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;
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
     * 头像
     */
    private String avatar;
    /**
     * 会员名称
     */
    private String memberName;
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
     * 回复名称
     */
    private String replyName;

    /**
     * 评论数量
     */
    private Integer commentCount;
    /**
     * 评论总数量
     */
    private Integer totalCommentCount;
    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建人Id
     */
    private Long creatorId;

    private String createTimeName;

    private String updateTimeName;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新人Id
     */
    private Long updatorId;



    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除人Id
     */
    private Long deletorId;


    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private Integer isDeleted;
    /**
     * 自己是否点赞 0已点赞 1未点赞
     */
    private Integer isLike;


    private List<AttachmentVo> attachments;
    private CommentDTO replyComment;
    private Integer replyCommentCount;
    private List<CommentDTO> replyComments = new ArrayList<>();
    //    private Set<CommentAction> canPerformActions = new TreeSet<>();
    private List<Action> actions;

}
