package com.qy.tool.interfaces.workflow.workflow;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.workflow.dto.WfRunWfDTO;
import com.qy.workflow.dto.WfRunWfFormDTO;
import com.qy.workflow.dto.WfRunWfQueryDTO;
import com.qy.workflow.entity.WfRunWfEntity;
import com.qy.workflow.service.WfRunWfService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作流_执行_工作流
 *
 * @author iFeng
 * @since 2022-11-16
 */
@RestController
@Validated
@RequestMapping("/v4/platform/workflow/wf-run-wfs")
@Api(tags = "工作流_执行_工作流")
public class WfRunWfController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private WfRunWfService wfRunWfService;

    /**
     * 获取工作流_执行_工作流列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_执行_工作流分页列表")
    public List<WfRunWfDTO> getWfRunWfs(
        @ModelAttribute WfRunWfQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<WfRunWfEntity> pm = wfRunWfService.getWfRunWfs(iPage, queryDTO, currentUser);
        List<WfRunWfDTO> dtos = wfRunWfService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_执行_工作流
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_执行_工作流")
    public WfRunWfDTO getWfRunWf(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        WfRunWfEntity wfRunWfEntity = wfRunWfService.getWfRunWf(id, currentUser);

        return wfRunWfService.mapperToDTO(wfRunWfEntity, currentUser);
    }

    /**
     * 创建单个工作流_执行_工作流
     *
     * @param wfRunWfFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_执行_工作流")
    public ResponseEntity createWfRunWf(
        @Validated @RequestBody WfRunWfFormDTO wfRunWfFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunWfService.createWfRunWf(wfRunWfFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_执行_工作流
     *
     * @param id
     * @param wfRunWfFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_执行_工作流")
    public ResponseEntity updateWfRunWf(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody WfRunWfFormDTO wfRunWfFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        wfRunWfService.updateWfRunWf(id, wfRunWfFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_执行_工作流
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_执行_工作流")
    public ResponseEntity deleteWfRunWf(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        wfRunWfService.deleteWfRunWf(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

