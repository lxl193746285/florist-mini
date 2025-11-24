package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @serial ark_msg_message_log
 * @version 2018-08-30
 * @author by:liuwei 
 */
public class MessageLog implements Serializable {

    /** id (主健ID) (无默认值) */
    private Integer id;

    /** 模版id(必填项)  (默认值为: 0) */
    private Integer templateId;

    /** 消息类型(必填项)  (默认值为: 0) */
    private Byte messageType;

    /** 标题(必填项)  (默认值为: ) */
    private String title;

    /** 内容(必填项)  (默认值为: ) */
    private String content;

    /** 发送时间(必填项)  (默认值为: 0) */
    private LocalDateTime sendTime;

    /** 接收人(必填项)  (默认值为: 0) */
    private Long receiveUserId;

    /** 发送状态(必填项)  (默认值为: 0) */
    private Byte sendStatus;

    /** 唯一码(必填项)  (默认值为: ) */
    private String uniqueKey;

    /** 反馈时间(必填项)  (默认值为: 0) */
    private Integer feebackTime;
    /** 错误信息(必填项)  (默认值为: ) */
    private String errorMsg;
    /** 消息标识 */
    private String srcId;
    /** 源表行id */
    private Long srcRowId;

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public Long getSrcRowId() {
        return srcRowId;
    }

    public void setSrcRowId(Long srcRowId) {
        this.srcRowId = srcRowId;
    }

    protected static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
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

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey == null ? null : uniqueKey.trim();
    }

    public Integer getFeebackTime() {
        return feebackTime;
    }

    public void setFeebackTime(Integer feebackTime) {
        this.feebackTime = feebackTime;
    }

    public String getErrorMsg() {
      return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
      this.errorMsg = errorMsg;
    }

    
}