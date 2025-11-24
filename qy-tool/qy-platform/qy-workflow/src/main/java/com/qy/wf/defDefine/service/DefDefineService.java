package com.qy.wf.defDefine.service;

import com.qy.security.session.MemberIdentity;
import com.qy.wf.defDefine.dto.*;
import com.qy.wf.defDefine.entity.DefDefineEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_定义	 服务类
 *
 * @author wch
 * @since 2022-11-12
 */
public interface DefDefineService extends IService<DefDefineEntity> {

    /**
     * 获取工作流_设计_定义	分页列表
     *
     * @param iPage 分页
     * @param defDefineQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_定义	
     */
    IPage<DefDefineEntity> getDefDefines(IPage iPage, DefDefineQueryDTO defDefineQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_设计_定义	
     *
     * @param defDefineQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_定义	
     */
    List<DefDefineEntity> getDefDefines(DefDefineQueryDTO defDefineQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_设计_定义	
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_定义	
     */
    DefDefineEntity getDefDefine(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_设计_定义	
     *
     * @param defDefineFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_定义	
     */
    DefDefineEntity createDefDefine(DefDefineFormDTO defDefineFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_设计_定义	
     *
     * @param defDefineFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_定义	
     */
    DefDefineEntity updateDefDefine(Long id, DefDefineFormDTO defDefineFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_设计_定义
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_定义	
     */
    DefDefineEntity deleteDefDefine(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defDefineEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefDefineDTO mapperToDTO(DefDefineEntity defDefineEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defDefineEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefDefineDTO> mapperToDTO(List<DefDefineEntity> defDefineEntityList, MemberIdentity currentUser);

    void callbackUpdate(Long wfId, DefDefineFormDTO defDefineFormDTO, String wfStr);

    String getWfStr(Long id, MemberIdentity currentUser);

    /**
     * 获取可发起的工作流流程分页
     *
     * @return
     */
    List<DefStartDTO> getCanStartWFDef();

}
