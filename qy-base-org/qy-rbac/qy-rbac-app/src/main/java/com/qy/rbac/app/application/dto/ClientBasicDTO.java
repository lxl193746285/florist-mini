package com.qy.rbac.app.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户端
 *
 * @author legendjw
 * @since 2021-08-06
 */
@Data
public class ClientBasicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 客户端id
     */
    private String clientId;
}