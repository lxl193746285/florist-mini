package com.qy.wf.defNodeEvent.dto;

import com.qy.common.dto.ArkBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作流_设计_节点事件
 *
 * @author syf
 * @since 2022-11-21
 */
@Data
public class DefNodeEventDTO  extends ArkBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 公司
     */
    @ApiModelProperty(value = "公司")
    private Long companyId;

    /**
     * 工作流ID
     */
    @ApiModelProperty(value = "工作流ID")
    private Long wfId;

    /**
     * 节点ID
     */
    @ApiModelProperty(value = "节点ID")
    private Long nodeId;
    /**
     * 节点编码id
     */
    @ApiModelProperty(value = "节点编码id")
    private String nodeCode;

    /**
     * 触发时机1到达前，2办理后
     */
    @ApiModelProperty(value = "触发时机1到达前，2办理后")
    private Integer triggerType;
    @ApiModelProperty(value = "1直接执行默认，2SQL表达式")
    private Integer triggerMode;
    @ApiModelProperty(value = "条件表达式")
    private String triggerExp;
    /**
     * 1直接执行2SQL表达式验证
     */
    @ApiModelProperty(value = "1直接执行2SQL表达式验证")
    private Integer rule;

    /**
     * 执行表达式
     */
    @ApiModelProperty(value = "执行表达式")
    private String ruleExp;

    /**
     * 执行方式1-SQL （调用plat_trigger_data_exe 支持多组操作）2-Http接口
     */
    @ApiModelProperty(value = "执行方式1-SQL （调用plat_trigger_data_exe 支持多组操作）2-Http接口")
    private Integer executionMethod;

    /**
     * url类型 1相对地址（应用内地址） 2绝对地址（全路径）
     */
    @ApiModelProperty(value = "url类型 1相对地址（应用内地址） 2绝对地址（全路径）")
    private Integer urlType;
    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式 1-GET 2-POST,3-PUT 4 PATCH 5 DELETE 代码表plat_trigger_exe.url_request_method")
    private Integer urlRequestMethod;

    /**
     * 类型  0-None 1-JSON 2-XML
     */
    @ApiModelProperty(value = "类型  0-None 1-JSON 2-XML")
    private Integer bodyType;

    /**
     * 请求参数，多个参数按照Url请求的方式添加
     */
    @ApiModelProperty(value = "请求参数，多个参数按照Url请求的方式添加")
    private String params;

    /**
     * body
     */
    @ApiModelProperty(value = "body")
    private String body;
    /**
     * auth_type 0-None 1-bearertoken 2basic_auth
     */
    @ApiModelProperty(value = "auth_type 0-None 1-bearertoken 2basic_auth")
    private Integer authType;
    /**
     * bearertoken
     */
    @ApiModelProperty(value = "bearertoken")
    private String bearertoken;
    /**
     * basic_auth_pass_word
     */
    @ApiModelProperty(value = "basic_auth_pass_word")
    private String basicAuthUserName;
    /**
     * basic_auth_pass_word
     */
    @ApiModelProperty(value = "basic_auth_pass_word")
    private String basicAuthPassWord;

    /**
     * 顺序
     */
    @ApiModelProperty(value = "顺序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String note;

    /**
     * 1启用0禁用
     */
    @ApiModelProperty(value = "1启用0禁用")
    private Integer status;

}
