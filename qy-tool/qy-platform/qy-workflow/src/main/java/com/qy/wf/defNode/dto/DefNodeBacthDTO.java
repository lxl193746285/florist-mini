package com.qy.wf.defNode.dto;

import com.qy.wf.defDefine.dto.DefDefineFormDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventFormDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationFormDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserFormDTO;
import com.qy.wf.nodetable.dto.NodeTableFormDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefNodeBacthDTO {

    /**
     * 工作流id
     */
    @ApiModelProperty(value = "工作流id")
    private Long wfId;

    /**
     * 流程图字符串
     */
    @ApiModelProperty(value = "流程图字符串")
    private String wfStr;

    /**
     * 工作流设计定义
     */
    @ApiModelProperty(value = "工作流设计定义")
    private DefDefineFormDTO defDefineFormDTO;

    /**
     * 设计节点
     */
    @ApiModelProperty(value = "设计节点")
    private List<com.qy.wf.defNode.dto.DefNodeFormNodeDTO> defNodeFormNodeDTOList = new ArrayList<>();

    /**
     * 设计节点关系（连线）
     */
    @ApiModelProperty(value = "设计节点关系（连线）")
    private List<DefNodeRelationFormDTO> defNodeRelationFormDTOList = new ArrayList<>();

    /**
     * 设计节点人员（人员）
     */
    @ApiModelProperty(value = "设计节点人员（人员）")
    private List<DefNodeUserFormDTO> defNodeUserFormDTOList = new ArrayList<>();

    /**
     * 设计节点表单
     */
    @ApiModelProperty(value = "设计节点表单")
    private List<NodeTableFormDTO> nodeTableFormDTOList = new ArrayList<>();

    /**
     * 设计节点事件
     */
    @ApiModelProperty(value = "设计节点事件")
    private List<DefNodeEventFormDTO> nodeEventFormDTOList = new ArrayList<>();



}
