package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.message.app.application.enums.MessageTemplateCustomParameterAction;
import com.qy.message.app.application.enums.Template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by:liuwei
 * @version 2018-08-31
 * @serial ark_msg_message_template_custom_parameter
 */
public class MessageTemplateCustomParameter implements Serializable {

    /**
     * id (主健ID) (无默认值)
     */
    private Integer id;

    /**
     * 模版id(必填项)  (默认值为: 0)
     */
    private Integer templatelId;

    /**
     * 参数编号(必填项)  (默认值为: )
     */
    private String keyId;

    /**
     * 参数取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项)  (默认值为: 0)
     */
    private Byte keyMode;

    /**
     * 参数内容(必填项)  (默认值为: )
     */
    private String keyValue;

    /**
     * 标题(必填项)  (默认值为: )
     */
    private String title;

    /**
     * 备注
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String keyModeName = "";

    private List<MessageTemplateCustomParameterAction> messageTemplateCustomParameterActions = new ArrayList<>();

    protected static final long serialVersionUID = 1L;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getKeyModeName() {
        return Template.getName(keyMode.intValue());
    }

    public void setKeyModeName(String keyModeName) {
        this.keyModeName = keyModeName;
    }

    public List<MessageTemplateCustomParameterAction> getMessageTemplateCustomParameterActions() {
        return messageTemplateCustomParameterActions;
    }

    public void setMessageTemplateCustomParameterActions(
            List<MessageTemplateCustomParameterAction> messageTemplateCustomParameterActions) {
        this.messageTemplateCustomParameterActions = messageTemplateCustomParameterActions;
    }
}