package com.qy.base.interfaces.codetable.api;

import com.qy.codetable.app.application.dto.CodeTableItemBasicDTO;
import com.qy.codetable.app.application.enums.CodeTableType;
import com.qy.codetable.app.application.query.CodeTableItemQuery;
import com.qy.codetable.app.application.service.CodeTableItemQueryService;
import com.qy.codetable.app.application.service.CodeTableQueryService;
import com.qy.rest.util.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代码表内部服务接口
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/code-tables")
public class CodeTableApiController {
    private CodeTableItemQueryService codeTableItemQueryService;

    public CodeTableApiController(CodeTableItemQueryService codeTableItemQueryService) {
        this.codeTableItemQueryService = codeTableItemQueryService;
    }

    /**
     * 获取指定系统代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/system/codes/items")
    public ResponseEntity<List<CodeTableItemBasicDTO>> getSystemBasicCodeTableItems(
            @RequestParam(name = "code") String code
    ) {
        CodeTableItemQuery query = new CodeTableItemQuery();
        query.setType(CodeTableType.SYSTEM.name());
        query.setCode(code);
        return ResponseUtils.ok().body(codeTableItemQueryService.getBasicCodeTableItems(query));
    }

    /**
     * 获取指定组织下代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/organization/codes/items")
    public ResponseEntity<List<CodeTableItemBasicDTO>> getOrganizationBasicCodeTableItems(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code
    ) {
        CodeTableItemQuery query = new CodeTableItemQuery();
        query.setType(CodeTableType.ORGANIZATION.name());
        query.setRelatedId(organizationId);
        query.setCode(code);
        return ResponseUtils.ok().body(codeTableItemQueryService.getBasicCodeTableItems(query));
    }

    /**
     * 获取指定组织下代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/personal/codes/items")
    public ResponseEntity<List<CodeTableItemBasicDTO>> getPersonalBasicCodeTableItems(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code
    ) {
        CodeTableItemQuery query = new CodeTableItemQuery();
        query.setType(CodeTableType.PERSONAL.name());
        query.setRelatedId(userId);
        query.setCode(code);
        return ResponseUtils.ok().body(codeTableItemQueryService.getBasicCodeTableItems(query));
    }

    /**
     * 获取系统代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/system/codes/item")
    public ResponseEntity<CodeTableItemBasicDTO> getSystemCodeTableItem(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getSystemCodeTableItem(code, value));
    }

    /**
     * 获取系统代码表细项名称
     *
     * @param code
     * @return
     */
    @GetMapping("/system/codes/item-name")
    public ResponseEntity<String> getSystemCodeTableItemName(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        CodeTableItemBasicDTO codeTableItemBasicDTO = codeTableItemQueryService.getSystemCodeTableItem(code, value);
        return ResponseUtils.ok().body(codeTableItemBasicDTO == null ? "" : codeTableItemBasicDTO.getName());
    }

    /**
     * 获取组织代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/organization/codes/item")
    public ResponseEntity<CodeTableItemBasicDTO> getOrganizationCodeTableItem(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getOrganizationCodeTableItem(organizationId, code, value));
    }

    /**
     * 获取组织代码表细项名称
     *
     * @param code
     * @return
     */
    @GetMapping("/organization/codes/item-name")
    public ResponseEntity<String> getOrganizationCodeTableItemName(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        CodeTableItemBasicDTO codeTableItemBasicDTO = codeTableItemQueryService.getOrganizationCodeTableItem(organizationId, code, value);
        return ResponseUtils.ok().body(codeTableItemBasicDTO == null ? "" : codeTableItemBasicDTO.getName());
    }

    /**
     * 获取个人代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/personal/codes/item")
    public ResponseEntity<CodeTableItemBasicDTO> getPersonalCodeTableItem(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        return ResponseUtils.ok().body(codeTableItemQueryService.getPersonalCodeTableItem(userId, code, value));
    }

    /**
     * 获取个人代码表细项名称
     *
     * @param userId
     * @param code
     * @param value
     * @return
     */
    @GetMapping("/personal/codes/item-name")
    public ResponseEntity<String> getPersonalCodeTableItemName(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    ) {
        CodeTableItemBasicDTO codeTableItemBasicDTO = codeTableItemQueryService.getPersonalCodeTableItem(userId, code, value);
        return ResponseUtils.ok().body(codeTableItemBasicDTO == null ? "" : codeTableItemBasicDTO.getName());
    }
}


