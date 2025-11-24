package com.qy.wf.runWf.service;

import com.qy.message.api.dto.MessageLogDTO;
import com.qy.security.permission.action.Action;
import com.qy.security.session.MemberIdentity;
import com.qy.wf.runWf.dto.*;
import com.qy.wf.runWf.entity.RunWfEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;


/**
 * 工作流_执行_工作流 服务类
 *
 * @author wch
 * @since 2022-11-17
 */
public interface RunWfService extends IService<RunWfEntity> {

    /**
     * 获取工作流_执行_工作流分页列表
     *
     * @param iPage 分页
     * @param runWfQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_工作流列表
     */
    IPage<RunWfDTO> getRunWfs(IPage iPage, RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_执行_工作流列表
     *
     * @param runWfQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_执行_工作流列表
     */
    List<RunWfDTO> getRunWfs(RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_执行_工作流
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_执行_工作流
     */
    RunWfDTO getRunWfDTO(Long id, MemberIdentity currentUser);

    RunWfEntity getRunWf(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_执行_工作流
     *
     * @param runWfFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_执行_工作流
     */
    RunWfEntity createRunWf(RunWfFormDTO runWfFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_执行_工作流
     *
     * @param runWfFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_执行_工作流
     */
    RunWfEntity updateRunWf(Long id, RunWfFormDTO runWfFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_执行_工作流
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_执行_工作流
     */
    RunWfEntity deleteRunWf(Long id, MemberIdentity currentUser);
    //删除恢复
    RunWfEntity deleteRunWfRepair(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param runWfDTO 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    RunWfDTO mapperToDTO(RunWfDTO runWfDTO, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param runWfDTOList 数据库映射对象列表
     * @param currentUser 当前登录用户
     *
     */
    List<RunWfDTO> mapperToDTO(List<RunWfDTO> runWfDTOList, MemberIdentity currentUser);

    List<RunWfDTO> getRunWfsTheList(RunWfQueryDTO queryDTO, MemberIdentity currentUser);

    /**
     * 导出
     *
     * @param runWfDTOList 数据库映射对象列表
     * @param response 响应
     * @param currentUser 当前登录用户
     */
    void export(List<RunWfDTO> runWfDTOList, HttpServletResponse response, MemberIdentity currentUser);

    List<RunWfListDTO> getRunWfExportList(RunWfQueryDTO queryDTO, MemberIdentity currentUser);

    void exportList(List<RunWfListDTO> runWfEntities, HttpServletResponse response, MemberIdentity currentUser);

    /**
     * 执行工作流详情--消息推送
     * @param id
     * @param currentUser
     * @return
     */
    List<MessageLogDTO> getMessageSend(Long id, MemberIdentity currentUser);

    /**
     * 获取工作流明细列表
     * @param iPage
     * @param queryDTO
     * @param currentUser
     * @return
     */
    IPage<RunWfListDTO> getRunWfDetailList(IPage iPage, RunWfQueryDTO queryDTO, MemberIdentity currentUser);

    /**
     * 获取执行工作流详情
     * @param id
     * @param currentUser
     * @return
     */
    RunWfDetailDTO getRunWfDetail(Long id, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param runWfDetailDTO 数据库映射对象列表
     * @param currentUser 当前登录用户
     *
     */
    RunWfListDTO mapperDetailToDTO(RunWfDetailDTO runWfDetailDTO, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param runWfListDTOList 数据库映射对象列表
     * @param currentUser 当前登录用户
     *
     */
    List<RunWfListDTO> mapperDetailListToDTO(List<RunWfListDTO> runWfListDTOList, MemberIdentity currentUser);

    /**
     * 获取指定工作流节点的操作按钮
     * @param type 1主表按钮2子表按钮
     * @param runNodeId 工作流执行节点
     * @param currentUser
     * @return
     */
    List<Action> getRunWfActionList(Integer type, Long runNodeId, List<Action> actions, MemberIdentity currentUser);

    List<RWActionDTO> getRunWfActionListByRunNodeIds(Integer type, List<Long> runNodeIds, List<Action> actions, MemberIdentity currentUser);

}
