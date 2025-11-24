package com.qy.crm.customer.app.application.dto;

import lombok.Value;

/**
 * 关联的模块数据
 *
 * @author legendjw
 */
@Value
public class RelatedModuleDataDTO {
    /**
     * 关联模块id
     */
    private String relatedModuleId;

    /**
     * 关联数据id
     */
    private Long relatedDataId;
}
