package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.io.Serializable;

/**
 * @数表名称 ark_msg_message_template
 * @开发日期 2019-05-07
 * @开发作者 by:qiuhoujian 
 */
public class MessageTemplate implements Serializable {

    /** id (主健ID) (无默认值) */
    private Integer id;

    /** 模块编号(必填项)  (默认值为: ) */
    private String modelId;

    /** 功能编号(必填项)  (默认值为: ) */
    private String functionId;

    /** 启用状态(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isEnable;

    /** 站内信(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isSendSystemNotice;

    /** 微信(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isSendWeixin;

    /** 短信(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isSendPhoneMessage;

    /** 邮件(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isSendEmail;

    /** APP推送(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isSendApppush;

    /** 是否记录日志(0：禁用 1：启用)(必填项)  (默认值为: 0) */
    private Byte isWirtelog;

    /** 内容取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项)  (默认值为: 0) */
    private Byte contentMode;

    /** 标题取值模式(1:固定文本;2:参数匹配;3:SQL取值)(必填项)  (默认值为: 0) */
    private Byte titleMode;

    /** 标题(必填项)  (默认值为: ) */
    private String title;

    /** 内容(必填项)  (默认值为: ) */
    private String content;

    /** 微信模版编号(必填项)  (默认值为: ) */
    private String weixinModelId;

    /** 短信模版编号(必填项)  (默认值为: ) */
    private String phoneMessageModelId;

    /** 数据表(必填项)  (默认值为: ) */
    private String tableName;

    /** 消息类型(1：系统消息2：用户消息)(必填项)  (默认值为: 0) */
    private Byte messageType;

    /** 响应地址(必填项)  (默认值为: ) */
    private String responseUrl;

    /** 消息接收人模式(1:系统定义;2:用户定义)(必填项)  (默认值为: 0) */
    private Byte userReceiveMode;

    /** 消息接收人sql(必填项)  (默认值为: ) */
    private String userReceiveSql;

    /** 备注(必填项)  (默认值为: ) */
    private String remark;

    /** 模块描述(必填项)  (默认值为: ) */
    private String modelDescription;

    /** 名称(可选项)  (默认值为: ) */
    private String name;

    /** 发送消息类型（1：消息，2：通知，3：提醒）(可选项)  (默认值为: 1) */
    private Byte type;

    /** 1:方舟，2:蚂蚁聚游(必填项)  (默认值为: 1) */
    private Integer clientType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId == null ? null : modelId.trim();
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId == null ? null : functionId.trim();
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public Byte getIsSendSystemNotice() {
        return isSendSystemNotice;
    }

    public void setIsSendSystemNotice(Byte isSendSystemNotice) {
        this.isSendSystemNotice = isSendSystemNotice;
    }

    public Byte getIsSendWeixin() {
        return isSendWeixin;
    }

    public void setIsSendWeixin(Byte isSendWeixin) {
        this.isSendWeixin = isSendWeixin;
    }

    public Byte getIsSendPhoneMessage() {
        return isSendPhoneMessage;
    }

    public void setIsSendPhoneMessage(Byte isSendPhoneMessage) {
        this.isSendPhoneMessage = isSendPhoneMessage;
    }

    public Byte getIsSendEmail() {
        return isSendEmail;
    }

    public void setIsSendEmail(Byte isSendEmail) {
        this.isSendEmail = isSendEmail;
    }

    public Byte getIsSendApppush() {
        return isSendApppush;
    }

    public void setIsSendApppush(Byte isSendApppush) {
        this.isSendApppush = isSendApppush;
    }

    public Byte getIsWirtelog() {
        return isWirtelog;
    }

    public void setIsWirtelog(Byte isWirtelog) {
        this.isWirtelog = isWirtelog;
    }

    public Byte getContentMode() {
        return contentMode;
    }

    public void setContentMode(Byte contentMode) {
        this.contentMode = contentMode;
    }

    public Byte getTitleMode() {
        return titleMode;
    }

