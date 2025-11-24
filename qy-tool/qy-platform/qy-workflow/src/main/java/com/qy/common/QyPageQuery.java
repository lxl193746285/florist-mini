package com.qy.common;

import com.qy.rest.pagination.PageQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 封装一层获取查询分页数据
 */
public class QyPageQuery extends PageQuery {
    public <T> IPage<T> getPagination() {
        Page<T> page = new Page((long) this.getPage(), (long) this.getPerPage());
        return page;
    }
}
