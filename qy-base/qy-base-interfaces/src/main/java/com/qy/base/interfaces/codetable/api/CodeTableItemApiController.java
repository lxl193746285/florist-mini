package com.qy.base.interfaces.codetable.api;

import com.qy.codetable.app.application.dto.CodeTableItemDTO;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.application.service.CodeTableItemCommandService;
import com.qy.codetable.app.application.service.CodeTableItemQueryService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.SessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码表细项
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/api/code-table/code-items")
public class CodeTableItemApiController {
    private SessionContext sessionContext;
    private CodeTableItemQueryService codeTableItemQueryService;
    private CodeTableItemCommandService codeTableItemCommandService;

    public CodeTableItemApiController(SessionContext sessionContext, CodeTableItemQueryService codeTableItemQueryService, CodeTableItemCommandService codeTableItemCommandService) {
        this.sessionContext = sessionContext;
        this.codeTableItemQueryService = codeTableItemQueryService;
        this.codeTableItemCommandService = codeTableItemCommandService;
    }

    /**
     * 获取代码表细项列表
     *
     * @param query
     * @return
     */
    @GetMapping
    public ResponseEntity<SimplePageImpl<CodeTableItemDTO>> getCodeTableItems(CodeTableItemQuery query) {
        Page<CodeTableItemDTO> page = codeTableItemQueryService.getCodeTableItems(query);
        return ResponseUtils.ok().body(new SimplePageImpl(page));
    }
}