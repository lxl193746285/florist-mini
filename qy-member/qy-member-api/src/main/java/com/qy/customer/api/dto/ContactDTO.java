package com.qy.customer.api.dto;

import lombok.Data;

/**
 * 简化版联系人 DTO，用于在缺少 qy-customer 模块时继续编译。
 */
@Data
public class ContactDTO {
    /**
     * 联系人主键
     */
    private Long id;

    /**
     * 联系人名称
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 职位/头衔
     */
    private String position;

    /**
     * 是否法人 1 表示是法人
     */
    private Integer isLegalPerson;

    /**
     * 备注
     */
    private String remark;
}
