package com.qy.extend.app.dto;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

@Data
public class CommentExtendQueryDTO  extends PageQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 关联id
     */
    private Long linkId;
    /**
     * 类型
     */
    private String type;
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
     * 创建日期-开始(2021-01-01)
     */
    private String startCreateDate;

    /**
     * 创建日期-结束(2021-01-01)
     */
    private String endCreateDate;
}

