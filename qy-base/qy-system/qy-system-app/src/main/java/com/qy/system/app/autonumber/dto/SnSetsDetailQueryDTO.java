package com.qy.system.app.autonumber.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 编号规则设置-子
 * </p>
 *
 * @author ln
 * @since 2022-05-01
 */
@Data
public class SnSetsDetailQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 单号
     */
    private String noid;

    /**
     * 公司Id
     */
    private Long companyId;

    /**
     * 序号
     */
    private Integer recno;

}
