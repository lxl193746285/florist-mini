package com.qy.base.interfaces.codetable.web;

import com.qy.codetable.app.application.command.CreateCodeTableCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCommand;
import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.dto.CodeTableDTO;
import com.qy.codetable.app.application.dto.CodeTableItemBasicDTO;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.application.query.CodeTableQuery;
import com.qy.codetable.app.application.service.CodeTableCommandService;
import com.qy.codetable.app.application.service.CodeTableItemQueryService;
import com.qy.codetable.app.application.service.CodeTableQueryService;
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
 * 代码表
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@Api(tags = "代码表")
@RequestMapping("/v4/code-table/codes")
public class CodeTableController {
    private SessionContext sessionContext;
    private CodeTableQueryService codeTableQueryService;
    private CodeTableCommandService codeTableCommandService;
    private CodeTableItemQueryService codeTableItemQueryService;

    public CodeTableController(SessionContext sessionContext, CodeTableQueryService codeTableQueryService, CodeTableCommandService codeTableCommandService, CodeTableItemQueryService codeTableItemQueryService) {
        this.sessionContext = sessionContext;
        this.codeTableQueryService = codeTableQueryService;
        this.codeTableCommandService = codeTableCommandService;
        this.codeTableItemQueryService = codeTableItemQueryService;
    }

    /**
     * 获取按照分类分组的代码表
     *
     * @param type
     * @return
     */
    @ApiOperation("获取按照分类分组的代码表")
    @GetMapping("/group-by-category")
    public ResponseEntity<List<CodeTableCategoryDTO>> getCodeTablesGroupByCategory(
            @RequestParam("type") String type
    ) {
        return ResponseUtils.ok().body(codeTableQueryService.getCodeTablesGroupByCategory(type, sessionContext.getUser()));
    }

    /**
     * 获取代码表列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取代码表列表")
    @GetMapping
    public ResponseEntity<List<CodeTableDTO>> getCodeTables(@Valid CodeTableQuery query) {
        return ResponseUtils.ok().body(codeTableQueryService.getCodeTables(query));
    }

    /**
     * 获取指定代码表下的所有细项
     *
     * @param query
     * @return
     */
    @ApiOperation("获取指定代码表下的所有细项")
    @GetMapping("/items")
    public ResponseEntity<List<CodeTableItemBasicDTO>> getCodeTableItems(@Valid CodeTableItemQuery query) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getBasicCodeTableItems(query));
    }

    /**
     * 获取单个代码表
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个代码表")
    @GetMapping("/{id}")
    public ResponseEntity<CodeTableDTO> getCodeTableById(
        @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(codeTableQueryService.getCodeTableById(id));
    }

    /**
     * 创建单个代码表
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个代码表")
    @PostMapping
    public ResponseEntity<Object> createCodeTable(
        @Valid @RequestBody CreateCodeTableCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUser(sessionContext.getUser());
        codeTableCommandService.createCodeTable(command);

        return ResponseUtils.ok("代码表创建成功").build();
    }

    /**
     * 修改单个代码表
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个代码表")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCodeTable(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateCodeTableCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableCommandService.updateCodeTable(command);

        return ResponseUtils.ok("代码表修改成功").build();
    }

    /**
     * 删除单个代码表
     *
     * @param id
     */
    @ApiOperation("删除单个代码表")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCodeTable(
        @PathVariable(value = "id") Long id
    ) {
        DeleteCodeTableCommand command = new DeleteCodeTableCommand();
        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableCommandService.deleteCodeTable(command);

        return ResponseUtils.noContent("删除代码表成功").build();
    }
}

