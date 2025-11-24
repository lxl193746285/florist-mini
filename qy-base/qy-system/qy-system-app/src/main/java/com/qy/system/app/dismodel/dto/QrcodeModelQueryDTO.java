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
public class QrcodeModelQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场景
     */
    private String scene;
//    /**
//    *
//    */
//    private Long id;

    /**
    * 模板id
    */
    private String modelId;

    /**
    * 模板名称
    */
    private String name;

//    /**
//    * 内容
//    */
//    private String content;

    private Integer sort;

    private String remark;
    /**
    * 创建日期-开始(2021-01-01)
    */
    private String startCreateDate;
    /**
     * 状态
     */
    private Integer status;
    /**
    * 创建日期-结束(2021-01-01)
    */
    private String endCreateDate;
}
