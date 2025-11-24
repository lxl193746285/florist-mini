package com.qy.system.app.autonumber.dto;

import com.qy.security.permission.action.Action;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 编号设置主表
 * </p>
 *
 * @author ln
 * @since 2022-04-27
 */
@Data
public class SnSetsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Action> Actions;

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
    private Integer nextseq;

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
     * 循环周期
     */
    private String cycleName;

    /**
     * 公司Id
     */
    private Long companyId;
    /**
     * 用户
     */
    private Long userId;
    /**
     * 1 表示平台的，0表示自己的公司的
     */
    private int isPlatform;

    private String isPlatformName;


    private String createTimeName;
    private String updateTimeName;
    private String deleteTimeName;

    /**
     * 状态名称(0:停用；1:启用)
     */
    private String statusName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子表数据
     */
    private List<SnSetsDetailDTO> items = new ArrayList<>();

    public void setCreateTimeName(LocalDateTime createTime) {
        this.createTimeName = tranferTime(createTime);
    }
    public void setUpdateTimeName(LocalDateTime updateTime) {
        this.updateTimeName = tranferTime(updateTime);
    }
    public void setDeleteTimeName(LocalDateTime deleteTime) {
        this.deleteTimeName = tranferTime(deleteTime);
    }

    public String tranferTime(LocalDateTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = formatter.format(time);
        return format;
    }

    public void setIsPlatformName(int isPlatformName) {
        if (isPlatformName == 0) {
            this.isPlatformName = "公司";
        } else {
            this.isPlatformName = "平台";
        }
    }

}
