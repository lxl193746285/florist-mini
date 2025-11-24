package com.qy.system.app.useruniquecode.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.useruniquecode.dto.*;
import com.qy.system.app.useruniquecode.entity.UserUniqueCodeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;


/**
 * 用户设备唯一码 服务类
 *
 * @author wwd
 * @since 2024-04-19
 */
public interface UserUniqueCodeService extends IService<UserUniqueCodeEntity> {

    /**
     * 获取用户设备唯一码分页列表
     *
     * @param iPage 分页
     * @param userUniqueCodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 用户设备唯一码列表
     */
    IPage<UserUniqueCodeDTO> getUserUniqueCodes(IPage iPage, UserUniqueCodeQueryDTO userUniqueCodeQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取用户设备唯一码列表
     *
     * @param userUniqueCodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 用户设备唯一码列表
     */
    List<UserUniqueCodeEntity> getUserUniqueCodes(UserUniqueCodeQueryDTO userUniqueCodeQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个用户设备唯一码
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个用户设备唯一码
     */
    UserUniqueCodeEntity getUserUniqueCode(Long id, EmployeeIdentity currentUser);



    /**
    * 获取单个用户设备唯一码
    *
    * @param id 主键id
    * @param currentUser 当前登录用户
    * @return 单个用户设备唯一码
    */
    UserUniqueCodeDTO getUserUniqueCodeDTO(Long id, EmployeeIdentity currentUser);


    /**
     * 创建单个用户设备唯一码
     *
     * @param userUniqueCodeFormDTO 表单对象
     * @return 新创建的用户设备唯一码
     */
    Boolean createUserUniqueCode(UserUniqueCodeFormDTO userUniqueCodeFormDTO);

    /**
     * 更新单个用户设备唯一码
     *
     * @param userUniqueCodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的用户设备唯一码
     */
    UserUniqueCodeEntity updateUserUniqueCode(Long id, UserUniqueCodeFormDTO userUniqueCodeFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个用户设备唯一码
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的用户设备唯一码
     */
    UserUniqueCodeEntity deleteUserUniqueCode(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param userUniqueCodeEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    UserUniqueCodeDTO mapperToDTO(UserUniqueCodeEntity userUniqueCodeEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param userUniqueCodeEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<UserUniqueCodeDTO> mapperToDTO(List<UserUniqueCodeEntity> userUniqueCodeEntityList, EmployeeIdentity currentUser);

    UserUniqueCodeBasicDTO getUserUniqueCodeDTO(Long memberId, Long orgId);
}
