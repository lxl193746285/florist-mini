package com.qy.wf.var.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.var.dto.VarDTO;
import com.qy.wf.var.dto.VarFormDTO;
import com.qy.wf.var.dto.VarQueryDTO;
import com.qy.wf.var.entity.VarEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_变量 服务类
 *
 * @author syf
 * @since 2022-11-21
 */
public interface VarService extends IService<VarEntity> {

    /**
     * 获取工作流_变量分页列表
     *
     * @param iPage 分页
     * @param varQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_变量列表
     */
    IPage<VarEntity> getVars(IPage iPage, VarQueryDTO varQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_变量列表
     *
     * @param varQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_变量列表
     */
    List<VarEntity> getVars(VarQueryDTO varQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_变量
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_变量
     */
    VarEntity getVar(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_变量
     *
     * @param varFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_变量
     */
    VarEntity createVar(VarFormDTO varFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_变量
     *
     * @param varFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_变量
     */
    VarEntity updateVar(Long id, VarFormDTO varFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_变量
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_变量
     */
    VarEntity deleteVar(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param varEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    VarDTO mapperToDTO(VarEntity varEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param varEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<VarDTO> mapperToDTO(List<VarEntity> varEntityList, EmployeeIdentity currentUser);
}
