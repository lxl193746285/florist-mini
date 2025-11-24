package com.qy.common.dto;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 通用查询审核日志条件
 */
@Data
public class AuditLogQueryDTO extends PageQuery {

    /**
     * 关联模块id
     */
    @NotBlank(message = "模块id不能为空")
    private String moduleId;

}
