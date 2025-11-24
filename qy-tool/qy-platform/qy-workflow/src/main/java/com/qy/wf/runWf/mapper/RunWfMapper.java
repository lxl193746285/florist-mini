package com.qy.wf.runWf.mapper;

import com.qy.wf.runWf.dto.*;
import com.qy.wf.runWf.entity.RunWfEntity;
import com.qy.workflow.entity.WfDefNodeEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 工作流_执行_工作流 Mapper 接口
 * </p>
 *
 * @author wch
 * @since 2022-11-17
 */
@Mapper
public interface RunWfMapper extends BaseMapper<RunWfEntity> {
    // 分页查询工作流管理列表
    IPage<RunWfDTO> selectPageByQueryDTO(@Param("iPage") IPage iPage, @Param("ew") QueryWrapper<RunWfDTO> runWfQueryWrapper);

    List<RunWfDTO> selectPageByQueryDTO(@Param("ew") QueryWrapper<RunWfDTO> runWfQueryWrapper);

    RunWfDTO selectDTOById(Long id);


    IPage<RunWfEntity> getByType(@Param("iPage") IPage iPage, @Param("type") Integer type, @Param("id") Long id);

//    IPage<RunWfListDTO> selectPageByQueryDTO(@Param("iPage") IPage iPage, @Param("ew") QueryWrapper<RunWfListDTO> queryWrapper);

    List<RunWfListDTO> selectPageByQueryDTOList(@Param("ew")QueryWrapper<RunWfListDTO> queryWrapper);

    WfDefNodeEntity getDefNode(@Param("curNodeId") Long curNodeId);

//    IPage<RunWfDTO> getRunWfsV2(@Param("iPage") IPage iPage, @Param("ew") QueryWrapper<RunWfDTO> runWfQueryWrapper);

    List<RunWfDTO> getRunWfsTheList(@Param("ew")QueryWrapper<RunWfDTO> runWfQueryWrapper);

    Long getEmployeeId(Long beginUserId);

    String getEmployeeName(Long beginUserId);

    RunWfListDTO selectQueryDTO(@Param("ew") QueryWrapper<RunWfListDTO> queryWrapper);

    // 获取执行工作流节点信息+节点定义的信息
    RunWfNodeDTO selectRunNodeInfoDTO(Long runNodeId);

    List<RunWfNodeDTO> selectRunNodeDetailDTO(@Param("wfRunNodeIds") List<Long> wfRunNodeIds);


//    // 上一节点信息
//    RunWfNodeDTO selectPreNodeInfoDTO(@Param("ew") QueryWrapper<RunWfListDTO> queryWrapper);

    // 工作流明细列表
    IPage<RunWfListDTO> selectWFDetailPageByQueryDTO(@Param("iPage") IPage iPage,@Param("ew")  QueryWrapper<RunWfListDTO> queryWrapper);

    Long getMemberIdTurnEmployeeId(Long curNodeUserId);

    String getMemberIdTurnEmployeeName(Long curNodeUserId);

    // 获取当前工作流全部有操作权限的人员（办理人+待办人）
    List<Long> getWfRunUserIds(Long wfRunId);

    // 获取当前工作流执行节点有操作权限的人员（办理人+待办人）
    List<Long> getWfRunNodeUserIds(Long wfRunNodeId, Long nodeId);

    // 获取当前工作流全部有作废操作权限的人员（办理人+待办人）
    List<Long> getWfRunInvalidUserIds(Long wfRunId, Long wfId);

    List<RWCanInvalidDTO> getWfRunListInvalidUserIds(@Param("wfRunIds") List<Long> wfRunIds);

    void updateStoreHeaderApplication(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialApplication(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateStoreHeaderReport(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialReport(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialSecondReport(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialDepositReport(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);
    void updateAppealMajor(@Param("wfId") Long wfId, @Param("currentUserId") Long currentUserId);


    void updateStoreHeaderApplicationRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialApplicationRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateStoreHeaderReportRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialReportRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialSecondReportRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateStoreSpecialDepositReportRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);
    void updateAppealMajorRepair(@Param("wfId") Long wfId,  @Param("currentUserId") Long currentUserId);

}
