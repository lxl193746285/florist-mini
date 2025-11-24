package com.qy.crm.customer.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 客户查询
 *
 * @author legendjw
 */
@Data
public class CustomerQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 客户等级id 组织代码表：oa_customer_category
     */
    private Integer levelId;

    /**
     * 客户来源id 组织代码表：oa_customer_source
     */
    private Integer sourceId;

    /**
     * 是否已开户 1：是 0：否
     */
    private Byte isOpenAccount;

    /**
     * 状态id 系统代码表: common_audit_status
     */
    private Integer statusId;

    /**
     * 创建开始时间
     */
    private String createStartTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;
}