    public void setTitleMode(Byte titleMode) {
        this.titleMode = titleMode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getWeixinModelId() {
        return weixinModelId;
    }

    public void setWeixinModelId(String weixinModelId) {
        this.weixinModelId = weixinModelId == null ? null : weixinModelId.trim();
    }

    public String getPhoneMessageModelId() {
        return phoneMessageModelId;
    }

    public void setPhoneMessageModelId(String phoneMessageModelId) {
        this.phoneMessageModelId = phoneMessageModelId == null ? null : phoneMessageModelId.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl == null ? null : responseUrl.trim();
    }

    public Byte getUserReceiveMode() {
        return userReceiveMode;
    }

    public void setUserReceiveMode(Byte userReceiveMode) {
        this.userReceiveMode = userReceiveMode;
    }

    public String getUserReceiveSql() {
        return userReceiveSql;
    }

    public void setUserReceiveSql(String userReceiveSql) {
        this.userReceiveSql = userReceiveSql == null ? null : userReceiveSql.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription == null ? null : modelDescription.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modelId=").append(modelId);
        sb.append(", functionId=").append(functionId);
        sb.append(", isEnable=").append(isEnable);
        sb.append(", isSendSystemNotice=").append(isSendSystemNotice);
        sb.append(", isSendWeixin=").append(isSendWeixin);
        sb.append(", isSendPhoneMessage=").append(isSendPhoneMessage);
        sb.append(", isSendEmail=").append(isSendEmail);
        sb.append(", isSendApppush=").append(isSendApppush);
        sb.append(", isWirtelog=").append(isWirtelog);
        sb.append(", contentMode=").append(contentMode);
        sb.append(", titleMode=").append(titleMode);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", weixinModelId=").append(weixinModelId);
        sb.append(", phoneMessageModelId=").append(phoneMessageModelId);
        sb.append(", tableName=").append(tableName);
        sb.append(", messageType=").append(messageType);
        sb.append(", responseUrl=").append(responseUrl);
        sb.append(", userReceiveMode=").append(userReceiveMode);
        sb.append(", userReceiveSql=").append(userReceiveSql);
        sb.append(", remark=").append(remark);
        sb.append(", modelDescription=").append(modelDescription);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", clientType=").append(clientType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MessageTemplate other = (MessageTemplate) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getModelId() == null ? other.getModelId() == null : this.getModelId().equals(other.getModelId()))
            && (this.getFunctionId() == null ? other.getFunctionId() == null : this.getFunctionId().equals(other.getFunctionId()))
            && (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
            && (this.getIsSendSystemNotice() == null ? other.getIsSendSystemNotice() == null : this.getIsSendSystemNotice().equals(other.getIsSendSystemNotice()))
            && (this.getIsSendWeixin() == null ? other.getIsSendWeixin() == null : this.getIsSendWeixin().equals(other.getIsSendWeixin()))
            && (this.getIsSendPhoneMessage() == null ? other.getIsSendPhoneMessage() == null : this.getIsSendPhoneMessage().equals(other.getIsSendPhoneMessage()))
            && (this.getIsSendEmail() == null ? other.getIsSendEmail() == null : this.getIsSendEmail().equals(other.getIsSendEmail()))
            && (this.getIsSendApppush() == null ? other.getIsSendApppush() == null : this.getIsSendApppush().equals(other.getIsSendApppush()))
            && (this.getIsWirtelog() == null ? other.getIsWirtelog() == null : this.getIsWirtelog().equals(other.getIsWirtelog()))
            && (this.getContentMode() == null ? other.getContentMode() == null : this.getContentMode().equals(other.getContentMode()))
            && (this.getTitleMode() == null ? other.getTitleMode() == null : this.getTitleMode().equals(other.getTitleMode()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getWeixinModelId() == null ? other.getWeixinModelId() == null : this.getWeixinModelId().equals(other.getWeixinModelId()))
            && (this.getPhoneMessageModelId() == null ? other.getPhoneMessageModelId() == null : this.getPhoneMessageModelId().equals(other.getPhoneMessageModelId()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getMessageType() == null ? other.getMessageType() == null : this.getMessageType().equals(other.getMessageType()))
            && (this.getResponseUrl() == null ? other.getResponseUrl() == null : this.getResponseUrl().equals(other.getResponseUrl()))
            && (this.getUserReceiveMode() == null ? other.getUserReceiveMode() == null : this.getUserReceiveMode().equals(other.getUserReceiveMode()))
            && (this.getUserReceiveSql() == null ? other.getUserReceiveSql() == null : this.getUserReceiveSql().equals(other.getUserReceiveSql()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getModelDescription() == null ? other.getModelDescription() == null : this.getModelDescription().equals(other.getModelDescription()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getClientType() == null ? other.getClientType() == null : this.getClientType().equals(other.getClientType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getModelId() == null) ? 0 : getModelId().hashCode());
        result = prime * result + ((getFunctionId() == null) ? 0 : getFunctionId().hashCode());
        result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
        result = prime * result + ((getIsSendSystemNotice() == null) ? 0 : getIsSendSystemNotice().hashCode());
        result = prime * result + ((getIsSendWeixin() == null) ? 0 : getIsSendWeixin().hashCode());
        result = prime * result + ((getIsSendPhoneMessage() == null) ? 0 : getIsSendPhoneMessage().hashCode());
        result = prime * result + ((getIsSendEmail() == null) ? 0 : getIsSendEmail().hashCode());
        result = prime * result + ((getIsSendApppush() == null) ? 0 : getIsSendApppush().hashCode());
        result = prime * result + ((getIsWirtelog() == null) ? 0 : getIsWirtelog().hashCode());
        result = prime * result + ((getContentMode() == null) ? 0 : getContentMode().hashCode());
        result = prime * result + ((getTitleMode() == null) ? 0 : getTitleMode().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getWeixinModelId() == null) ? 0 : getWeixinModelId().hashCode());
        result = prime * result + ((getPhoneMessageModelId() == null) ? 0 : getPhoneMessageModelId().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getMessageType() == null) ? 0 : getMessageType().hashCode());
        result = prime * result + ((getResponseUrl() == null) ? 0 : getResponseUrl().hashCode());
        result = prime * result + ((getUserReceiveMode() == null) ? 0 : getUserReceiveMode().hashCode());
        result = prime * result + ((getUserReceiveSql() == null) ? 0 : getUserReceiveSql().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getModelDescription() == null) ? 0 : getModelDescription().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getClientType() == null) ? 0 : getClientType().hashCode());
        return result;
    }
}