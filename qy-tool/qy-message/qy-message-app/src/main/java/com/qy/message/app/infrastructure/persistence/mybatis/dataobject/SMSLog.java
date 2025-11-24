package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @serial sms_log (短信日志)
 * @version 2025-03-28
 * @author by:lxl
 */
public class SMSLog implements Serializable {

    /** id (主健ID) (无默认值) */
    private Integer id;

    /** 发送时间(必填项)  (默认值为: 0) */
    private LocalDateTime sendTime;

    /** 发送类型1.单发，2.群发 */
    private Integer type;

    /** 接收人手机号 */
    private String phone;

    /** 参数内容 */
    private String params;

    /** 状态码 */
    private String resultCode;

    /** 结果描述 */
    private String message;

    /** 回执id */
    private String bizid;

    /** 请求ID */
    private String requestId;

    /** 原始报文 */
    private String result;

    /** 发送人id */
    private Long senderId;

    /** 发送数量 */
    private Integer sendNum;

    /** 场景 */
    private String scene;

    /** 发送状态（1发送成功2发送失败） */
    private Integer status;



    protected static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer messageType) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getBizid() {
        return bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid == null ? null : bizid.trim();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Integer getSendNum() {
        return sendNum;
    }

    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}