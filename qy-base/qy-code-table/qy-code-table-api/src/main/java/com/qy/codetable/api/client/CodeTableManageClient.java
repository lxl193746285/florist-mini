package com.qy.codetable.api.client;

import com.qy.codetable.api.command.CreateCodeTableItemCommand;
import com.qy.codetable.api.command.UpdateCodeTableItemCommand;
import com.qy.codetable.api.dto.CodeTableDTO;
import com.qy.codetable.api.dto.CodeTableItemDTO;
import com.qy.codetable.api.query.CodeTableItemQuery;
import com.qy.codetable.api.query.CodeTableQuery;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 代码表项内部服务
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-code-table-item", configuration = FeignTokenRequestInterceptor.class)
public interface CodeTableManageClient {
    /**
     * 获取代码表列表
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/code-table/codes")
    List<CodeTableDTO> getCodeTables(@SpringQueryMap CodeTableQuery query);

    /**
     * 获取代码表细项分页列表
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/code-table/code-items")
    SimplePageImpl<CodeTableItemDTO> getCodeTableItems(@SpringQueryMap CodeTableItemQuery query);

    /**
     * 获取代码表所有细项（不分页）
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/code-table/code-items/all")
    List<CodeTableItemDTO> getAllCodeTableItems(@SpringQueryMap CodeTableItemQuery query);

    /**
     * 获取单个代码表细项
     *
     * @param id
     * @return
     */
    @GetMapping("/v4/code-table/code-items/{id}")
    CodeTableItemDTO getCodeTableItemById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建单个代码表细项
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/code-table/code-items")
    void createCodeTableItem(
            @Valid @RequestBody CreateCodeTableItemCommand command
    );

    /**
     * 修改单个代码表细项
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/code-table/code-items/{id}")
    void updateCodeTableItem(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateCodeTableItemCommand command
    );

    /**
     * 删除单个代码表细项
     *
     * @param id
     */
    @DeleteMapping("/v4/code-table/code-items/{id}")
    void deleteCodeTableItem(
            @PathVariable(value = "id") Long id
    );

    /**
     * 批量删除代码表细项
     *
     * @param ids
     */
    @DeleteMapping("/v4/code-table/code-items")
    void batchDeleteCodeTableItem(
            @RequestParam(value = "ids") List<Long> ids
    );
}
