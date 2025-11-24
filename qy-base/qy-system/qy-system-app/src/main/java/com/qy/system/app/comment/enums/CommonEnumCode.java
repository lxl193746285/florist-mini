package com.qy.system.app.comment.enums;

/**
 * 字典
 */
public enum CommonEnumCode {
    /**
     * 审核状态
     */
    AUDIT_STATUS("common_audit_status"),

    /**
     * 启用禁用状态
     */
    STATUS("common_status"),

    /**
     * 是否状态
     */
    YES_OR_NO("common_yes_or_no"),

    /**
     * 支付方式
     */
    PAY_TYPE("xdzsj_pay_type"),

    /**
     * 进货单状态
     */
    STOCK_ORDER_TYPE("xdzsj_order_type"),

    /**
     * 打款状态
     */
    TRANSFER_ACCOUNTS_STATUS("transfer_accounts_status"),

    /**
     * 会员优惠券状态
     */
    ARK_MALL_MEMBER_COUPON_STATUS("ark_mall_member_coupon_status"),

    /**
     * 广告类型
     */
    ARK_ADVERTISEMENT_TYPE_ID("ARK_ADVERTISEMENT_TYPE_ID"),

    /**
     * 合同状态
     */
    ARK_MALL_QUOTA_CONTRACT_STATUS("ark_mall_quota_contract_status"),

    /**
     * 合同审核状态
     */
    ARK_MALL_QUOTA_CONTRACT_AUDIT_STATUS("ark_mall_quota_contract_audit_status"),

    /**
     * 账单状态
     */
    ARK_MALL_QUOTA_BILL_STATUS("ark_mall_quota_bill_status"),

    /**
     * 账单审核状态
     */
    ARK_MALL_QUOTA_BILL_AUDIT_STATUS("ark_mall_quota_bill_audit_status"),

    /**
     * 账单还款状态
     */
    ARK_MALL_QUOTA_BILL_REPAYMENT_STATUS("ark_mall_quota_bill_repayment_status"),

    /**
     * 账单明细付款状态
     */
    ARK_MALL_QUOTA_BILL_ITEM_REPAYMENT_STATUS("ark_mall_quota_bill_item_repayment_status"),

    /**
     * 还款类型
     */
    ARK_MALL_QUOTA_REPAYMENT_TYPE("ark_mall_quota_repayment_type"),

    /**
     * 备件发货状态
     */
    ARK_WMS_PARTS_DELIVERY_STATUS("ark_wms_parts_delivery_status"),

    /**
     * 备件发货补件原因
     */
    ARK_WMS_PARTS_DELIVERY_DETAIL_SUPPLEMENT_STATUS("ark_wms_parts_delivery_detail_supplement_status"),

    /**
     * 备件发货日志状态
     */
    ARK_WMS_PARTS_DELIVERY_LOG_STATUS("ark_wms_parts_delivery_log_status"),

    /**
     * 公告通知分类
     */
    ARK_MALL_NOTICE_CATEGORY("ark_mall_notice_category"),

    /**
     * 编号循环周期
     */
    ARK_MALL_SNSET_CIRCLE("ark_mall_snset_circle"),

    /**
     * 物流方式
     */
    LOGISTICS_MODEL("mall_logistics_mode"),
    ;

    private String code;

    private CommonEnumCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
