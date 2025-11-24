package com.qy.member.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * 个人会员系统查询
 *
 * @author legendjw
 */
@Data
public class MemberQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 会员系统id
     */
    private Long systemId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private Integer statusId;

    /**
     * 审核状态
     */
    private Integer auditStatusId;

    /**
     * 性别id
     */
    private Integer genderId;

    /**
     * 等级id
     */
    private Integer levelId;

    /**
     * 权限组id
     */
    private Long roleId;

    /**
     * 会员id集合
     */
    private List<Long> ids;

    /**
     * 创建开始时间
     */
    private String createStartTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;
}
