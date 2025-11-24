package com.qy.base.interfaces.codetable.web;

import com.qy.codetable.app.application.command.BatchDeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.CreateCodeTableItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableItemCommand;
import com.qy.codetable.app.application.dto.CodeTableItemDTO;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.application.service.CodeTableItemCommandService;
import com.qy.codetable.app.application.service.CodeTableItemQueryService;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 代码表细项
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "代码表细项")
@RestController
@RequestMapping("/v4/code-table/code-items")
public class CodeTableItemController {
    private SessionContext sessionContext;
    private CodeTableItemQueryService codeTableItemQueryService;
    private CodeTableItemCommandService codeTableItemCommandService;

    public CodeTableItemController(SessionContext sessionContext, CodeTableItemQueryService codeTableItemQueryService, CodeTableItemCommandService codeTableItemCommandService) {
        this.sessionContext = sessionContext;
        this.codeTableItemQueryService = codeTableItemQueryService;
        this.codeTableItemCommandService = codeTableItemCommandService;
    }

    /**
     * 获取代码表细项分页列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取代码表细项分页列表")
    @GetMapping
    public ResponseEntity<List<CodeTableItemDTO>> getCodeTableItems(CodeTableItemQuery query) {
        Page<CodeTableItemDTO> page = codeTableItemQueryService.getCodeTableItems(query);
        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取代码表所有细项（不分页）
     *
     * @param query
     * @return
     */
    @ApiOperation("获取代码表所有细项（不分页）")
    @GetMapping("/all")
    public ResponseEntity<List<CodeTableItemDTO>> getAllCodeTableItems(CodeTableItemQuery query) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getAllCodeTableItems(query));
    }

    /**
     * 获取单个代码表细项
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个代码表细项")
    @GetMapping("/{id}")
    public ResponseEntity<CodeTableItemDTO> getCodeTableItemById(
        @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getCodeTableItemById(id));
    }

    /**
     * 创建单个代码表细项
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个代码表细项")
    @PostMapping
    public ResponseEntity<Object> createCodeTableItem(
        @Valid @RequestBody CreateCodeTableItemCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUser(sessionContext.getUser());
        codeTableItemCommandService.createCodeTableItem(command);

        return ResponseUtils.ok("创建成功").build();
    }

    /**
     * 修改单个代码表细项
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个代码表细项")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCodeTableItem(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateCodeTableItemCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableItemCommandService.updateCodeTableItem(command);

        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个代码表细项
     *
     * @param id
     */
    @ApiOperation("删除单个代码表细项")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCodeTableItem(
        @PathVariable(value = "id") Long id
    ) {
        DeleteCodeTableItemCommand command = new DeleteCodeTableItemCommand();
        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableItemCommandService.deleteCodeTableItem(command);

        return ResponseUtils.noContent("删除成功").build();
    }

    /**
     * 批量删除代码表细项
     *
     * @param ids
     */
    @ApiOperation("批量删除代码表细项")
    @DeleteMapping
    public ResponseEntity<Object> batchDeleteCodeTableItem(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        BatchDeleteCodeTableItemCommand command = new BatchDeleteCodeTableItemCommand();
        command.setIds(ids);
        command.setUser(sessionContext.getUser());
        codeTableItemCommandService.deleteCodeTableItem(command);

        return ResponseUtils.noContent("批量删除成功").build();
    }
}

