package com.qy.wf.defNodeForm.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.defNodeForm.dto.DefNodeFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormFormDTO;
import com.qy.wf.defNodeForm.dto.DefNodeFormQueryDTO;
import com.qy.wf.defNodeForm.entity.DefNodeFormEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_表单 服务类
 *
 * @author syf
 * @since 2022-11-14
 */
public interface DefNodeFormService extends IService<DefNodeFormEntity> {

    /**
     * 获取工作流_设计_表单分页列表
     *
     * @param iPage 分页
     * @param defNodeFormQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_表单列表
     */
    IPage<DefNodeFormEntity> getDefNodeForms(IPage iPage, DefNodeFormQueryDTO defNodeFormQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_表单列表
     *
     * @param defNodeFormQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_表单列表
     */
    List<DefNodeFormEntity> getDefNodeForms(DefNodeFormQueryDTO defNodeFormQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_表单
     */
    DefNodeFormEntity getDefNodeForm(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_表单
     *
     * @param defNodeFormFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_表单
     */
    DefNodeFormEntity createDefNodeForm(DefNodeFormFormDTO defNodeFormFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_表单
     *
     * @param defNodeFormFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_表单
     */
    DefNodeFormEntity updateDefNodeForm(Long id, DefNodeFormFormDTO defNodeFormFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_表单
     */
    DefNodeFormEntity deleteDefNodeForm(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeFormEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeFormDTO mapperToDTO(DefNodeFormEntity defNodeFormEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeFormEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeFormDTO> mapperToDTO(List<DefNodeFormEntity> defNodeFormEntityList, EmployeeIdentity currentUser);
}
