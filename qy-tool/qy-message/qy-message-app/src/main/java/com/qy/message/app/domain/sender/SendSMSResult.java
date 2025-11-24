package com.qy.message.app.domain.sender;

import com.qy.message.app.domain.valueobject.SmsReceiver;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * 发送结果
 *
 * @author lxl
 */
@Data
public class SendSMSResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isSuccess;

    private String errorMessage;

    private SmsReceiver receiver;

}