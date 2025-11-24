package com.qy.customer.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户联系人基本信息
 *
 * @author legendjw
 * @since 2021-08-03
 */
@Data
public class ContactBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名拼音
     */
    private String namePinyin;

    /**
     * 性别id: 1: 男性 0: 女性
     */
    private Integer genderId;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 电话1
     */
    private String tel;
}
