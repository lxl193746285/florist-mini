package com.qy.system.app.config.service;

import com.qy.system.app.config.dto.*;
import com.qy.system.app.config.entity.SystemConfigCategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import com.qy.security.session.MemberIdentity;


/**
 * 配置类别 服务类
 *
 * @author hh
 * @since 2024-07-09
 */
public interface SystemConfigCategoryService extends IService<SystemConfigCategoryEntity> {

    /**
     * 获取配置类别分页列表
     *
     * @param iPage 分页
     * @param systemConfigCategoryQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 配置类别列表
     */
    IPage<SystemConfigCategoryDTO> getSystemConfigCategorys(IPage iPage, SystemConfigCategoryQueryDTO systemConfigCategoryQueryDTO, MemberIdentity currentUser);

    /**
     * 获取配置类别列表
     *
     * @param systemConfigCategoryQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 配置类别列表
     */
    List<SystemConfigCategoryEntity> getSystemConfigCategorys(SystemConfigCategoryQueryDTO systemConfigCategoryQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个配置类别
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个配置类别
     */
    SystemConfigCategoryEntity getSystemConfigCategory(Long id, MemberIdentity currentUser);



    /**
    * 获取单个配置类别
    *
    * @param id 主键id
    * @param currentUser 当前登录用户
    * @return 单个配置类别
    */
    SystemConfigCategoryDTO getSystemConfigCategoryDTO(Long id, MemberIdentity currentUser);


    /**
     * 创建单个配置类别
     *
     * @param systemConfigCategoryFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的配置类别
     */
    SystemConfigCategoryEntity createSystemConfigCategory(SystemConfigCategoryFormDTO systemConfigCategoryFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个配置类别
     *
     * @param systemConfigCategoryFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的配置类别
     */
    SystemConfigCategoryEntity updateSystemConfigCategory(Long id, SystemConfigCategoryFormDTO systemConfigCategoryFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个配置类别
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的配置类别
     */
    SystemConfigCategoryEntity deleteSystemConfigCategory(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param systemConfigCategoryEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    SystemConfigCategoryDTO mapperToDTO(SystemConfigCategoryEntity systemConfigCategoryEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param systemConfigCategoryEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<SystemConfigCategoryDTO> mapperToDTO(List<SystemConfigCategoryEntity> systemConfigCategoryEntityList, MemberIdentity currentUser);
}
