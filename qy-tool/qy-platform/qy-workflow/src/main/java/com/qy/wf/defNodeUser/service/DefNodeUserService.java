package com.qy.wf.defNodeUser.service;

import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.MemberIdentity;
import com.qy.wf.defNode.dto.DefNodeBacthDTO;
import com.qy.wf.defNode.entity.DefNodeEntity;
import com.qy.wf.defNodeUser.dto.DefNodeUserDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserFormDTO;
import com.qy.wf.defNodeUser.dto.DefNodeUserQueryDTO;
import com.qy.wf.defNodeUser.entity.DefNodeUserEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 工作流_设计_节点人员 服务类
 *
 * @author syf
 * @since 2022-11-14
 */
public interface DefNodeUserService extends IService<DefNodeUserEntity> {

    /**
     * 获取工作流_设计_节点人员分页列表
     *
     * @param iPage 分页
     * @param defNodeUserQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点人员列表
     */
    IPage<DefNodeUserEntity> getDefNodeUsers(IPage iPage, DefNodeUserQueryDTO defNodeUserQueryDTO, EmployeeIdentity currentUser);

    /**
     * 获取工作流_设计_节点人员列表
     *
     * @param defNodeUserQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点人员列表
     */
    List<DefNodeUserEntity> getDefNodeUsers(DefNodeUserQueryDTO defNodeUserQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点人员
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点人员
     */
    DefNodeUserEntity getDefNodeUser(Long id, EmployeeIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点人员
     *
     * @param defNodeUserFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点人员
     */
    DefNodeUserEntity createDefNodeUser(DefNodeUserFormDTO defNodeUserFormDTO, EmployeeIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点人员
     *
     * @param defNodeUserFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点人员
     */
    DefNodeUserEntity updateDefNodeUser(Long id, DefNodeUserFormDTO defNodeUserFormDTO, EmployeeIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点人员
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点人员
     */
    DefNodeUserEntity deleteDefNodeUser(Long id, EmployeeIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param defNodeUserEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    DefNodeUserDTO mapperToDTO(DefNodeUserEntity defNodeUserEntity, EmployeeIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param defNodeUserEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<DefNodeUserDTO> mapperToDTO(List<DefNodeUserEntity> defNodeUserEntityList, EmployeeIdentity currentUser);

    void batchSave(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, EmployeeIdentity currentUser);

    void batchSaveV2(DefNodeBacthDTO defNodeBacthDTO, List<DefNodeEntity> list, List<DefNodeEntity> needDefNodeAddEntityList, List<DefNodeEntity> needDefNodeUpdateEntityList, List<String> needDeleteNodeList, EmployeeIdentity currentUser);
}
