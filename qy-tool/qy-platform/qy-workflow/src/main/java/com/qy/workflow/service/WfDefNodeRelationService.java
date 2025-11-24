package com.qy.workflow.service;

import com.qy.security.session.MemberIdentity;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfDefNodeRelationEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点关系 服务类
 *
 * @author iFeng
 * @since 2022-11-15
 */
public interface WfDefNodeRelationService extends IService<WfDefNodeRelationEntity> {

    /**
     * 获取工作流_设计_节点关系分页列表
     *
     * @param iPage 分页
     * @param wfDefNodeRelationQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点关系列表
     */
    IPage<WfDefNodeRelationEntity> getWfDefNodeRelations(IPage iPage, WfDefNodeRelationQueryDTO wfDefNodeRelationQueryDTO, MemberIdentity currentUser);


    /**
     * 获取工作流_设计_节点关系列表
     *
     * @param wfDefNodeRelationQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点关系列表
     */
    List<WfDefNodeRelationEntity> getWfDefNodeRelations(WfDefNodeRelationQueryDTO wfDefNodeRelationQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点关系
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点关系
     */

    WfDefNodeRelationEntity getWfDefNodeRelation(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点关系
     *
     * @param wfDefNodeRelationFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点关系
     */

    WfDefNodeRelationEntity createWfDefNodeRelation(WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点关系
     *
     * @param wfDefNodeRelationFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点关系
     */

    WfDefNodeRelationEntity updateWfDefNodeRelation(Long id, WfDefNodeRelationFormDTO wfDefNodeRelationFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点关系
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点关系
     */

    WfDefNodeRelationEntity deleteWfDefNodeRelation(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param wfDefNodeRelationEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */

    WfDefNodeRelationDTO mapperToDTO(WfDefNodeRelationEntity wfDefNodeRelationEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param wfDefNodeRelationEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */

    List<WfDefNodeRelationDTO> mapperToDTO(List<WfDefNodeRelationEntity> wfDefNodeRelationEntityList, MemberIdentity currentUser);

    List<WfDefNodeRelationEntity> getNodeRelations(Long wfId, Long nodeId);

}
