package com.qy.rest.pagination;

import java.util.List;
import java.util.function.Function;

/**
 * 分页接口
 *
 * @author legendjw
 */
public interface Page<T> {
    /**
     * 获取当前页数
     */
    int getPage();

    /**
     * 获取每页数量
     */
    int getPageSize();

    /**
     * 获取总页数
     *
     * @return
     */
    long getTotalPages();

    /**
     * 获取记录总数量
     *
     * @return
     */
    long getTotalRecords();

    /**
     * 获取分页偏移量
     */
    long getOffset();

    /**
     * 获取分页记录
     *
     * @return
     */
    List<T> getRecords();

    /**
     * 返回一个新的记录的分页信息
     *
     * @param converter
     * @param <U>
     * @return
     */
    <U> Page<U> map(Function<? super T, ? extends U> converter);
}
