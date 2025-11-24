package com.qy.system.app.autonumber.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 编号规则-子
 * </p>
 *
 * @author ln
 * @since 2022-04-30
 */
@Data
public class SnSetsDetailFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主表主键Id
     */
    private String noid;

    /**
     * 序号
     */
    private int recno;

    /**
     * 使用固定字符
     */
    private int usefix;

    /**
     * 固定字符
     */
    private String fixval;

    /**
     * 使用年
     */
    private int useyear;

    /**
     * 使用月
     */
    private int usemonth;

    /**
     * 使用日
     */
    private int useday;

    /**
     * 使用序号
     */
    private int useseq;

    /**
     * 序号位数
     */
    private int digit;

    /**
     * 使用随机数
     */
    private int userandom;

    /**
     * 随机数长度
     */
    private int randomdigit;
}
