package com.qy.system.app.autonumber.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.autonumber.dto.SnSetsDTO;
import com.qy.system.app.autonumber.dto.SnSetsFormDTO;
import com.qy.system.app.autonumber.dto.SnSetsQueryDTO;
import com.qy.system.app.autonumber.entity.SnSetsEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SnSetsService extends IService<SnSetsEntity> {
    /**
     * 获取编号设置分页列表
     *
     * @param iPage 分页
     * @param snSetsQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 编号设置列表
     */
    IPage<SnSetsEntity> getSnSets(IPage iPage, SnSetsQueryDTO snSetsQueryDTO, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param snSetsEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<SnSetsDTO> mapperToDTO(List<SnSetsEntity> snSetsEntityList, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param snsetsEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    SnSetsDTO mapperToDTO(SnSetsEntity snsetsEntity, EmployeeIdentity currentUser);

    /**
     * 获取单个编号设置规则
     *
     * @param noid 编号
     * @return 单个编号设置规则
     */
    SnSetsEntity getSnSet(String noid, EmployeeIdentity currentUser);

    /**
     * 创建单个编号规则
     *
     * @param snSetsFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的编号规则
     */
    SnSetsEntity createSnSet(SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser);

    /**
     * 获取编号规则设置列表
     *
     * @param snSetsQueryDTO 查询条件
     * @return 编号规则列表
     */
    List<SnSetsEntity> getSnSets(SnSetsQueryDTO snSetsQueryDTO);

    /**
     * 更新单个编号规则设置
     *
     * @param snSetsFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的编号规则
     */
    SnSetsEntity updateSnSet(SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个编号设置启用禁用状态
     *
     * @param snSetsFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的编号设置
     */
    SnSetsEntity updateSnSetsStatus(String noid, SnSetsFormDTO snSetsFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个编号规则设置
     *
     * @param noid 编号
     * @param currentUser 当前登录用户
     * @return 删除的编号规则设置
     */
    SnSetsEntity deleteSnSet(String noid, EmployeeIdentity currentUser);

    /**
     * 获取自动编号
     * @param noId
     * @param companyId
     * @param userId
     * @return
     */
    String getAutoNumber(String noId, Long companyId, Long userId);
}
