package com.qy.region.app.infrastructure.persistence.mybatis.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author legendjw
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_sys_region_area")
public class AreaDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地区id
     */
    private Long id;

    /**
     * 父级地区id
     */
    private Long parentId;

    /**
     * 地区码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 经度
     */
    private Float longitude;

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 人口
     */
    private Integer population;
    private String province;
    private String city;
    private String country;
    private String street;


}
