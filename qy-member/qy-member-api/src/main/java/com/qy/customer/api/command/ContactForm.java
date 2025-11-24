package com.qy.customer.api.command;

import lombok.Data;

/**
 * 联系人表单.
 */
@Data
public class ContactForm {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Integer isLegalPerson;
    private String position;
    private String remark;
}
