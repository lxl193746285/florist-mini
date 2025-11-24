package com.baomidou.mybatisplus.extension.plugins.pagination;

/**
 * Minimal PageDTO implementation to keep MyBatis-Plus based repositories compiling.
 * Extends {@link Page} so downstream code can continue using the familiar API.
 *
 * @param <T> data type
 */
public class PageDTO<T> extends Page<T> {

    public PageDTO() {
        super();
    }

    public PageDTO(long current, long size) {
        super(current, size);
    }

    public PageDTO(long current, long size, boolean searchCount) {
        super(current, size, searchCount);
    }

    public PageDTO(long current, long size, long total) {
        super(current, size, total);
    }

    public PageDTO(long current, long size, long total, boolean searchCount) {
        super(current, size, total, searchCount);
    }

    public static <T> PageDTO<T> of(long current, long size) {
        return new PageDTO<>(current, size);
    }

    public static <T> PageDTO<T> of(long current, long size, boolean searchCount) {
        return new PageDTO<>(current, size, searchCount);
    }
}
