package com.qy.extend.app.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评论扩展
 *
 * @author huangh
 * @since 2022-04-02
 */
@Data
public class CommentExtendDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
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
     * 会员名称
     */
    private String memberName;
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

    private String createTimeName;

    private String updateTimeName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private Integer isDeleted;
    private List<Action> actions;
}
