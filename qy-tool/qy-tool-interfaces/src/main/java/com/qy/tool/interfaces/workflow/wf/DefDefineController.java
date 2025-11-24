package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import com.qy.wf.defDefine.dto.DefStartDTO;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defDefine.dto.DefDefineDTO;
import com.qy.wf.defDefine.dto.DefDefineFormDTO;
import com.qy.wf.defDefine.dto.DefDefineQueryDTO;
import com.qy.wf.defDefine.entity.DefDefineEntity;
import com.qy.wf.defDefine.service.DefDefineService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 工作流_设计_定义	1-同意，2拒绝，3-退回，4-作废，5撤回
 *
 * @author wch
 * @since 2022-11-12
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-defines")
@Api(tags = "工作流_设计_定义")
public class DefDefineController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefDefineService defDefineService;

    /**
     * 获取工作流_设计_定义
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_定义")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'index')")
    public List<DefDefineDTO> getDefDefines(
        @ModelAttribute DefDefineQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<DefDefineEntity> pm = defDefineService.getDefDefines(iPage, queryDTO, currentUser);
        List<DefDefineDTO> dtos = defDefineService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    @GetMapping("/select")
    @ApiOperation(value = "获取工作流_设计_定义-下拉")
    public List<DefDefineDTO> getDefDefinesSelect(
            @ModelAttribute DefDefineQueryDTO queryDTO,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        IPage iPage = this.getPagination();
        IPage<DefDefineEntity> pm = defDefineService.getDefDefines(iPage, queryDTO, currentUser);
        List<DefDefineDTO> dtos = defDefineService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_定义	1-同意，2拒绝，3-退回，4-作废，5撤回
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_定义")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'view')")
    public DefDefineDTO getDefDefine(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        DefDefineEntity defDefineEntity = defDefineService.getDefDefine(id, currentUser);

        return defDefineService.mapperToDTO(defDefineEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_定义
     *
     * @param defDefineFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_定义")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'create')")
    public ResponseEntity createDefDefine(
        @Validated @RequestBody DefDefineFormDTO defDefineFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        defDefineService.createDefDefine(defDefineFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 修改单个工作流_设计_定义
     *
     * @param id
     * @param defDefineFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_定义")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'update')")
    public ResponseEntity updateDefDefine(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefDefineFormDTO defDefineFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();
        RestUtils.validation(result);

        defDefineService.updateDefDefine(id, defDefineFormDTO, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 删除单个工作流_设计_定义
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_定义")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'delete')")
    public ResponseEntity deleteDefDefine(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        defDefineService.deleteDefDefine(id, currentUser);
        return ResponseUtils.ok("操作成功").build();
    }

    /**
     * 获取流程串
     *
     * @param id
     * @param response
     */
    @GetMapping("/getWfStr/{id}")
    @ApiOperation(value = "获取流程串")
    @PreAuthorize("@memberSystemSessionContext.member.hasPermission('wf_def_define', 'design')")
    public String getWfStr(
            @PathVariable(value = "id") Long id,
            HttpServletResponse response
    ) {
        MemberIdentity currentUser = memberSystemSessionContext.getMember();

        return defDefineService.getWfStr(id, currentUser);
    }

    /**
     * 获取可发起的工作流流程分页
     *
     * @return
     */
    @GetMapping("/can-start-wfs")
    @ApiOperation(value = "获取可发起的工作流流程分页")
    public List<DefStartDTO> getCanStartWFDef() {
        List<DefStartDTO> dtos = defDefineService.getCanStartWFDef();
        return dtos;
    }
}
