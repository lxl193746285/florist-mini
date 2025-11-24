package com.qy.wf.defMap.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defMap.dto.DefMapDTO;
import com.qy.wf.defMap.dto.DefMapFormDTO;
import com.qy.wf.defMap.dto.DefMapQueryDTO;
import com.qy.wf.defMap.entity.DefMapEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_映射表 记录业务表与工作流id关联 服务类
 *
 * @author syf
 * @since 2022-11-21
 */
public interface DefMapService extends IService<DefMapEntity> {

    /**
     * 获取工作流_设计_映射表 记录业务表与工作流id关联分页列表
     *
     * @param iPage 分页
     * @param defMapQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_映射表 记录业务表与工作流id关联列表
     */
    IPage<DefMapEntity> getDefMaps(IPage iPage, DefMapQueryDTO defMapQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_映射表 记录业务表与工作流id关联列表
     *
     * @param defMapQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_映射表 记录业务表与工作流id关联列表
     */
    List<DefMapEntity> getDefMaps(DefMapQueryDTO defMapQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_映射表 记录业务表与工作流id关联
     */
    DefMapEntity getDefMap(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param defMapFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_映射表 记录业务表与工作流id关联
     */
    DefMapEntity createDefMap(DefMapFormDTO defMapFormDTO, EmployeeIdentity currentUser);

    DefMapEntity getDefMapByWfId(Long wfId, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param defMapFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_映射表 记录业务表与工作流id关联
     */
    DefMapEntity updateDefMap(Long id, DefMapFormDTO defMapFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_映射表 记录业务表与工作流id关联
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_映射表 记录业务表与工作流id关联
     */
    DefMapEntity deleteDefMap(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defMapEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefMapDTO mapperToDTO(DefMapEntity defMapEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defMapEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefMapDTO> mapperToDTO(List<DefMapEntity> defMapEntityList, EmployeeIdentity currentUser);


}
