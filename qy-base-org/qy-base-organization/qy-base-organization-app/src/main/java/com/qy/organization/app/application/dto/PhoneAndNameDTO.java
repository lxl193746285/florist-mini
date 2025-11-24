package com.qy.organization.app.application.dto;

import lombok.Data;

/**
 * 手机号以及姓名DTO
 *
 * @author legendjw
 */
@Data
public class PhoneAndNameDTO {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 姓名
     */
    private String name;
}
