package com.qy.jpworkflow.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流
 *
 * @author syf
 * @since 2024-08-09
 */
@Data
public class StOaWorkflowDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Integer companyId;

    /**
     * 表单id
     */
    @ApiModelProperty(value = "表单id")
    private Integer formId;

    /**
     * 分类
     */
    @ApiModelProperty(value = "分类")
    private Integer categoryId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer dataSourceId;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Byte dataCategory;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer reportId;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Byte type;

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private String functionId;

}
