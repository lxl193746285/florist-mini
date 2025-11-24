package com.qy.wf.defNode.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.dto.DefNodeDTO;
import com.qy.wf.defNode.dto.DefNodeFormNodeDTO;
import com.qy.wf.defNode.dto.DefNodeQueryDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点 服务类
 *
 * @author syf
 * @since 2022-11-14
 */
public interface DefNodeService extends IService<DefNodeEntity> {

    /**
     * 获取工作流_设计_节点分页列表
     *
     * @param iPage 分页
     * @param defNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点列表
     */
    IPage<DefNodeEntity> getDefNodes(IPage iPage, DefNodeQueryDTO defNodeQueryDTO, EmployeeIdentity currentUser);

    IPage<DefNodeEntity> getDefNodesSelect(IPage iPage, DefNodeQueryDTO queryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_节点列表
     *
     * @param defNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点列表
     */
    List<DefNodeEntity> getDefNodes(DefNodeQueryDTO defNodeQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点
     */
    DefNodeEntity getDefNode(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点
     *
     * @param defNodeFormNodeDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点
     */
    DefNodeEntity createDefNode(DefNodeFormNodeDTO defNodeFormNodeDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点
     *
     * @param defNodeFormNodeDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点
     */
    DefNodeEntity updateDefNode(Long id, DefNodeFormNodeDTO defNodeFormNodeDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点
     */
    DefNodeEntity deleteDefNode(Long id, EmployeeIdentity currentUser);

    DefNodeDTO getByNodeCode(String nodeCode, Integer type, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeDTO mapperToDTO(DefNodeEntity defNodeEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeDTO> mapperToDTO(List<DefNodeEntity> defNodeEntityList, EmployeeIdentity currentUser);

    DefNodeEntity getDefNodeById(Long curNodeId, Long wfId);

    void batchCreateDefNode(DefNodeBacthDTO defNodeBacthDTO, EmployeeIdentity currentUser);

    List<DefNodeDTO> getNodesByWfId(Long wfId, EmployeeIdentity currentUser);


}
