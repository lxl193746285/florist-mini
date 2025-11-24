package com.qy.system.app.config.service;

import com.qy.system.app.config.dto.*;
import com.qy.system.app.config.entity.SystemConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import com.qy.security.session.MemberIdentity;


/**
 * 配置 服务类
 *
 * @author hh
 * @since 2024-07-09
 */
public interface SystemConfigService extends IService<SystemConfigEntity> {

    /**
     * 获取配置分页列表
     *
     * @param iPage 分页
     * @param systemConfigQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 配置列表
     */
    IPage<SystemConfigDTO> getSystemConfigs(IPage iPage, SystemConfigQueryDTO systemConfigQueryDTO, MemberIdentity currentUser);

    /**
     * 获取配置列表
     *
     * @param systemConfigQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 配置列表
     */
    List<SystemConfigEntity> getSystemConfigs(SystemConfigQueryDTO systemConfigQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个配置
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个配置
     */
    SystemConfigEntity getSystemConfig(Long id, MemberIdentity currentUser);



    /**
    * 获取单个配置
    *
    * @param id 主键id
    * @param currentUser 当前登录用户
    * @return 单个配置
    */
    SystemConfigDTO getSystemConfigDTO(Long id, MemberIdentity currentUser);


    /**
     * 创建单个配置
     *
     * @param systemConfigFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的配置
     */
    SystemConfigEntity createSystemConfig(SystemConfigFormDTO systemConfigFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个配置
     *
     * @param systemConfigFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的配置
     */
    SystemConfigEntity updateSystemConfig(Long id, SystemConfigFormDTO systemConfigFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个配置
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的配置
     */
    SystemConfigEntity deleteSystemConfig(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param systemConfigEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    SystemConfigDTO mapperToDTO(SystemConfigEntity systemConfigEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param systemConfigEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<SystemConfigDTO> mapperToDTO(List<SystemConfigEntity> systemConfigEntityList, MemberIdentity currentUser);

    /**
     * 根据属性查找数据
     *
     * @param attribute 属性值
     * @param currentUser 当前登录用户
     * @return
     */
    SystemConfigSearchDTO getSystemConfigSearchDTO(String attribute,String categoryIdentifier,MemberIdentity currentUser);
}
