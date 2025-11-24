package com.qy.message.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 接收方值对象
 *
 * @author lxl
 */
@Data
public class SmsReceiver implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 接收方地址
     */
    private String address;
}
