package com.qy.customer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联模块数据标识.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatedModuleDataDTO {
    /**
     * 关联模块ID
     */
    private Integer relatedModuleId;

    /**
     * 关联数据ID
     */
    private Long relatedDataId;
}
