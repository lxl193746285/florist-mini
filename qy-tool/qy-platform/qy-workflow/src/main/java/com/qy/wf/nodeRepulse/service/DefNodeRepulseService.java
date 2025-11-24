package com.qy.wf.nodeRepulse.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseFormDTO;
import com.qy.wf.nodeRepulse.dto.DefNodeRepulseQueryDTO;
import com.qy.wf.nodeRepulse.entity.DefNodeRepulseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_打回节点 服务类
 *
 * @author syf
 * @since 2023-08-16
 */
public interface DefNodeRepulseService extends IService<DefNodeRepulseEntity> {

    /**
     * 获取工作流_设计_打回节点分页列表
     *
     * @param iPage 分页
     * @param defNodeRepulseQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_打回节点列表
     */
    IPage<DefNodeRepulseEntity> getDefNodeRepulses(IPage iPage, DefNodeRepulseQueryDTO defNodeRepulseQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_打回节点列表
     *
     * @param defNodeRepulseQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_打回节点列表
     */
    List<DefNodeRepulseEntity> getDefNodeRepulses(DefNodeRepulseQueryDTO defNodeRepulseQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_打回节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_打回节点
     */
    DefNodeRepulseEntity getDefNodeRepulse(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_打回节点
     *
     * @param defNodeRepulseFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_打回节点
     */
    DefNodeRepulseEntity createDefNodeRepulse(DefNodeRepulseFormDTO defNodeRepulseFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_打回节点
     *
     * @param defNodeRepulseFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_打回节点
     */
    DefNodeRepulseEntity updateDefNodeRepulse(Long id, DefNodeRepulseFormDTO defNodeRepulseFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_打回节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_打回节点
     */
    DefNodeRepulseEntity deleteDefNodeRepulse(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeRepulseEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeRepulseDTO mapperToDTO(DefNodeRepulseEntity defNodeRepulseEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeRepulseEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeRepulseDTO> mapperToDTO(List<DefNodeRepulseEntity> defNodeRepulseEntityList, EmployeeIdentity currentUser);

    void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeRepulseFormDTO> defNodeRepulseFormDTOS, EmployeeIdentity currentUser);
}
