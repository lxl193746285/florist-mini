package com.qy.customer.api.query;

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
     * 关键字
     */
    private String keywords;

    /**
     * 客户等级id
     */
    private Integer levelId;

    /**
     * 客户来源id
     */
    private Integer sourceId;

    /**
     * 是否已开户
     */
    private Byte isOpenAccount;
}
