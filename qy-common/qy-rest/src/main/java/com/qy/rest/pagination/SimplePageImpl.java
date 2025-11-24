package com.qy.rest.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页的简单默认实现
 *
 * @author legendjw
 */
public class SimplePageImpl<T> implements Page<T>, Serializable {
    private int page;
    private int pageSize;
    private long totalPages;
    private long totalRecords;
    private long offset;
    private List<T> records;

    public SimplePageImpl(Page page) {
        this.page = page.getPage();
        this.pageSize = page.getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalRecords = page.getTotalRecords();
        this.offset = page.getOffset();
        this.records = page.getRecords();
    }

    public SimplePageImpl(int page, int pageSize, long totalPages, long totalRecords, long offset, List<T> records) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalRecords = totalRecords;
        this.offset = offset;
        this.records = records;
    }

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    @Override
    public List<T> getRecords() {
        return records;
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new SimplePageImpl<>(page, pageSize, totalPages, totalRecords, offset, getConvertedRecords(converter));
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    private <U> List<U> getConvertedRecords(Function<? super T, ? extends U> converter) {
        return this.records.stream().map(converter::apply).collect(Collectors.toList());
    }
}
