package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Builder;
import lombok.Value;

/**
 * 地址
 *
 * @author legendjw
 */
@Value
@Builder
public class Address implements ValueObject {
    /**
     * 省份代码
     */
    private Integer provinceId;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 城市代码
     */
    private Integer cityId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 地区代码
     */
    private Integer areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 街道代码
     */
    private Integer streetId;

    /**
     * 街道名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String address;
}