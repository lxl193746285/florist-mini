package com.qy.wf.defNodeRelation.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationFormDTO;
import com.qy.wf.defNodeRelation.dto.DefNodeRelationQueryDTO;
import com.qy.wf.defNodeRelation.entity.DefNodeRelationEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点关系 服务类
 *
 * @author syf
 * @since 2022-11-14
 */
public interface DefNodeRelationService extends IService<DefNodeRelationEntity> {

    /**
     * 获取工作流_设计_节点关系分页列表
     *
     * @param iPage 分页
     * @param defNodeRelationQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点关系列表
     */
    IPage<DefNodeRelationEntity> getDefNodeRelations(IPage iPage, DefNodeRelationQueryDTO defNodeRelationQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_节点关系列表
     *
     * @param defNodeRelationQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点关系列表
     */
    List<DefNodeRelationEntity> getDefNodeRelations(DefNodeRelationQueryDTO defNodeRelationQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点关系
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点关系
     */
    DefNodeRelationEntity getDefNodeRelation(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点关系
     *
     * @param defNodeRelationFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点关系
     */
    DefNodeRelationEntity createDefNodeRelation(DefNodeRelationFormDTO defNodeRelationFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点关系
     *
     * @param defNodeRelationFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点关系
     */
    DefNodeRelationEntity updateDefNodeRelation(Long id, DefNodeRelationFormDTO defNodeRelationFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点关系
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点关系
     */
    DefNodeRelationEntity deleteDefNodeRelation(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeRelationEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeRelationDTO mapperToDTO(DefNodeRelationEntity defNodeRelationEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeRelationEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeRelationDTO> mapperToDTO(List<DefNodeRelationEntity> defNodeRelationEntityList, EmployeeIdentity currentUser);

    void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, EmployeeIdentity currentUser);

}
