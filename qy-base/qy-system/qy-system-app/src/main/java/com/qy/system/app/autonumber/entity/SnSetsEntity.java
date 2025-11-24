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
@TableName("st_system_no")
public class SnSetsEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /* noid 和 company_id确定唯一性 */
    private String noid;

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
    private Integer isDeleted;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 下一序号
     */
    private int nextseq;

    /**
     * 当前年
     */
    private String nowyear;

    /**
     * 名称
     */
    private String noname;

    /**
     * 当前月
     */
    private String nowmonth;

    /**
     * 当前日
     */
    private String nowday;

    /**
     * 当前值
     */
    private String nowvalue;

    /**
     * 循环周期
     */
    private int cycle;

    /**
     * 公司Id
     */
    private Long companyId;
    /**
     * 用户
     */
    private Long userId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1 表示平台的，0表示自己的公司的
     */
    private int isPlatform;
}
