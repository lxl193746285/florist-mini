package com.qy.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 发送会员模版消息命令
 *
 * @author legendjw
 */
@Data
public class SendMemberMessageDTO {
    /**
     * 模版模块id
     */
    @NotBlank(message = "请输入模版模块id")
    private String templateModuleId;

    /**
     * 模版功能id
     */
    @NotBlank(message = "请输入模版功能id")
    private String templateFunctionId;

    /**
     * 一级关联模块id
     */
    private String primaryModuleId;

    /**
     * 一级关联模块名称
     */
    private String primaryModuleName;

    /**
     * 一级关联数据id
     */
    private Long primaryDataId;

    /**
     * 二级关联模块id
     */
    private String secondaryModuleId;

    /**
     * 二级关联模块名称
     */
    private String secondaryModuleName;

    /**
     * 二级关联数据id
     */
    private Long secondaryDataId;

    /**
     * 链接
     */
    private String link;

    /**
     * 会员账号id
     */
    private Long memberAccountId;

    /**
     * 额外参数
     */
    Map<String, Object> params;
}