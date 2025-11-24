package com.qy.workflow.service;

import com.qy.security.session.MemberIdentity;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunNodeEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 工作流_执行_节点 服务类
 *
 * @author iFeng
 * @since 2022-11-16
 */
public interface WfRunNodeService extends IService<WfRunNodeEntity> {

    /**
     * 获取工作流_执行_节点分页列表
     *
     * @param iPage 分页
     * @param wfRunNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    IPage<WfRunNodeEntity> getWfRunNodes(IPage iPage, WfRunNodeQueryDTO wfRunNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_执行_节点列表
     *
     * @param wfRunNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    List<WfRunNodeEntity> getWfRunNodes(WfRunNodeQueryDTO wfRunNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_执行_节点
     */
    WfRunNodeEntity getWfRunNode(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_执行_节点
     *
     * @param wfRunNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_执行_节点
     */
    WfRunNodeEntity createWfRunNode(WfRunNodeFormDTO wfRunNodeFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_执行_节点
     *
     * @param wfRunNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_执行_节点
     */
    WfRunNodeEntity updateWfRunNode(Long id, WfRunNodeFormDTO wfRunNodeFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_执行_节点
     */
    WfRunNodeEntity deleteWfRunNode(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param wfRunNodeEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    WfRunNodeDTO mapperToDTO(WfRunNodeEntity wfRunNodeEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param wfRunNodeEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<WfRunNodeDTO> mapperToDTO(List<WfRunNodeEntity> wfRunNodeEntityList, MemberIdentity currentUser);
}
