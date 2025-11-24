package com.qy.base.interfaces.region.web;

import com.qy.region.app.application.command.CreateAreaCommand;
import com.qy.region.app.application.command.UpdateAreaCommand;
import com.qy.region.app.application.dto.AreaDTO;
import com.qy.region.app.application.query.AreaQuery;
import com.qy.region.app.application.service.AreaCommandService;
import com.qy.region.app.application.service.AreaQueryService;
import com.qy.rest.pagination.Page;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberSystemSessionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 地区
 *
 * @author legendjw
 * @since 2021-07-23
 */
@Api(tags = "地区")
@RestController
@RequestMapping("/v4/region/areas")
public class AreaController {
    private AreaQueryService areaQueryService;
    private AreaCommandService areaCommandService;
    private MemberSystemSessionContext sessionContext;

    public AreaController(AreaQueryService areaQueryService, AreaCommandService areaCommandService, MemberSystemSessionContext sessionContext) {
        this.areaQueryService = areaQueryService;
        this.areaCommandService = areaCommandService;
        this.sessionContext = sessionContext;
    }

    /**
     * 获取地区
     *
     * @param query
     * @return
     */
    @ApiOperation("获取地区")
    @GetMapping
    public ResponseEntity<List<AreaDTO>> getAreas(@Valid AreaQuery query) {
        if (query.getPage() == null) {
            return ResponseUtils.ok().body(areaQueryService.getAreas(query, sessionContext.getMember()));
        } else {
            Page<AreaDTO> iPage = areaQueryService.getAreasPage(query, sessionContext.getMember());
            return ResponseUtils.ok(iPage).body(iPage.getRecords());
        }
    }

    /**
     * 新增地区
     *
     * @param command
     * @return
     */
    @ApiOperation("新增地区")
    @PostMapping
    public ResponseEntity<Object> createAreas(@RequestBody CreateAreaCommand command) {
        areaCommandService.createArea(command);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改地区
     *
     * @param command
     * @return
     */
    @ApiOperation("修改地区")
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateAreas(
            @RequestBody UpdateAreaCommand command,
            @PathVariable(value = "id") Long id
    ) {
        command.setId(id);
        areaCommandService.updateArea(command);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除地区
     *
     * @return
     */
    @ApiOperation("删除地区")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAreas(
            @PathVariable(value = "id") Long id
    ) {
        areaCommandService.deleteArea(id);
        return ResponseUtils.ok("删除成功").build();
    }


    /**
     * 获取地区
     *
     * @param query
     * @return
     */
    @ApiOperation("app获取地区")
    @GetMapping("/app")
    public ResponseEntity<List<AreaDTO>> getAreasNoToken(@Valid AreaQuery query) {
        return ResponseUtils.ok().body(areaQueryService.getAreas(query));
    }
}