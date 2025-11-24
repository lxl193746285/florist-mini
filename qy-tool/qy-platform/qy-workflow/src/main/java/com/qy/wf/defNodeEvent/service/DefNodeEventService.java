package com.qy.wf.defNodeEvent.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeEvent.dto.DefNodeEventDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventFormDTO;
import com.qy.wf.defNodeEvent.dto.DefNodeEventQueryDTO;
import com.qy.wf.defNodeEvent.entity.DefNodeEventEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点事件 服务类
 *
 * @author syf
 * @since 2022-11-21
 */
public interface DefNodeEventService extends IService<DefNodeEventEntity> {

    /**
     * 获取工作流_设计_节点事件分页列表
     *
     * @param iPage 分页
     * @param defNodeEventQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点事件列表
     */
    IPage<DefNodeEventEntity> getDefNodeEvents(IPage iPage, DefNodeEventQueryDTO defNodeEventQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_节点事件列表
     *
     * @param defNodeEventQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点事件列表
     */
    List<DefNodeEventEntity> getDefNodeEvents(DefNodeEventQueryDTO defNodeEventQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点事件
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点事件
     */
    DefNodeEventEntity getDefNodeEvent(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点事件
     *
     * @param defNodeEventFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点事件
     */
    DefNodeEventEntity createDefNodeEvent(DefNodeEventFormDTO defNodeEventFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点事件
     *
     * @param defNodeEventFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点事件
     */
    DefNodeEventEntity updateDefNodeEvent(Long id, DefNodeEventFormDTO defNodeEventFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点事件
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点事件
     */
    DefNodeEventEntity deleteDefNodeEvent(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeEventEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeEventDTO mapperToDTO(DefNodeEventEntity defNodeEventEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeEventEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeEventDTO> mapperToDTO(List<DefNodeEventEntity> defNodeEventEntityList, EmployeeIdentity currentUser);

    void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser);
}
