package com.qy.comment.api.dto;

import com.qy.comment.api.vo.AttachmentVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评论
 *
 * @author huangh
 * @since 2022-04-02
 */
@Data
public class CommentFormDTO implements Serializable {
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
//    @NotBlank(message = "内容 不能为空")
    private String content;

    /**
     * 类型
     */
//    @NotBlank(message = "类型 不能为空")
    private String linkType;
    /**
     * 类型
     */
    private String linkTypeName;
    /**
     * 关联数据ID
     */
//    @NotNull(message = "关联数据ID 不能为空")
    private Long linkId;

    /**
     * 点赞数量
     */
//    @NotNull(message = "点赞数量 不能为空")
    private Integer praiseNum;

    /**
     * 主评论ID
     */
//    @NotNull(message = "主评论ID 不能为空")
    private Long primaryCommentId;

    /**
     * 回复评论ID
     */
//    @NotNull(message = "回复评论ID 不能为空")
    private Long replyCommentId;

    /**
     * 评论数量
     */
//    @NotNull(message = "评论数量 不能为空")
    private Integer commentCount;
    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建人Id
     */
    private Long creatorId;

//    /**
//     * 创建时间
//     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
//    private Date createTime;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新人Id
     */
    private Long updatorId;

//    /**
//     * 更新时间
//     */@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
//
//    private Date updateTime;

    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除人Id
     */
    private Long deletorId;

//    /**
//     * 删除时间
//     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
//    private Date deleteTime;

    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private Integer isDeleted;

    private List<AttachmentVo> attachments;
}
