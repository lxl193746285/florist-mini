package com.qy.region.app.application.dto;

import lombok.Data;

@Data
public class AdderssIdDTO {

    /**
     * 省
     */
    private Long provinceId;
    /**
     * 市
     */
    private Long cityId;
    /**
     * 区/县
     */
    private Long countyId;
    /**
     * 街道
     */
    private Long townId;
}
