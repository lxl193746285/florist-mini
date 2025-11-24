package com.qy.tool.interfaces.message.web;

import com.qy.message.app.application.command.CreatePlatformCommand;
import com.qy.message.app.application.command.UpdatePlatformCommand;
import com.qy.message.app.application.dto.PlatformDTO;
import com.qy.message.app.application.query.PlatformQuery;
import com.qy.message.app.application.service.PlatformCommandService;
import com.qy.message.app.application.service.PlatformQueryService;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.OrganizationSessionContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 消息平台
 *
 * @author legendjw
 * @since 2021-07-23
 */
@RestController
@RequestMapping("/v4/message/platforms")
public class PlatformController {
    private OrganizationSessionContext sessionContext;
    private PlatformCommandService platformCommandService;
    private PlatformQueryService platformQueryService;

    public PlatformController(OrganizationSessionContext sessionContext, PlatformCommandService platformCommandService, PlatformQueryService platformQueryService) {
        this.sessionContext = sessionContext;
        this.platformCommandService = platformCommandService;
        this.platformQueryService = platformQueryService;
    }

    /**
     * 获取消息平台列表
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getPlatforms(PlatformQuery query) {
        Page<PlatformDTO> page = platformQueryService.getPlatforms(query, sessionContext.getUser());

        return ResponseUtils.ok(page).body(page.getRecords());
    }

    /**
     * 获取单个消息平台
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlatformDTO> getPlatform(
        @PathVariable(value = "id") Long id
    ) {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(id, sessionContext.getUser());
        return ResponseUtils.ok().body(platformDTO);
    }

    /**
     * 创建单个消息平台
     *
     * @param command
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> createPlatform(
            @Valid @RequestBody CreatePlatformCommand command,
            BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        platformCommandService.createPlatform(command, sessionContext.getEmployee());

        return ResponseUtils.ok("消息平台创建成功").build();
    }

    /**
     * 修改单个消息平台
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePlatform(
        @PathVariable(value = "id") Long id,
        @Valid @RequestBody UpdatePlatformCommand command,
        BindingResult result
    ) {
        ValidationUtils.handleValidationResult(result);

        command.setId(id);
        platformCommandService.updatePlatform(command, sessionContext.getEmployee());

        return ResponseUtils.ok("消息平台修改成功").build();
    }

    /**
     * 删除单个消息平台
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePlatform(
        @PathVariable(value = "id") Long id
    ) {
        PlatformDTO platformDTO = findPlatform(id, null);

        platformCommandService.deletePlatform(id, sessionContext.getEmployee());

        return ResponseUtils.noContent("删除消息平台成功").build();
    }

    /**
     * 查找消息平台
     *
     * @param id
     * @return
     */
    private PlatformDTO findPlatform(Long id, Identity identity) {
        PlatformDTO platformDTO = platformQueryService.getPlatformById(id, identity);
        if (platformDTO == null) {
            throw new NotFoundException("未找到指定的消息平台");
        }
        return platformDTO;
    }
}

