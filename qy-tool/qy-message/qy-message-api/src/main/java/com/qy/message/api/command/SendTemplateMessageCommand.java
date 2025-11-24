package com.qy.message.api.command;

import com.qy.message.api.dto.UserMessageWxDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 发送模版消息命令
 *
 * @author legendjw
 */
@Data
public class SendTemplateMessageCommand {
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
     * 上下文
     */
    private String context;

    /**
     * 上下文id
     */
    private String contextId;

    /**
     * 上下文名称
     */
    private String contextName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 微信appid
     */
    private String weixinAppId;

    /**
     * 公众号openId
     */
    private String openId;

    /**
     * 手机唯一id
     */
    private String mobileId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 参数
     */
    Map<String, Object> params;

    /**
     * 极光推送额外参数
     */
    Map<String, String> extraParams;

    /**
     * 消息标识
     */
    private String srcId;

    /**
     * 源表行id
     */
    private Long srcRowId;

    /**
     * 接收人id
     */
    private Long receiveUserId;

    private List<UserMessageWxDto> userMessageWxDtos;
}