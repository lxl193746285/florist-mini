package com.qy.system.app.autonumber.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 编号规则设置
 * </p>
 *
 * @author ln
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("st_system_noitem")
public class SnSetsDetailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /* noid 和 company_id、recno确定唯一性 */
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

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建人Id
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updatorName;

    /**
     * 更新人Id
     */
    private Long updatorId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除人
     */
    private String deletorName;

    /**
     * 删除人Id
     */
    private Long deletorId;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除标记(0:未删除；1:已删除)
     */
    private int isDeleted;

    /**
     * 0禁用1启用
     */
    private int status;

    /**
     * 公司Id
     */
    private Long companyId;
}
