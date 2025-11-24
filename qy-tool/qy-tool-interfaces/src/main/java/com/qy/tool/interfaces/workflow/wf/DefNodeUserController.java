package com.qy.tool.interfaces.workflow.wf;

import com.qy.common.BaseController;
import org.springframework.web.bind.annotation.*;
import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.RestUtils;
import com.qy.wf.defNodeUser.dto.DefNodeUserDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserFormDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserQueryDTO;
import com.qy.wf.defNodeUser.entity.DefNodeUserEntity;
import com.qy.wf.defNodeUser.service.DefNodeUserService;
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
 * 工作流_设计_节点人员
 *
 * @author syf
 * @since 2022-11-14
 */
@RestController
@Validated
@RequestMapping("/v4/wf/def-node-users")
@Api(tags = "工作流_设计_节点人员")
public class DefNodeUserController  extends BaseController {
    @Autowired
    private OrganizationSessionContext context;
    @Autowired
    private DefNodeUserService defNodeUserService;

    /**
     * 获取工作流_设计_节点人员列表
     *
     * @param queryDTO
     * @param response
     * @return
     */
    @GetMapping
    @ApiOperation(value = "获取工作流_设计_节点人员分页列表")
    public List<DefNodeUserDTO> getDefNodeUsers(
        @ModelAttribute DefNodeUserQueryDTO queryDTO,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        IPage iPage = this.getPagination();
        IPage<DefNodeUserEntity> pm = defNodeUserService.getDefNodeUsers(iPage, queryDTO, currentUser);

        List<DefNodeUserDTO> dtos = defNodeUserService.mapperToDTO(pm.getRecords(), currentUser);
        RestUtils.initResponseByPage(pm, response);
        return dtos;
    }

    /**
     * 获取单个工作流_设计_节点人员
     *
     * @param id
     * @param response
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个工作流_设计_节点人员")
    public DefNodeUserDTO getDefNodeUser(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        DefNodeUserEntity defNodeUserEntity = defNodeUserService.getDefNodeUser(id, currentUser);

        return defNodeUserService.mapperToDTO(defNodeUserEntity, currentUser);
    }

    /**
     * 创建单个工作流_设计_节点人员
     *
     * @param defNodeUserFormDTO
     * @param response
     * @return
     */
    @PostMapping
    @ApiOperation(value = "创建单个工作流_设计_节点人员")
    public ResponseEntity createDefNodeUser(
        @Validated @RequestBody DefNodeUserFormDTO defNodeUserFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeUserService.createDefNodeUser(defNodeUserFormDTO, currentUser);
        return ResponseUtils.ok("新增成功").build();
    }

    /**
     * 修改单个工作流_设计_节点人员
     *
     * @param id
     * @param defNodeUserFormDTO
     * @param response
     * @return
     */
    @PatchMapping("/{id}")
    @ApiOperation(value = "修改单个工作流_设计_节点人员")
    public ResponseEntity updateDefNodeUser(
        @PathVariable(value = "id") Long id,
        @Validated @RequestBody DefNodeUserFormDTO defNodeUserFormDTO,
        BindingResult result,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();
        RestUtils.validation(result);

        defNodeUserService.updateDefNodeUser(id, defNodeUserFormDTO, currentUser);
        return ResponseUtils.ok("修改成功").build();
    }

    /**
     * 删除单个工作流_设计_节点人员
     *
     * @param id
     * @param response
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个工作流_设计_节点人员")
    public ResponseEntity deleteDefNodeUser(
        @PathVariable(value = "id") Long id,
        HttpServletResponse response
    ) {
        EmployeeIdentity currentUser = context.getEmployee();

        defNodeUserService.deleteDefNodeUser(id, currentUser);
        return ResponseUtils.ok("删除成功").build();
    }
}

