package com.qy.common.dto;

import lombok.Data;

/**
 * 申请退款结果
 *
 * @author legendjw
 */
@Data
public class ApplyRefundResult {
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
     * 下单是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return result.equals("SUCCESS");
    }
}