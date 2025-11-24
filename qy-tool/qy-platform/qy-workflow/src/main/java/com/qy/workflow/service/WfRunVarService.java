package com.qy.workflow.service;

import com.qy.security.session.MemberIdentity;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunVarEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 工作流_执行_节点 服务类
 *
 * @author iFeng
 * @since 2022-11-23
 */
public interface WfRunVarService extends IService<WfRunVarEntity> {

    /**
     * 获取工作流_执行_节点分页列表
     *
     * @param iPage 分页
     * @param wfRunVarQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    IPage<WfRunVarEntity> getWfRunVars(IPage iPage, WfRunVarQueryDTO wfRunVarQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_执行_节点列表
     *
     * @param wfRunVarQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    List<WfRunVarEntity> getWfRunVars(WfRunVarQueryDTO wfRunVarQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_执行_节点
     */
    WfRunVarEntity getWfRunVar(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_执行_节点
     *
     * @param wfRunVarFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_执行_节点
     */
    WfRunVarEntity createWfRunVar(WfRunVarFormDTO wfRunVarFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_执行_节点
     *
     * @param wfRunVarFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_执行_节点
     */
    WfRunVarEntity updateWfRunVar(Long id, WfRunVarFormDTO wfRunVarFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_执行_节点
     */
    WfRunVarEntity deleteWfRunVar(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param wfRunVarEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    WfRunVarDTO mapperToDTO(WfRunVarEntity wfRunVarEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param wfRunVarEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<WfRunVarDTO> mapperToDTO(List<WfRunVarEntity> wfRunVarEntityList, MemberIdentity currentUser);
}
