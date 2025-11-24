package com.qy.crm.customer.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 客户联系人查询
 *
 * @author legendjw
 */
@Data
public class ContactQuery extends PageQuery {
    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 关联模块id
     */
    private String relatedModuleId;

    /**
     * 关联数据id
     */
    private Long relatedDataId;

    /**
     * 关键字
     */
    private String keywords;
}
