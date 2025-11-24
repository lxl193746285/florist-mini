package com.qy.system.app.dismodel.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 二维码配置模板
 *
 * @author sxj
 * @since 2022-03-17
 */
@Data
public class QrcodeModelFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 模板id
     */
    private String modelId;

    /**
     * 场景
     */
    private String scene;
    /**
     * 备注
     */
    private String remark;
    /**
     * 名称
     */
    private String name;


    /**
     * 内容
     */
    private String content;

}
