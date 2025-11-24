package com.qy.rest.pagination;

/**
 * 分页信息
 *
 * @author legendjw
 */
public interface Pageable {
    /**
     * 获取当前页数
     */
    int getPage();

    /**
     * 获取每页数量
     */
    int getPageSize();

    /**
     * 获取分页偏移量
     */
    long getOffset();
}
