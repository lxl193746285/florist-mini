package com.qy.wf.nodetable.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.nodetable.dto.*;
import com.qy.wf.nodetable.entity.NodeTableEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点表单 服务类
 *
 * @author hh
 * @since 2022-11-19
 */
public interface NodeTableService extends IService<NodeTableEntity> {

    /**
     * 获取工作流_设计_节点表单分页列表
     *
     * @param iPage 分页
     * @param nodeTableQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点表单列表
     */
    IPage<NodeTableEntity> getNodeTables(IPage iPage, NodeTableQueryDTO nodeTableQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_节点表单列表
     *
     * @param nodeTableQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点表单列表
     */
    List<NodeTableEntity> getNodeTables(NodeTableQueryDTO nodeTableQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点表单
     */
    NodeTableEntity getNodeTable(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点表单
     *
     * @param nodeTableFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点表单
     */
    NodeTableEntity createNodeTable(NodeTableFormDTO nodeTableFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点表单
     *
     * @param nodeTableFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点表单
     */
    NodeTableEntity updateNodeTable(Long id, NodeTableFormDTO nodeTableFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点表单
     */
    NodeTableEntity deleteNodeTable(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param nodeTableEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    NodeTableDTO mapperToDTO(NodeTableEntity nodeTableEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param nodeTableEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<NodeTableDTO> mapperToDTO(List<NodeTableEntity> nodeTableEntityList, EmployeeIdentity currentUser);

    void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser);
}
