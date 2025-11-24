package com.qy.wf.runNode.service;

import com.qy.security.session.MemberIdentity;
import com.qy.wf.runNode.dto.*;
import com.qy.wf.runNode.entity.RunNodeEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_执行_节点 服务类
 *
 * @author wch
 * @since 2022-11-17
 */
public interface RunNodeService extends IService<RunNodeEntity> {

    /**
     * 获取工作流_执行_节点分页列表
     *
     * @param iPage 分页
     * @param runNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    IPage<RunNodeEntity> getRunNodes(IPage iPage, RunNodeQueryDTO runNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_执行_节点列表
     *
     * @param runNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_节点列表
     */
    List<RunNodeEntity> getRunNodes(RunNodeQueryDTO runNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_执行_节点
     */
    RunNodeEntity getRunNode(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_执行_节点
     *
     * @param runNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_执行_节点
     */
    RunNodeEntity createRunNode(RunNodeFormDTO runNodeFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_执行_节点
     *
     * @param runNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_执行_节点
     */
    RunNodeEntity updateRunNode(Long id, RunNodeFormDTO runNodeFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_执行_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_执行_节点
     */
    RunNodeEntity deleteRunNode(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param runNodeEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    RunNodeDTO mapperToDTO(RunNodeEntity runNodeEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param runNodeEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<RunNodeDTO> mapperToDTO(List<RunNodeEntity> runNodeEntityList, MemberIdentity currentUser);

    List<RunNodeDTO> getListById(Long wfId);

    /**
     * 工作流管理详情
     * @param wfId
     * @return
     */
    List<RunNodeDetail> getDetailListById(Long wfId, MemberIdentity currentUser);

    Integer getNextNodeStatus(Long curNodeId, Long curNodeUserId, Long id);

    List<RunNodeEntity>  getRunNodeId(Long id, Long curNodeId, Long curNodeUserId);

    List<RunNodeEntity> getAllNode();

    /**
     * 查询当前工作流最后一条子记录
     * @param id
     * @return
     */
    RunNodeEntity getRunNodeByWfRunId(Long id);

    /**
     * 更换工作流_执行_节点的办理人
     *
     * @param approverFormDTO
     */
    void replaceRunNodeApprover(ReplaceApproverFormDTO approverFormDTO, MemberIdentity currentUser);
}
