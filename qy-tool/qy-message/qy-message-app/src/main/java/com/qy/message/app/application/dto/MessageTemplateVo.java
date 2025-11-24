package com.qy.message.app.application.dto;


import com.qy.message.app.application.enums.MessageType;
import com.qy.message.app.application.enums.Template;
import com.qy.message.app.application.enums.UserReceiveMode;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class MessageTemplateVo extends MessageTemplate {

    /**
     * 内容取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项)  (默认值为: 0)
     */
    private String contentModeName = "";

    /**
     * 标题取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项)  (默认值为: 0)
     */
    private String titleModeName = "";

    /**
     * 消息类型(1：系统消息2：用户消息)(必填项)  (默认值为: 0)
     */
    private String messageTypeName = "";

    /**
     * 消息接收人模式(1:系统定义;2:用户定义)(必填项)  (默认值为: 0)
     */
    private String userReceiveModeName = "";

    private String typeName = "";

    private String clientTypeName = "";

    private List canPerformActions = new ArrayList<>();

/*    List<MessageTemplateParameter> messageTemplateParameters = new ArrayList<>();

    List<MessageTemplateRule> messageTemplateRules = new ArrayList<>();*/

    public String getContentModeName() {
        if (getTitleMode() != null) {
            contentModeName = Template.getName(getContentMode());
        }
        return contentModeName;
    }

    public void setContentModeName(String contentModeName) {
        this.contentModeName = contentModeName;
    }

    public String getTitleModeName() {
        if (getTitleMode() != null) {
            titleModeName = Template.getName(getTitleMode());
        }
        return titleModeName;
    }

    public void setTitleModeName(String titleModeName) {
        this.titleModeName = titleModeName;
    }

    public List getCanPerformActions() {
        return canPerformActions;
    }

    public void setCanPerformActions(List canPerformActions) {
        this.canPerformActions = canPerformActions;
    }

    public String getMessageTypeName() {
        if (getMessageType() != null) {
            messageTypeName = MessageType.getName(getMessageType());
        }
        return messageTypeName;
    }

    public void setMessageTypeName(String messageTypeName) {
        this.messageTypeName = messageTypeName;
    }

    public String getUserReceiveModeName() {
        if (getUserReceiveMode() != null) {
            userReceiveModeName = UserReceiveMode.getName(getUserReceiveMode());
        }
        return userReceiveModeName;
    }

    public void setUserReceiveModeName(String userReceiveModeName) {
        this.userReceiveModeName = userReceiveModeName;
    }

    public String getTypeName() {
        if (getType().intValue() == 1) {
            return "消息";
        }
        else if (getType().intValue() == 2) {
            return "通知";
        }
        else if (getType().intValue() == 3) {
            return "提醒";
        }
        return "";
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClientTypeName() {
        return clientTypeName;
    }

    public void setClientTypeName(String clientTypeName) {
        this.clientTypeName = clientTypeName;
    }
}
