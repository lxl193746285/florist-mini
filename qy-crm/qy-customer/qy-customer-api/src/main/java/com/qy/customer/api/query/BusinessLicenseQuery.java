package com.qy.customer.api.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 营业执照查询
 *
 * @author legendjw
 */
@Data
public class BusinessLicenseQuery extends PageQuery {
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