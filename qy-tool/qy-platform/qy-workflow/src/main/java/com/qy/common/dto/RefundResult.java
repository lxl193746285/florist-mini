package com.qy.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 退款结果
 *
 * @author legendjw
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundResult {
    /**
     * 结果: SUCCESS（成功） FAIL(失败)
     */
    private String result;

    /**
     * 消息
     */
    private String message;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 退款订单号
     */
    private String refundNo;

    /**
     * 第三方支付平台退款订单号
     */
    private String thirdPartyRefundNo;

    /**
     * 退款金额
     */
    private String refund_amount;

    /**
     * 退款完成时间
     */
    private LocalDateTime refundFinishTime;

    /**
     * 退款是否成功
     *
     * @return
     */
    public boolean isRefundSuccess() {
        return result.equals("SUCCESS");
    }
}