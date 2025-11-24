package com.qy.base.interfaces.codetable.web;

import com.qy.codetable.app.application.command.CreateCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableDefaultItemCommand;
import com.qy.codetable.app.application.dto.CodeTableDefaultItemDTO;
import com.qy.codetable.app.application.query.CodeTableDefaultItemQuery;
import com.qy.codetable.app.application.service.CodeTableDefaultItemCommandService;
import com.qy.codetable.app.application.service.CodeTableDefaultItemQueryService;
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
 * 代码表默认细项
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@Api(tags = "代码表默认细项")
@RequestMapping("/v4/code-table/code-default-items")
public class CodeTableDefaultItemController {
    private SessionContext sessionContext;
    private CodeTableDefaultItemQueryService codeTableDefaultItemQueryService;
    private CodeTableDefaultItemCommandService codeTableDefaultItemCommandService;

    public CodeTableDefaultItemController(SessionContext sessionContext, CodeTableDefaultItemQueryService codeTableDefaultItemQueryService, CodeTableDefaultItemCommandService codeTableDefaultItemCommandService) {
        this.sessionContext = sessionContext;
        this.codeTableDefaultItemQueryService = codeTableDefaultItemQueryService;
        this.codeTableDefaultItemCommandService = codeTableDefaultItemCommandService;
    }

    /**
     * 获取代码表细项列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取代码表细项列表")
    @GetMapping
    public ResponseEntity<List<CodeTableDefaultItemDTO>> getCodeTableItems(@Valid CodeTableDefaultItemQuery query) {
        return ResponseUtils.ok().body(codeTableDefaultItemQueryService.getCodeTableItems(query));
    }

    /**
     * 获取单个代码表细项
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个代码表细项")
    @GetMapping("/{id}")
    public ResponseEntity<CodeTableDefaultItemDTO> getCodeTableItemById(
        @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(codeTableDefaultItemQueryService.getCodeTableItemById(id));
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
        @Valid @RequestBody CreateCodeTableDefaultItemCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUser(sessionContext.getUser());
        codeTableDefaultItemCommandService.createCodeTableItem(command);

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
        @Valid @RequestBody UpdateCodeTableDefaultItemCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableDefaultItemCommandService.updateCodeTableItem(command);

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
        DeleteCodeTableDefaultItemCommand command = new DeleteCodeTableDefaultItemCommand();
        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableDefaultItemCommandService.deleteCodeTableItem(command);

        return ResponseUtils.noContent("删除成功").build();
    }
}

