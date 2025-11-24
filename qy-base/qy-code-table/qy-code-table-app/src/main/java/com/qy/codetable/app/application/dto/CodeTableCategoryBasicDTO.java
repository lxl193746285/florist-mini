package com.qy.codetable.app.application.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本代码表分类
 *
 * @author legendjw
 */
@Data
public class CodeTableCategoryBasicDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 标示码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类下的代码表
     */
    private List<CodeTableDTO> codeTables = new ArrayList<>();
}
