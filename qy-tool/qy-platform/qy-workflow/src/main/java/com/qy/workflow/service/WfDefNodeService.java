package com.qy.workflow.service;

import com.qy.security.session.MemberIdentity;
import com.qy.workflow.dto.*;
import com.qy.workflow.entity.WfDefNodeEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qy.workflow.entity.WfRunWfEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 工作流_设计_节点 服务类
 *
 * @author iFeng
 * @since 2022-11-15
 */
public interface WfDefNodeService extends IService<WfDefNodeEntity> {

    /**
     * 获取工作流_设计_节点分页列表
     *
     * @param iPage 分页
     * @param wfDefNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点列表
     */
    IPage<WfDefNodeEntity> getWfDefNodes(IPage iPage, WfDefNodeQueryDTO wfDefNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取工作流_设计_节点列表
     *
     * @param wfDefNodeQueryDTO 查询条件
     * @param currentUser 当前登录用户
     * @return 工作流_设计_节点列表
     */
    List<WfDefNodeEntity> getWfDefNodes(WfDefNodeQueryDTO wfDefNodeQueryDTO, MemberIdentity currentUser);

    /**
     * 获取单个工作流_设计_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 单个工作流_设计_节点
     */
    WfDefNodeEntity getWfDefNode(Long id, MemberIdentity currentUser);

    /**
     * 创建单个工作流_设计_节点
     *
     * @param wfDefNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 新创建的工作流_设计_节点
     */
    WfDefNodeEntity createWfDefNode(WfDefNodeFormDTO wfDefNodeFormDTO, MemberIdentity currentUser);

    /**
     * 更新单个工作流_设计_节点
     *
     * @param wfDefNodeFormDTO 表单对象
     * @param currentUser 当前登录用户
     * @return 修改后的工作流_设计_节点
     */
    WfDefNodeEntity updateWfDefNode(Long id, WfDefNodeFormDTO wfDefNodeFormDTO, MemberIdentity currentUser);

    /**
     * 删除单个工作流_设计_节点
     *
     * @param id 主键id
     * @param currentUser 当前登录用户
     * @return 删除的工作流_设计_节点
     */
    WfDefNodeEntity deleteWfDefNode(Long id, MemberIdentity currentUser);

    /**
     * 数据库映射对象转换为DTO对象
     *
     * @param wfDefNodeEntity 数据库映射对象
     * @param currentUser 当前登录用户
     * @return
     */
    WfDefNodeDTO mapperToDTO(WfDefNodeEntity wfDefNodeEntity, MemberIdentity currentUser);

    /**
     * 批量数据库映射对象转换为DTO对象
     *
     * @param wfDefNodeEntityList 数据库映射对象列表
     * @param currentUser 当前登录用户
     * @return
     */
    List<WfDefNodeDTO> mapperToDTO(List<WfDefNodeEntity> wfDefNodeEntityList, MemberIdentity currentUser);

    /**
     * 启动工作流
     * @param wfStartDTO
     * @param currentUser
     */
    WfRunWfEntity startWF(WfStartDTO wfStartDTO,HttpRequestDTO data, MemberIdentity currentUser);

    /**
     * 重新发起工作流
     * @param wfStartDTO
     * @param currentUser
     */
    WfRunWfEntity reStartWF(WfStartDTO wfStartDTO,HttpRequestDTO requestDTO, MemberIdentity currentUser) throws Exception;

    /**
     * 办理
     * @param dealDTO
     * @param currentUser
     */
    void dealWF(WfDealDTO dealDTO,HttpRequestDTO requestData,MemberIdentity currentUser);

    void dealWFStartUser(WfDealStartDTO dealDTO,HttpRequestDTO httpRequestDTO,MemberIdentity currentUser);

    /**
     * 获取办理信息返回值
     *
     * @param platform
     * @param type
     * @param funName
     * @param wfRunNodeID
     * @param applyUserId
     * @param currentUser
     * @return
     */
    WfDealNodeInfoDTO getdealinfo(Integer platform,Integer type, String funName, Long wfRunNodeID, Long applyUserId, MemberIdentity currentUser);

    List<WfNodeSimpleDTO> getPreNode(Long wf_id, Long node_id);
    List<WfNodeSimpleDTO> getPreNodeBycode(Long wf_id, String node_code);

    List<WfInvalidRunNodeDTO> getInvalidNode(Long run_wf_id, Long user_id, Long wf_run_node_id);
    /**
     * 获取查询表
     * @param wfRunNodeId
     * @param currentUser
     * @return
     */
    List<WfRunQueryTableDTO> getTables(Long wfWfId, Integer wfIsStartNode, Long wfRunNodeId, Integer platform, Integer showType,Integer dealResult,MemberIdentity currentUser);


    List<WfRunQueryTableDTO> getWFTables(WfGetTableDTO wfGetTableDTO, MemberIdentity currentUser);

    List<WfFilterTable> getFilterTables(Long wfId, String platFormId, MemberIdentity currentUser);

    List<WfRunQueryTableDTO> getWFTablesByFunName(WfGetTableByFunNameDTO wfGetTableDTO,MemberIdentity currentUser);

    void  testExcel(HttpServletResponse response);

    List<WfGlobalVarDTO> IdeVarDeal(List<IdeCtrlValueDTO> postCtrlValues);

    void testApplyUserBegin(Long wfId, Long wf_run_id, Long node_id, int wf_status, int approve_result, String approve_comments,
                            Long applyUserId, Long createUserId);
    void testApplyUserEnd(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments,
                            Long applyUserId,Long createUserId);
    void testApplyUserProcess(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments,
                            Long applyUserId,Long createUserId);

    void testSendMessageDealUser(Long wfId,Long wf_run_id,Long node_id,Long dealUserId,int wf_status,int approve_result,String approve_comments, MemberIdentity currentUser);

    void testSendMessageCcUser(Long wfId,Long wf_run_id,Long node_id,int wf_status,int approve_result,String approve_comments);
}
