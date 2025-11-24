package com.qy.system.app.autonumber.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 编号规则设置表单
 * </p>
 *
 * @author ln
 * @since 2022-04-30
 */
@Data
public class SnSetsFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规则编号
     */
    private String noid;

    /**
     * 规则名称
     */
    private String noname;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 循环周期
     */
    private int cycle;

    /**
     * 备注
     */
    private String remark;

    /**
     * 平台通用配置（1 表示平台的，0表示自己的公司的，默认1）
     */
    private int isPlatform;

    /**
     * 子表数据
     */
    private List<SnSetsDetailFormDTO> items = new ArrayList<>();
}
