package com.qy.base.interfaces.codetable.web;

import com.qy.codetable.app.application.command.CreateCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.DeleteCodeTableCategoryCommand;
import com.qy.codetable.app.application.command.UpdateCodeTableCategoryCommand;
import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.query.CodeTableCategoryQuery;
import com.qy.codetable.app.application.service.CodeTableCategoryCommandService;
import com.qy.codetable.app.application.service.CodeTableCategoryQueryService;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.SessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 代码表分类
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "代码表分类")
@RestController
@RequestMapping("/v4/code-table/categorys")
public class CodeTableCategoryController {
    private SessionContext sessionContext;
    private CodeTableCategoryQueryService codeTableCategoryQueryService;
    private CodeTableCategoryCommandService codeTableCategoryCommandService;

    public CodeTableCategoryController(SessionContext sessionContext, CodeTableCategoryQueryService codeTableCategoryQueryService, CodeTableCategoryCommandService codeTableCategoryCommandService) {
        this.sessionContext = sessionContext;
        this.codeTableCategoryQueryService = codeTableCategoryQueryService;
        this.codeTableCategoryCommandService = codeTableCategoryCommandService;
    }

    /**
     * 获取代码表分类列表
     *
     * @param query
     * @return
     */
    @ApiOperation("获取代码表分类列表")
    @GetMapping
    public ResponseEntity<List<CodeTableCategoryDTO>> getCodeTableCategorys(@Valid CodeTableCategoryQuery query) {
        return ResponseUtils.ok().body(codeTableCategoryQueryService.getCodeTableCategorys(query, sessionContext.getUser()));
    }

    /**
     * 获取单个代码表分类
     *
     * @param id
     * @return
     */
    @ApiOperation("获取单个代码表分类")
    @GetMapping("/{id}")
    public ResponseEntity<CodeTableCategoryDTO> getCodeTableCategoryById(
        @PathVariable(value = "id") Long id
    ) {
        return ResponseUtils.ok().body(codeTableCategoryQueryService.getCodeTableCategoryById(id));
    }

    /**
     * 创建单个代码表分类
     *
     * @param command
     * @return
     */
    @ApiOperation("创建单个代码表分类")
    @PostMapping
    public ResponseEntity<Object> createCodeTableCategory(
        @Valid @RequestBody CreateCodeTableCategoryCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setUser(sessionContext.getUser());
        codeTableCategoryCommandService.createCodeTableCategory(command);

        return ResponseUtils.ok("代码表分类创建成功").build();
    }

    /**
     * 修改单个代码表分类
     *
     * @param id
     * @param command
     * @return
     */
    @ApiOperation("修改单个代码表分类")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateCodeTableCategory(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdateCodeTableCategoryCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableCategoryCommandService.updateCodeTableCategory(command);

        return ResponseUtils.ok("代码表分类修改成功").build();
    }

    /**
     * 删除单个代码表分类
     *
     * @param id
     */
    @ApiOperation("删除单个代码表分类")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCodeTableCategory(
        @PathVariable(value = "id") Long id
    ) {
        DeleteCodeTableCategoryCommand command = new DeleteCodeTableCategoryCommand();
        command.setId(id);
        command.setUser(sessionContext.getUser());
        codeTableCategoryCommandService.deleteCodeTableCategory(command);

        return ResponseUtils.noContent("删除代码表分类成功").build();
    }
}

