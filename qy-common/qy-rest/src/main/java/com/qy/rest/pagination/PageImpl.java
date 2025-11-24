package com.qy.rest.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页的默认实现
 *
 * @author legendjw
 */
public class PageImpl<T> implements Page<T>, Serializable {
    private Pageable pageable;
    private long totalPages;
    private long totalRecords;
    private List<T> records;

    public PageImpl(Pageable pageable, long totalRecords, List<T> records) {
        if (totalRecords < 0L) {
            throw new IllegalArgumentException("分页记录总数量必须大于0");
        }
        this.pageable = pageable;
        this.totalRecords = totalRecords;
        this.records = records;
        this.totalPages = this.calculateTotalPages(pageable.getPageSize(), totalRecords);
    }

    @Override
    public int getPage() {
        return pageable.getPage();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public long getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalRecords() {
        return totalRecords;
    }

    @Override
    public long getOffset() {
        return pageable.getOffset();
    }

    @Override
    public List<T> getRecords() {
        return records;
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(pageable, totalRecords, getConvertedRecords(converter));
    }

    private long calculateTotalPages(int pageSize, long totalRecords) {
        if (pageSize == 0 || totalRecords == 0L) {
            return 0;
        } else {
            long pages = totalRecords / pageSize;
            if (totalRecords % pageSize != 0L) {
                ++pages;
            }

            return pages;
        }
    }

    private <U> List<U> getConvertedRecords(Function<? super T, ? extends U> converter) {
        return this.records.stream().map(converter::apply).collect(Collectors.toList());
    }
}
