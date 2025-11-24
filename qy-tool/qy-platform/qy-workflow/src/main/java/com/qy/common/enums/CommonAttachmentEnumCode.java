package com.qy.common.enums;

/**
 * 附件
 */
public enum CommonAttachmentEnumCode {
    /**
     * 商品主图
     */
    GOODS_HEAD("mall_goods_head"),

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

    ;

    private String linkType;

    private CommonAttachmentEnumCode(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkType() {
        return this.linkType;
    }
}
