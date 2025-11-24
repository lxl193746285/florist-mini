package com.qy.attachment.app.application.query;

import com.qy.rest.pagination.PageQuery;
import lombok.Data;

/**
 * 缩略图配置查询
 *
 * @author legendjw
 */
@Data
public class ThumbConfigQuery extends PageQuery {
    /**
     * 关键字
     */
    private String keywords;
}
