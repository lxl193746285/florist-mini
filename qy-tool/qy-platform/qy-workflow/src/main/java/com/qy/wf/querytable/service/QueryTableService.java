package com.qy.wf.querytable.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.wf.querytable.dto.*;
import com.qy.wf.querytable.entity.QueryTableEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_查询表单 服务类
 *
 * @author hh
 * @since 2022-11-19
 */
public interface QueryTableService extends IService<QueryTableEntity> {

    /**
     * 获取工作流_设计_查询表单分页列表
     *
     * @param iPage 分页
     * @param queryTableQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_查询表单列表
     */
    IPage<QueryTableEntity> getQueryTables(IPage iPage, QueryTableQueryDTO queryTableQueryDTO, EmployeeIdentity currentUser);


    /**
     * 获取工作流表单的数据处理 getQueryTables
     * @param iPage
     * @param type
     * @param queryTableQueryDTO
     * @param currentUser
     * @return
     */
    IPage<QueryTableEntity> getQueryCustomTable(IPage iPage,Integer type, QueryTableQueryDTO queryTableQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_查询表单列表
     *
     * @param queryTableQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_查询表单列表
     */
    List<QueryTableEntity> getQueryTables(QueryTableQueryDTO queryTableQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取单个工作流_设计_查询表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_查询表单
     */
    QueryTableEntity getQueryTable(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_查询表单
     *
     * @param queryTableFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_查询表单
     */
    QueryTableEntity createQueryTable(QueryTableFormDTO queryTableFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_查询表单
     *
     * @param queryTableFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_查询表单
     */
    QueryTableEntity updateQueryTable(Long id, QueryTableFormDTO queryTableFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_查询表单
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_查询表单
     */
    QueryTableEntity deleteQueryTable(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param queryTableEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    QueryTableDTO mapperToDTO(QueryTableEntity queryTableEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param queryTableEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<QueryTableDTO> mapperToDTO(List<QueryTableEntity> queryTableEntityList, EmployeeIdentity currentUser);
}
