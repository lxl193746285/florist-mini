package com.qy.workflow.service;

import com.qy.security.session.MemberIdentity;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfRunWfEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_执行_工作流 服务类
 *
 * @author iFeng
 * @since 2022-11-16
 */
public interface WfRunWfService extends IService<WfRunWfEntity> {

    /**
     * 获取工作流_执行_工作流分页列表
     *
     * @param iPage 分页
     * @param wfRunWfQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_工作流列表
     */
    IPage<WfRunWfEntity> getWfRunWfs(IPage iPage, WfRunWfQueryDTO wfRunWfQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_执行_工作流列表
     *
     * @param wfRunWfQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_工作流列表
     */
    List<WfRunWfEntity> getWfRunWfs(WfRunWfQueryDTO wfRunWfQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_执行_工作流
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_执行_工作流
     */
    WfRunWfEntity getWfRunWf(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_执行_工作流
     *
     * @param wfRunWfFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_执行_工作流
     */
    WfRunWfEntity createWfRunWf(WfRunWfFormDTO wfRunWfFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_执行_工作流
     *
     * @param wfRunWfFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_执行_工作流
     */
    WfRunWfEntity updateWfRunWf(Long id, WfRunWfFormDTO wfRunWfFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_执行_工作流
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_执行_工作流
     */
    WfRunWfEntity deleteWfRunWf(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param wfRunWfEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    WfRunWfDTO mapperToDTO(WfRunWfEntity wfRunWfEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param wfRunWfEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<WfRunWfDTO> mapperToDTO(List<WfRunWfEntity> wfRunWfEntityList, MemberIdentity currentUser);
}
