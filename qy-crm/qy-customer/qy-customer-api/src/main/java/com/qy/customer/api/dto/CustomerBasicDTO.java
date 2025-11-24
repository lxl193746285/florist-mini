package com.qy.customer.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户基本DTO
 *
 * @author legendjw
 * @since 2021-08-03
 */
@Data
public class CustomerBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 组织id
     */
    private Long organizationId;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 拼音
     */
    private String namePinyin;

    /**
     * 电话
     */
    private String tel;

    /**
     * 传真
     */
    private String fax;

    /**
     * 邮箱
     */
    private String email;
}