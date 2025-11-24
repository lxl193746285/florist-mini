package com.qy.rest.pagination;

/**
 * 分页请求
 *
 * @author legendjw
 */
public class PageRequest implements Pageable {
    private int page;
    private int pageSize;
    private long offset;

    public PageRequest(int page, int pageSize) {
        if (page < 0) {
            throw new IllegalArgumentException("当前分页不得小于0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("分页数量必须大于0");
        }
        this.page = page <= 0 ? 1 : page;
        this.pageSize = pageSize;
        this.offset = this.calculateOffset(page, pageSize);
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    private long calculateOffset(long page, long pageSize) {
        return page <= 1L ? 0L : Math.max((page - 1L) * pageSize, 0L);
    }
}
