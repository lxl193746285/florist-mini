package com.qy.base.interfaces.system.web;

import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.system.app.autonumber.dto.SnSetsDTO;
import com.qy.system.app.autonumber.dto.SnSetsFormDTO;
import com.qy.system.app.autonumber.dto.SnSetsQueryDTO;
import com.qy.system.app.autonumber.entity.SnSetsEntity;
import com.qy.system.app.autonumber.service.SnSetsService;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.util.RestUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 编号规则设置 前端控制器
 *
 * @author ln
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/v4/system/autonumber")
public class SnSetsController extends BaseController {
    @Autowired
    private OrganizationSessionContext sessionService;
    @Autowired
    private SnSetsService snSetsService;

    /**
     * 获取编号设置列表
     * @param snSetsQueryDTO
     * @param response
     * @return
     */
    @GetMapping
    public ResponseEntity<List<SnSetsDTO>> getSNSets(
            @ModelAttribute SnSetsQueryDTO snSetsQueryDTO,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();
        IPage iPage = this.getPagination();
        IPage<SnSetsEntity> pm = snSetsService.getSnSets(iPage, snSetsQueryDTO, currentUser);
        RestUtils.initResponseByPage(pm, response);
        return ResponseUtils.ok().body(snSetsService.mapperToDTO(pm.getRecords(), currentUser));
    }

    /**
     * 获取单个编号设置
     * @param noid
     * @param response
     * @return
     */
    @GetMapping("/{noid}")
    public ResponseEntity<SnSetsDTO> getSnSet(
        @PathVariable(value = "noid") String noid,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();

        SnSetsEntity snsetsEntity = snSetsService.getSnSet(noid,currentUser);

        return ResponseUtils.ok().body(snSetsService.mapperToDTO(snsetsEntity, currentUser));
    }

    /**
     * 创建编号规则设置
     * @param snSetsFormDTO
     * @param result
     * @param response
     * @return
     */
    @PostMapping
    public ResponseEntity createSnset(
            @Valid @RequestBody SnSetsFormDTO snSetsFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();
        RestUtils.validation(result);

        SnSetsEntity snSetsEntity = snSetsService.createSnSet(snSetsFormDTO, currentUser);

        return ResponseUtils.ok("编号规则新增成功").build();
    }

    /**
     * 修改单个编号规则设置
     * @param noid
     * @param snSetsFormDTO
     * @param result
     * @param response
     * @return
     */
    @PatchMapping("/{noid}")
    public ResponseEntity updateSnSets(
        @PathVariable(value = "noid") String noid,
        @Valid @RequestBody SnSetsFormDTO snSetsFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();
        RestUtils.validation(result);
        snSetsFormDTO.setNoid(noid);
        snSetsService.updateSnSet(snSetsFormDTO, currentUser);
        return ResponseUtils.ok("编号规则设置修改成功").build();
    }

    /**
     * 启用禁用（用于更新状态值）
     * @param noid
     * @param snSetsFormDTO
     * @param result
     * @param response
     * @return
     */
    @PatchMapping("/status/{noid}")
    public ResponseEntity updateSnSetsStatus(
            @PathVariable(value = "noid") String noid,
            @Valid @RequestBody SnSetsFormDTO snSetsFormDTO,
            BindingResult result,
            HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();
        RestUtils.validation(result);

        SnSetsEntity snSetsEntity = snSetsService.updateSnSetsStatus(noid, snSetsFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个编号设置
     * @param noid
     * @param response
     * @return
     */
    @DeleteMapping("/{noid}")
    public ResponseEntity deleteSnSets(
        @PathVariable(value = "noid") String noid,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = sessionService.getEmployee();

        snSetsService.deleteSnSet(noid, currentUser);
        return ResponseUtils.ok("编号规则设置删除成功").build();
    }
}

