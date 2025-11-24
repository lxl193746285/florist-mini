package com.qy.system.app.loginlog.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.system.app.loginlog.dto.*;
import com.qy.system.app.loginlog.entity.LoginLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;


/**
 * 系统登录日志 服务类
 *
 * @author wwd
 * @since 2024-04-18
 */
public interface LoginLogService extends IService<LoginLogEntity> {

    /**
     * 获取系统登录日志分页列表
     *
     * @param iPage 分页
     * @param loginLogQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 系统登录日志列表
     */
    IPage<LoginLogDTO> getLoginLogs(IPage iPage, LoginLogQueryDTO loginLogQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取系统登录日志列表
     *
     * @param loginLogQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 系统登录日志列表
     */
    List<LoginLogEntity> getLoginLogs(LoginLogQueryDTO loginLogQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个系统登录日志
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个系统登录日志
     */
    LoginLogEntity getLoginLog(Long id, EmployeeIdentity currentUser);



    /**
    * 获取单个系统登录日志
    *
    * @param id 主键id
    * @param currentUser 当前登录用户
    * @return 单个系统登录日志
    */
    LoginLogDTO getLoginLogDTO(Long id, EmployeeIdentity currentUser);


    /**
     * 创建单个系统登录日志
     *
     * @param loginLogFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的系统登录日志
     */
    LoginLogEntity createLoginLog(LoginLogFormDTO loginLogFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个系统登录日志
     *
     * @param loginLogFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的系统登录日志
     */
    LoginLogEntity updateLoginLog(Long id, LoginLogFormDTO loginLogFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个系统登录日志
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的系统登录日志
     */
    LoginLogEntity deleteLoginLog(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param loginLogEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    LoginLogDTO mapperToDTO(LoginLogEntity loginLogEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param loginLogEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<LoginLogDTO> mapperToDTO(List<LoginLogEntity> loginLogEntityList, EmployeeIdentity currentUser);

    LoginLogDTO getLastLog(MemberIdentity member);
}
