package com.qy.system.app.version.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.security.session.MemberIdentity;
import com.qy.system.app.version.dto.AppVersionDTO;
import com.qy.system.app.version.dto.AppVersionFormDTO;
import com.qy.system.app.version.dto.AppVersionQueryDTO;
import com.qy.system.app.version.entity.AppVersionEntity;

import java.util.List;


/**
 * APP版本 服务类
 *
 * @author syf
 * @since 2024-05-21
 */
public interface AppVersionService extends IService<AppVersionEntity> {

    /**
     * 获取APP版本分页列表
     *
     * @param iPage 分页
     * @param appVersionQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return APP版本列表
     */
    IPage<AppVersionDTO> getAppVersions(IPage iPage, AppVersionQueryDTO appVersionQueryDTO, MemberIdentity currentUser);

    /**
     * 获取APP版本列表
     *
     * @param appVersionQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return APP版本列表
     */
    List<AppVersionEntity> getAppVersions(AppVersionQueryDTO appVersionQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个APP版本
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个APP版本
     */
    AppVersionEntity getAppVersion(Long id, MemberIdentity currentUser);



    /**
    * 获取单个APP版本
    *
    * @param id 主键id
    * @param currentUser 当前登录用户
    * @return 单个APP版本
    */
    AppVersionDTO getAppVersionDTO(Long id, MemberIdentity currentUser);

    /**
     * 创建单个APP版本
     *
     * @param appVersionFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的APP版本
     */
    AppVersionEntity createAppVersion(AppVersionFormDTO appVersionFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个APP版本
     *
     * @param appVersionFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的APP版本
     */
    AppVersionEntity updateAppVersion(Long id, AppVersionFormDTO appVersionFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个APP版本
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的APP版本
     */
    AppVersionEntity deleteAppVersion(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param appVersionEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    AppVersionDTO mapperToDTO(AppVersionEntity appVersionEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param appVersionEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<AppVersionDTO> mapperToDTO(List<AppVersionEntity> appVersionEntityList, MemberIdentity currentUser);

    /**
     * 获取APP最新版本列表
     *
     * @param type
     * @param itemType
     * @return
     */
    AppVersionDTO getNewVersion(String type, String itemType);
}
