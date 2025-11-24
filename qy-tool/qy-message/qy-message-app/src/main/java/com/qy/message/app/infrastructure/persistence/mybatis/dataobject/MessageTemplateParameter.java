package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.message.app.application.enums.MessageTemplateParameterAction;
import com.qy.message.app.application.enums.Template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by:liuwei
 * @version 2018-08-30
 * @serial ark_msg_message_template_parameter
 */
public class MessageTemplateParameter implements Serializable {
    protected static final long serialVersionUID = 1L;

    /**
     * id (主健ID) (无默认值)
     */
    private Integer id;

    /**
     * 模版id(必填项) (默认值为: 0)
     */
    private Integer templatelId;

    /**
     * 参数编号(必填项) (默认值为: )
     */
    private String keyId;

    /**
     * 参数取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项) (默认值为: 0)
     */
    private Byte keyMode;

    /**
     * 参数内容(必填项) (默认值为: )
     */
    private String keyValue;

    /**
     * 备注(必填项) (默认值为: )
     */
    private String remark;
    /**
     * 参数类型(1:短信,2:微信)(可选项)  (默认值为: 1)
     */
    private Byte parameterType;

    private String keyModeName = "";
    private String parameterTypeName = "";

    private List<MessageTemplateParameterAction> messageTemplateParameterActions = new ArrayList<>();

    public List<MessageTemplateParameterAction> getMessageTemplateParameterActions() {
        return messageTemplateParameterActions;
    }

    public void setMessageTemplateParameterActions(
            List<MessageTemplateParameterAction> messageTemplateParameterActions) {
        this.messageTemplateParameterActions = messageTemplateParameterActions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplatelId() {
        return templatelId;
    }

    public void setTemplatelId(Integer templatelId) {
        this.templatelId = templatelId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId == null ? null : keyId.trim();
    }

    public Byte getKeyMode() {
        return keyMode;
    }

    public void setKeyMode(Byte keyMode) {
        this.keyMode = keyMode;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public Byte getParameterType() {
        return parameterType;
    }

    public void setParameterType(Byte parameterType) {
        this.parameterType = parameterType;
    }

    public String getKeyModeName() {
        return Template.getName(getKeyMode().intValue());
    }

    public void setKeyModeName(String keyModeName) {
        this.keyModeName = keyModeName;
    }

    public void setParameterTypeName(String parameterTypeName) {
        this.parameterTypeName = parameterTypeName;
    }

    public String getParameterTypeName() {
        return parameterType.intValue() == 1 ? "短信" : "微信";
    }
}
