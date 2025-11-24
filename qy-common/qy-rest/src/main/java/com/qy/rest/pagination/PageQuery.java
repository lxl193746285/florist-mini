package com.qy.rest.pagination;

import com.qy.rest.constant.PaginationConstant;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分类查询
 *
 * @author legendjw
 */
public class PageQuery implements Serializable {
    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private int page = PaginationConstant.DEFAULT_PAGE;
    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    private int perPage = PaginationConstant.DEFAULT_PAGE_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
