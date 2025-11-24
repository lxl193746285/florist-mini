package com.qy.rest.constant;

/**
 * restful请求通用头部字段
 *
 * @author legendjw
 */
public class RestHeaderConstant {
    //自定义消息
    public static final String CUSTOM_MESSAGE = "X-Message";
    //自定义时间
    public static final String CUSTOM_TIME = "X-Time";

    //分页相关参数
    public static final String PAGINATION_CURRENT_PAGE = "X-Pagination-Current-Page";
    public static final String PAGINATION_PER_PAGE = "X-Pagination-Per-Page";
    public static final String PAGINATION_PAGE_COUNT = "X-Pagination-Page-Count";
    public static final String PAGINATION_TOTAL_COUNT = "X-Pagination-Total-Count";
}