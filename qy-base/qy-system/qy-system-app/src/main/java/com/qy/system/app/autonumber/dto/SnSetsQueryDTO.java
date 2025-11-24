package com.qy.system.app.autonumber.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 编号设置查询条件
 * </p>
 *
 * @author ln
 * @since 2022-04-29
 */
@Data
public class SnSetsQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

//    /**
//     * 关键字
//     */
//    private String keywords;

    /**
     * 规则名称
     */
    private String noname;

    /**
     * 规则编码
     */
    private String noid;

    /**
     * 启用状态的设置 状态(0:禁用；1:启用)
     */
    private Byte status;

    /**
     * 公司Id
     */
    private Long companyId;

}
