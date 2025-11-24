package com.qy.system.app.comment.enums;

/**
 * 附件
 */
public enum CommonAttachmentEnumCode {
    /**
     * 商品主图
     */
    GOODS_HEAD("mall_goods_head"),
    /**
     * 退换凭证
     */
    REFUND_HEAD("mall_refund_freight_head"),
    /**
     * 评论
     */
    COMMENT("mall_comment"),
    /**
     * 商品图文
     */
    GOODS_DETAIL("mall_goods_detail"),

    /**
     * 商品视频
     */
    GOODS_VIDEO("mall_goods_video"),


    /**
     * SKU图片
     */
    SKU_HEAD("mall_goods_info_head"),

    /**
     * SKU商品图文
     */
    SKU_DETAIL("mall_goods_sku_detail"),

    /**
     * SKU商品视频
     */
    SKU_VIDEO("mall_goods_sku_video"),

    /**
     * 账期账单还款
     */
    PAYMENT_DAYS_BILL_REPAYMENT("payment_days_bill_repayment"),

    ;

    private String linkType;

    private CommonAttachmentEnumCode(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkType() {
        return this.linkType;
    }
}
