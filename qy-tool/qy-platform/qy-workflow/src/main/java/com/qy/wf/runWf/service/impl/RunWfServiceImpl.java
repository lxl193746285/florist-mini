package com.qy.wf.runWf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qy.common.service.ArkCodeTableService;
import com.qy.message.api.dto.MessageLogDTO;
import com.qy.rbac.api.client.AuthClient;
import com.qy.rbac.api.dto.PermissionWithRuleDTO;
import com.qy.rest.exception.ValidationException;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import com.qy.security.session.MemberIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.qy.utils.DateUtils;
import com.qy.utils.RestUtils;
import com.qy.utils.StringUtils;
import com.qy.utils.excel.ExcelUtils;
import com.qy.wf.defDefine.entity.DefDefineEntity;
import com.qy.wf.defDefine.service.DefDefineService;
import com.qy.wf.defNode.service.DefNodeService;
import com.qy.wf.defNodeUser.service.DefNodeUserService;
import com.qy.wf.runNode.dto.RunNodeDTO;
import com.qy.wf.runNode.dto.RunNodeDetail;
import com.qy.wf.runNode.dto.RunNodeDetailDTO;
import com.qy.wf.runNode.entity.RunNodeEntity;
import com.qy.wf.runNode.service.RunNodeService;
import com.qy.wf.runWf.dto.*;
import com.qy.wf.runWf.entity.RunWfEntity;
import com.qy.wf.runWf.mapper.RunWfMapper;
import com.qy.wf.runWf.service.RunWfService;
import com.qy.workflow.entity.WfRunVarEntity;
import com.qy.workflow.service.WfRunVarService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * 工作流_执行_工作流 服务实现类
 *
 * @author wch
 * @since 2022-11-17
 */
@Service
public class RunWfServiceImpl extends ServiceImpl<RunWfMapper, RunWfEntity> implements RunWfService {
    @Autowired
    private RunWfMapper runWfMapper;
    @Autowired
    private ArkCodeTableService arkCodeTableService;
    @Autowired
    private DefDefineService defDefineService;
    @Autowired
    private DefNodeService defNodeService;
    @Autowired
    private RunNodeService runNodeService;
    @Autowired
    private DefNodeUserService defNodeUserService;
    @Autowired
    private OrganizationSessionContext sessionContext;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private WfRunVarService wfRunVarService;


    private static final Logger logger = LoggerFactory.getLogger(RunWfServiceImpl.class);

    private QueryWrapper<RunWfDTO> handleQueryWrapper(RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser) {
        QueryWrapper<RunWfDTO> runWfQueryWrapper = new QueryWrapper<>();
        LocalDate startDate;
        LocalDate endDate;
        if (StringUtils.isNotEmpty(runWfQueryDTO.getStartCreateDate())) {
            startDate = LocalDate.parse(runWfQueryDTO.getStartCreateDate());
            runWfQueryWrapper.ge(null != startDate, "wrw.create_time", startDate);
        }
        if (StringUtils.isNotEmpty(runWfQueryDTO.getEndCreateDate())) {
            endDate = LocalDate.parse(runWfQueryDTO.getEndCreateDate());
            if (null != endDate) {
                runWfQueryWrapper.le("wrw.create_time", endDate.atTime(23, 59, 59));
            }
        }

        LocalDate startDate2;
        LocalDate endDate2;
        if (StringUtils.isNotEmpty(runWfQueryDTO.getBeginTimeStart())) {
            startDate2 = LocalDate.parse(runWfQueryDTO.getBeginTimeStart());
            runWfQueryWrapper.ge(null != startDate2, "wrw.begin_time", startDate2);
        }
        if (StringUtils.isNotEmpty(runWfQueryDTO.getBeginTimeEnd())) {
            endDate2 = LocalDate.parse(runWfQueryDTO.getBeginTimeEnd());
            if (null != endDate2) {
                runWfQueryWrapper.le("wrw.begin_time", endDate2.atTime(23, 59, 59));
            }
        }

        LocalDate startDate3;
        LocalDate endDate3;
        if (StringUtils.isNotEmpty(runWfQueryDTO.getCurArriveTimeStart())) {
            startDate3 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeStart());
            runWfQueryWrapper.ge(null != startDate3, "wrw.cur_arrive_time", startDate3);
        }
        if (StringUtils.isNotEmpty(runWfQueryDTO.getCurArriveTimeEnd())) {
            endDate3 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeEnd());
            if (null != endDate3) {
                runWfQueryWrapper.le("wrw.cur_arrive_time", endDate3.atTime(23, 59, 59));
            }
        }

        LocalDate startDate4;
        LocalDate endDate4;
        if (StringUtils.isNotEmpty(runWfQueryDTO.getEndTimeStrat())) {
            startDate4 = LocalDate.parse(runWfQueryDTO.getEndTimeStrat());
            runWfQueryWrapper.ge(null != startDate4, "wrw.end_time", startDate4);
        }
        if (StringUtils.isNotEmpty(runWfQueryDTO.getEndTimeEnd())) {
            endDate4 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeEnd());
            if (null != endDate4) {
                runWfQueryWrapper.le("wrw.end_time", endDate4.atTime(23, 59, 59));
            }
        }
        if (!org.assertj.core.util.Strings.isNullOrEmpty(runWfQueryDTO.getKeywords())) {
            runWfQueryWrapper.and(wrapper -> wrapper.like("wrw.wf_title", runWfQueryDTO.getKeywords())
                    .or().like("wrw.wf_code", runWfQueryDTO.getKeywords())
            );
        }

        runWfQueryWrapper.eq(runWfQueryDTO.getCurNodeUserId() != null, "wrw.cur_node_user_id", runWfQueryDTO.getCurNodeUserId());
        runWfQueryWrapper.eq(runWfQueryDTO.getBeginUserId() != null, "wrw.begin_user_id", runWfQueryDTO.getBeginUserId());
        runWfQueryWrapper.like(StringUtils.isNotEmpty(runWfQueryDTO.getWfTitle()), "wrw.wf_title", runWfQueryDTO.getWfTitle());
        runWfQueryWrapper.like(StringUtils.isNotEmpty(runWfQueryDTO.getWfCode()), "wrw.wf_code", runWfQueryDTO.getWfCode());
        runWfQueryWrapper.eq(runWfQueryDTO.getWfType() != null, "wrw.wf_type", runWfQueryDTO.getWfType());
        runWfQueryWrapper.eq(runWfQueryDTO.getWfStatus() != null, "wrw.wf_status", runWfQueryDTO.getWfStatus());
        runWfQueryWrapper.eq(runWfQueryDTO.getApproveResult() != null, "wrw.approve_result", runWfQueryDTO.getApproveResult());
        runWfQueryWrapper.eq(runWfQueryDTO.getStatus() != null, "wrw.status", runWfQueryDTO.getStatus());
        runWfQueryWrapper.eq(runWfQueryDTO.getCurNodeId() != null, "wrw.cur_node_id", runWfQueryDTO.getCurNodeId());
        runWfQueryWrapper.eq(runWfQueryDTO.getWfId() != null, "wrw.wf_id", runWfQueryDTO.getWfId());
        // 处理权限-账户类型
        runWfQueryWrapper.in(runWfQueryDTO.getAuthWfIds() != null && runWfQueryDTO.getAuthWfIds().size() > 0, "wrw.wf_id", runWfQueryDTO.getAuthWfIds());
        runWfQueryWrapper.eq(runWfQueryDTO.getCreatorId() != null, "wrw.creator_id", runWfQueryDTO.getCreatorId());
        if (runWfQueryDTO.getAuthHandlerId() != null) {
            runWfQueryWrapper.apply("EXISTS (SELECT 1 FROM wf_run_node wrn\n" +
                    " WHERE wrn.run_wf_id = wrw.id AND wrn.is_deleted = 0 AND wrn.cur_node_user_id = " + runWfQueryDTO.getAuthHandlerId()+" )");
        }

//        if (runWfQueryDTO.getAreaManagerId() != null) {
//            runWfQueryWrapper.apply(" wrw.creator_id in (" +
//                    "select asm.service_manager_id from st_app_service_manager asm \n" +
//                    "left join st_app_sale_area asa on asm.sale_area_id = asa.id \n" +
//                    "where asm.is_deleted = 0 and asa.is_deleted = 0 and asa.area_manager_id = "+runWfQueryDTO.getAreaManagerId()+" )");
//        }
        if (runWfQueryDTO.getAreaManagerId() != null) {
            runWfQueryWrapper.apply(" wrw.creator_id in (select asm.service_manager_id from st_app_service_manager asm \n" +
                    " left join st_app_sale_area asa on asm.sale_area_id = asa.id \n" +
                    " left join ark_uims_org_employee oe on asa.area_manager_id = oe.id and oe.is_deleted = 0 \n" +
                    " where asm.is_deleted = 0 and asa.is_deleted = 0 \n" +
                    " and oe.member_id = "+runWfQueryDTO.getAreaManagerId()+" )");
        }
        if (runWfQueryDTO.getDealStatus() != null && runWfQueryDTO.getDealStatus() == 1) {//我办理的
            runWfQueryWrapper.apply(
                    " wrw.id in (select DISTINCT run_wf_id from wf_run_node where is_cur_mark = 0 and is_deleted = 0 and cur_node_user_id = "+currentUser.getId()+"  )"
            );
        } else if (runWfQueryDTO.getDealStatus() != null && runWfQueryDTO.getDealStatus() == 2) {//我待办的
            runWfQueryWrapper.eq(" wrw.cur_node_user_id", currentUser.getId());
            runWfQueryWrapper.ne(" wrw.wf_status", 3);//流程状态不为结束的
//            runWfQueryWrapper.apply(
//                    " wrw.id in (select DISTINCT run_wf_id from wf_run_node where cur_deal_time is null and is_deleted = 0 and cur_node_user_id = "+currentUser.getId()+"  )"
//            );
        }

        if (runWfQueryDTO.getIsDeleted() != null && runWfQueryDTO.getIsDeleted() == 1) {//查询删除数据
            runWfQueryWrapper.eq("wrw.is_deleted", 1);
        } else {
            runWfQueryWrapper.eq("wrw.is_deleted", 0);
        }

        runWfQueryWrapper.orderByDesc("wrw.create_time");
        return runWfQueryWrapper;
    }

    @Override
    public IPage<RunWfDTO> getRunWfs(IPage iPage, RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser) {
        String indexPermission = "wf/run-wfs/index";
        String funId = "wf_run_wf";
        if (!currentUser.hasPermission(indexPermission)) {
            return new Page<>();
        }
        Identity identity = sessionContext.getUser();
        List <String> funs = new ArrayList<>();
        funs.add(funId);
        List<PermissionWithRuleDTO> userPermissions = authClient.getUserFunctionPermissions(identity.getId().toString(), OrganizationSessionContext.contextId, currentUser.getOrganizationId().toString(), funs);
        final String finalIndexPermission = indexPermission;
        List<PermissionWithRuleDTO> ruleDTOs = userPermissions.stream().filter(dto -> finalIndexPermission.equals(dto.getPermission())).collect(Collectors.toList());
        if (ruleDTOs.size() == 0) {
            return new Page<>();
        }
        PermissionWithRuleDTO rule = ruleDTOs.get(0);
        if (rule.getRuleScopeId() == null) {
            return new Page<>();
        }
        if (indexPermission.equals(rule.getPermission()) && rule.getRuleScopeId().contains("specifiedWfScope")) { // 指定工作流
            if (rule.getRuleScopeData() != null) {
                List<String> ruleScopeData = (List) rule.getRuleScopeData();
                List<Long> currentScopeIds = ruleScopeData.stream().map(Long::valueOf).collect(Collectors.toList());
                if (currentScopeIds.size() == 0) {// 未获取到权限-工作流
                    return new Page<>();
                }
                runWfQueryDTO.setAuthWfIds(currentScopeIds);
            }
        }
        //全部、本大区、自己的
        if (indexPermission.equals(rule.getPermission()) && rule.getRuleScopeId().contains("selfScope")) {// 自己的
            runWfQueryDTO.setAuthHandlerId(currentUser.getId());
        } else if (indexPermission.equals(rule.getPermission()) && rule.getRuleScopeId().contains("selfSaleRegion")) {//本大区
            runWfQueryDTO.setIsSelfSaleRegion(1);
            runWfQueryDTO.setAreaManagerId(currentUser.getId());
        }
        QueryWrapper<RunWfDTO> runWfQueryWrapper = handleQueryWrapper(runWfQueryDTO, currentUser);
        return runWfMapper.selectPageByQueryDTO(iPage, runWfQueryWrapper);
    }

    @Override
    public List<RunWfDTO> getRunWfs(RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser) {
        QueryWrapper<RunWfDTO> runWfQueryWrapper = handleQueryWrapper(runWfQueryDTO, currentUser);
        return runWfMapper.selectPageByQueryDTO(runWfQueryWrapper);
    }

    @Override
    public RunWfDTO getRunWfDTO(Long id, MemberIdentity currentUser) {
        RunWfDTO runWfDTO = runWfMapper.selectDTOById(id);
        if (runWfDTO == null) {
            throw new RuntimeException("未找到工作流");
        }
        return runWfDTO;
    }

    @Override
    public RunWfEntity getRunWf(Long id, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = this.getOne(RestUtils.getQueryWrapperById(id));
        if (runWfEntity == null) {
            throw new RuntimeException("未找到工作流");
        }
        return runWfEntity;
    }

    @Override
    public IPage<RunWfListDTO> getRunWfDetailList(IPage iPage, RunWfQueryDTO queryDTO, MemberIdentity currentUser) {
        QueryWrapper<RunWfListDTO> queryWrapper = new QueryWrapper<RunWfListDTO>();
        getQuery(queryWrapper, queryDTO);
        queryWrapper.like(!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getWfTitle()), "b.wf_title", queryDTO.getWfTitle());
        queryWrapper.like(!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getWfCode()), "b.wf_code", queryDTO.getWfCode());
        queryWrapper.eq(queryDTO.getWfType() != null, "b.wf_type", queryDTO.getWfType());
        queryWrapper.eq(queryDTO.getWfStatus() != null, "b.wf_status", queryDTO.getWfStatus());
        queryWrapper.eq(queryDTO.getCurStatus() != null, "a.cur_status", queryDTO.getCurStatus());
        queryWrapper.eq(queryDTO.getApproveResult() != null, "a.approve_result", queryDTO.getApproveResult());
        queryWrapper.eq(queryDTO.getBeginUserId() != null, "b.begin_user_id", queryDTO.getBeginUserId());
        queryWrapper.eq(queryDTO.getCurNodeUserId() != null, "a.cur_node_user_id", queryDTO.getCurNodeUserId());
        if (!org.assertj.core.util.Strings.isNullOrEmpty(queryDTO.getKeywords())) {
            queryWrapper.and(wrapper -> wrapper.like("b.wf_title", queryDTO.getKeywords())
                            .or().like("b.wf_code", queryDTO.getKeywords())
            );
        }
        queryWrapper.eq("a.is_deleted", 0);
        queryWrapper.orderByDesc("b.wf_code").orderByAsc("a.sort").orderByDesc("a.create_time");

        return runWfMapper.selectWFDetailPageByQueryDTO(iPage, queryWrapper);
    }

    @Override
    public List<RunWfDTO> getRunWfsTheList(RunWfQueryDTO runWfQueryDTO, MemberIdentity currentUser) {
        QueryWrapper<RunWfDTO> runWfQueryWrapper = new QueryWrapper<>();
        LocalDate startDate;
        LocalDate endDate;
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getStartCreateDate())) {
            startDate = LocalDate.parse(runWfQueryDTO.getStartCreateDate());
            runWfQueryWrapper.ge(null != startDate, "wfRun.create_time", startDate);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getEndCreateDate())) {
            endDate = LocalDate.parse(runWfQueryDTO.getEndCreateDate());
            if (null != endDate) {
                runWfQueryWrapper.le("wfRun.create_time", endDate.atTime(23, 59, 59));
            }
        }

        LocalDate startDate2;
        LocalDate endDate2;
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getBeginTimeStart())) {
            startDate2 = LocalDate.parse(runWfQueryDTO.getBeginTimeStart());
            runWfQueryWrapper.ge(null != startDate2, "wfRun.begin_time", startDate2);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getBeginTimeEnd())) {
            endDate2 = LocalDate.parse(runWfQueryDTO.getBeginTimeEnd());
            if (null != endDate2) {
                runWfQueryWrapper.le("wfRun.begin_time", endDate2.atTime(23, 59, 59));
            }
        }

        LocalDate startDate3;
        LocalDate endDate3;
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getCurArriveTimeStart())) {
            startDate3 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeStart());
            runWfQueryWrapper.ge(null != startDate3, "wfRun.cur_arrive_time", startDate3);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getCurArriveTimeEnd())) {
            endDate3 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeEnd());
            if (null != endDate3) {
                runWfQueryWrapper.le("wfRun.cur_arrive_time", endDate3.atTime(23, 59, 59));
            }
        }


        LocalDate startDate4;
        LocalDate endDate4;
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getEndTimeStrat())) {
            startDate4 = LocalDate.parse(runWfQueryDTO.getEndTimeStrat());
            runWfQueryWrapper.ge(null != startDate4, "wfRun.end_time", startDate4);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(runWfQueryDTO.getEndTimeEnd())) {
            endDate4 = LocalDate.parse(runWfQueryDTO.getCurArriveTimeEnd());
            if (null != endDate4) {
                runWfQueryWrapper.le("wfRun.end_time", endDate4.atTime(23, 59, 59));
            }
        }
        if (!org.assertj.core.util.Strings.isNullOrEmpty(runWfQueryDTO.getKeywords())) {
            runWfQueryWrapper.and(wrapper -> wrapper.like("wfRun.wf_title", runWfQueryDTO.getKeywords())
                            .or().like("wfRun.wf_code", runWfQueryDTO.getKeywords())
                    //                    .or().eq("", queryDTO.getKeywords())
            );
        }
        runWfQueryWrapper.eq(runWfQueryDTO.getCurNodeUserId() != null, "wfRun.cur_node_user_id", runWfQueryDTO.getCurNodeUserId());
        runWfQueryWrapper.eq(runWfQueryDTO.getBeginUserId() != null, "wfRun.begin_user_id", runWfQueryDTO.getBeginUserId());
        runWfQueryWrapper.like(!Strings.isEmpty(runWfQueryDTO.getWfTitle()), "wfRun.wf_title", runWfQueryDTO.getWfTitle());
        runWfQueryWrapper.like(!Strings.isEmpty(runWfQueryDTO.getWfCode()), "wfRun.wf_code", runWfQueryDTO.getWfCode());
        runWfQueryWrapper.eq(runWfQueryDTO.getWfType() != null, "wfRun.wf_type", runWfQueryDTO.getWfType());
        runWfQueryWrapper.eq(runWfQueryDTO.getWfStatus() != null, "wfRun.wf_status", runWfQueryDTO.getWfStatus());
        runWfQueryWrapper.eq(runWfQueryDTO.getApproveResult() != null, "wfRun.approve_result", runWfQueryDTO.getApproveResult());
        runWfQueryWrapper.eq(runWfQueryDTO.getStatus() != null, "wfRun.satus", runWfQueryDTO.getStatus());
        List<RunWfDTO> runWfDTOIPage = runWfMapper.getRunWfsTheList(runWfQueryWrapper);
        return runWfDTOIPage;
    }

    @Override
    public void export(List<RunWfDTO> runWfDTOList, HttpServletResponse response, MemberIdentity currentUser) {
        List<RunWfModel> list = new ArrayList();
        for (RunWfDTO runWfDTO : runWfDTOList) {
            RunWfModel runWfModel = new RunWfModel();
            runWfModel.setWfCode(runWfDTO.getWfCode());
            runWfModel.setTitle(runWfDTO.getWfTitle());
            if (!Strings.isEmpty(runWfDTO.getWfName())) {
                runWfModel.setNote(runWfDTO.getWfName().replaceAll("\r|\n*", ""));
            }
            runWfModel.setType(runWfDTO.getWfTypeName());
            runWfModel.setStatusName(runWfDTO.getWfStatusName());
            runWfModel.setApproveResultName(runWfDTO.getApproveResultName());
            runWfModel.setApproveComments(runWfDTO.getApproveComments());
            runWfModel.setUserDeptName(runWfDTO.getBeginDeptName());
            runWfModel.setUserName(runWfDTO.getBeginUserName());
            runWfModel.setStartTime(runWfDTO.getBeginTimeName());
            runWfModel.setEndTime(runWfDTO.getEndTimeName());
            runWfModel.setCreateName(runWfDTO.getCreatorName());
            runWfModel.setCreateTime(runWfDTO.getCreateTimeName());
            runWfModel.setUpdatorName(runWfDTO.getUpdatorName());
            runWfModel.setUpdateTimeName(runWfDTO.getUpdateTimeName());
            list.add(runWfModel);
        }

        try {
            String fileName = URLEncoder.encode("工作流审批信息", "utf-8");
            ExcelUtils.export(response, fileName, list, RunWfModel.class);
        } catch (Exception e) {

        }
    }

    @Override
    public List<RunWfListDTO> getRunWfExportList(RunWfQueryDTO queryDTO, MemberIdentity currentUser) {
        QueryWrapper<RunWfListDTO> queryWrapper = new QueryWrapper<RunWfListDTO>();
        getQuery(queryWrapper, queryDTO);
        queryWrapper.like(!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getWfTitle()), "b.wf_title", queryDTO.getWfTitle());
        queryWrapper.like(!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getWfCode()), "b.wf_code", queryDTO.getWfCode());
        queryWrapper.eq(queryDTO.getWfType() != null, "b.wf_type", queryDTO.getWfType());
        queryWrapper.eq(queryDTO.getWfStatus() != null, "b.wf_status", queryDTO.getWfStatus());
        queryWrapper.eq(queryDTO.getCurStatus() != null, "a.cur_status", queryDTO.getCurStatus());
        queryWrapper.eq(queryDTO.getApproveResult() != null, "a.approve_result", queryDTO.getApproveResult());
        queryWrapper.eq(queryDTO.getBeginUserId() != null, "b.begin_user_id", queryDTO.getBeginUserId());
        queryWrapper.eq(queryDTO.getCurNodeUserId() != null, "a.cur_node_user_id", queryDTO.getCurNodeUserId());
        if (!org.assertj.core.util.Strings.isNullOrEmpty(queryDTO.getKeywords())) {
            queryWrapper.and(wrapper -> wrapper.like("b.wf_title", queryDTO.getKeywords())
                            .or().like("b.wf_code", queryDTO.getKeywords())
//                    .or().eq("", queryDTO.getKeywords())
            );
        }
        queryWrapper.eq("a.is_deleted", 0);
        List<RunWfListDTO> runWfListDTOIPage = runWfMapper.selectPageByQueryDTOList(queryWrapper);
//        runWfListDTOIPage = getMapperToDTOExport(runWfListDTOIPage, currentUser);
        return runWfListDTOIPage;
    }

    @Override
    public void exportList(List<RunWfListDTO> runWfEntities, HttpServletResponse response, MemberIdentity currentUser) {
        List<RunWfListModel> list = new ArrayList();
        if (!CollectionUtils.isEmpty(runWfEntities)) {
            for (RunWfListDTO runWfDTO : runWfEntities) {
                RunWfListModel runWfModel = new RunWfListModel();
                runWfModel.setWfCode(runWfDTO.getWfCode());
                runWfModel.setTitle(runWfDTO.getWfTitle());
                if (!Strings.isEmpty(runWfDTO.getWfName())) {
                    runWfModel.setNote(runWfDTO.getWfName().replaceAll("\r|\n*", ""));
                }
                runWfModel.setSort(runWfDTO.getSort());
                runWfModel.setType(runWfDTO.getWfTypeName());
                runWfModel.setStatusName(runWfDTO.getCurStatusName());
                runWfModel.setApproveResultName(runWfDTO.getApproveResultName());
                runWfModel.setApproveComments(runWfDTO.getApproveComments());
                runWfModel.setBeginDeptName(runWfDTO.getBeginDeptName());
                runWfModel.setBeginUserName(runWfDTO.getBeginUserName());
                runWfModel.setBeginTimeName(runWfDTO.getBeginTimeName());
                runWfModel.setEndTimeName(runWfDTO.getEndTimeName());
                runWfModel.setCurNodeName(runWfDTO.getCurNodeName());
                runWfModel.setCurStatusName(runWfDTO.getCurStatusName());
                runWfModel.setCurApproveResultName(runWfDTO.getCurApproveResultName());
                runWfModel.setCurApproveComments(runWfDTO.getCurApproveComments());
                runWfModel.setCurNodeUserName(runWfDTO.getCurNodeUserName());
                runWfModel.setCurSendTimeName(runWfDTO.getCurSendTimeName());
                runWfModel.setCurArriveTimeName(runWfDTO.getCurArriveTimeName());
                runWfModel.setCurReadTimeName(runWfDTO.getCurReadTimeName());
                runWfModel.setCurDealTimeName(runWfDTO.getCurDealTimeName());
                list.add(runWfModel);
            }
        }
        try {
            String fileName = URLEncoder.encode("工作流审批明细", "utf-8");
            ExcelUtils.export(response, fileName, list, RunWfListModel.class);
        } catch (Exception e) {

        }
    }

    private QueryWrapper<RunWfListDTO> getQuery(QueryWrapper<RunWfListDTO> queryWrapper, RunWfQueryDTO queryDTO) {
        LocalDate startDate;
        LocalDate endDate;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getStartCreateDate())) {
            startDate = LocalDate.parse(queryDTO.getStartCreateDate());
            queryWrapper.ge(null != startDate, "a.create_time", startDate);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getEndCreateDate())) {
            endDate = LocalDate.parse(queryDTO.getEndCreateDate());
            if (null != endDate) {
                queryWrapper.le("a.create_time", endDate.atTime(23, 59, 59));
            }
        }
        LocalDate beginTimeStartDate;
        LocalDate beginTimeEndDate;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getBeginTimeStart())) {
            beginTimeStartDate = LocalDate.parse(queryDTO.getBeginTimeStart());
            queryWrapper.ge(null != beginTimeStartDate, "b.begin_time", beginTimeStartDate);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getBeginTimeEnd())) {
            beginTimeEndDate = LocalDate.parse(queryDTO.getBeginTimeEnd());
            if (null != beginTimeEndDate) {
                queryWrapper.le("b.begin_time", beginTimeEndDate.atTime(23, 59, 59));
            }
        }
        LocalDate endTimeStart;
        LocalDate endTimeEnd;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getEndTimeStrat())) {
            endTimeStart = LocalDate.parse(queryDTO.getEndTimeStrat());
            queryWrapper.ge(null != endTimeStart, "b.end_time", endTimeStart);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getEndTimeEnd())) {
            endTimeEnd = LocalDate.parse(queryDTO.getCurArriveTimeEnd());
            if (null != endTimeEnd) {
                queryWrapper.le("b.end_time", endTimeEnd.atTime(23, 59, 59));
            }
        }

        LocalDate curArriveTimeStart;
        LocalDate curArriveTimeEnd;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getCurArriveTimeStart())) {
            curArriveTimeStart = LocalDate.parse(queryDTO.getCurArriveTimeStart());
            queryWrapper.ge(null != curArriveTimeStart, "a.cur_arrive_time", curArriveTimeStart);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getCurArriveTimeEnd())) {
            curArriveTimeEnd = LocalDate.parse(queryDTO.getCurArriveTimeEnd());
            if (null != curArriveTimeEnd) {
                queryWrapper.le("a.cur_arrive_time", curArriveTimeEnd.atTime(23, 59, 59));
            }
        }
        LocalDate curDealTimeStart;
        LocalDate curDealTimeEnd;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getCurDealTimeStart())) {
            curDealTimeStart = LocalDate.parse(queryDTO.getCurDealTimeStart());
            queryWrapper.ge(null != curDealTimeStart, "a.cur_deal_time", curDealTimeStart);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getCurDealTimeEnd())) {
            curDealTimeEnd = LocalDate.parse(queryDTO.getCurDealTimeEnd());
            if (null != curDealTimeEnd) {
                queryWrapper.le("a.cur_deal_time", curDealTimeEnd.atTime(23, 59, 59));
            }
        }
        return queryWrapper;
    }

    public RunWfListDTO mapperDetailToDTO(RunWfDetailDTO runWfDetailDTO, MemberIdentity currentUser) {
        return null;
    }


    public List<RunWfListDTO> mapperDetailListToDTO(List<RunWfListDTO> runWfListDTOList, MemberIdentity currentUser) {
        List<Action> normalActions = new ArrayList<>();
        List<Action> actions = currentUser.getActions("wf_run_wf_detail");
        List<Action> wfActions = currentUser.getActions("wf_run_wf");
        if (currentUser != null) {
            if (actions.stream().filter(o -> o.getId().equals("view")).findFirst().isPresent()) {
                normalActions.add(actions.stream().filter(o -> o.getId().equals("view")).findFirst().get());
            }
        }
        for (RunWfListDTO runWfListDTO : runWfListDTOList) {
            // 权限按钮
            List<Action> allActions = new ArrayList<>();
            allActions.addAll(normalActions);
            List<Action> haveWfActions = getRunWfActionList(2, runWfListDTO.getWfRunNodeId(), wfActions, currentUser);
            if (!CollectionUtils.isEmpty(haveWfActions)) {
                allActions.addAll(haveWfActions);
            }
            runWfListDTO.setActions(allActions);

            if (runWfListDTO.getBeginTime() != null) {
                runWfListDTO.setBeginTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getBeginTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getEndTime() != null) {
                runWfListDTO.setEndTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getEndTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getCurSendTime() != null) {
                runWfListDTO.setCurSendTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getCurSendTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getCurArriveTime() != null) {
                runWfListDTO.setCurArriveTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getCurArriveTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getCurReadTime() != null) {
                runWfListDTO.setCurReadTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getCurReadTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getCurDealTime() != null) {
                runWfListDTO.setCurDealTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getCurDealTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getCreateTime() != null) {
                runWfListDTO.setCreateTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getCreateTime(), DateUtils.dateTimeFormat));
            }
            if (runWfListDTO.getUpdateTime() != null) {
                runWfListDTO.setUpdateTimeName(DateUtils.localDateTimeToDateStr(runWfListDTO.getUpdateTime(), DateUtils.dateTimeFormat));
            }
//            runWfListDTO.setWfStatusName(arkCodeTableService.getNameBySystem("wf_status", runWfListDTO.getWfStatus()));
//            runWfListDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", runWfListDTO.getStatus()));
//            runWfListDTO.setWfTypeName(arkCodeTableService.getNameBySystem("wf_def_define_wf_type", runWfListDTO.getWfType()));
//            runWfListDTO.setNextNodeStatusName(arkCodeTableService.getNameBySystem("wf_status", runWfListDTO.getNextNodeStatus()));
//            runWfListDTO.setApproveResultName(arkCodeTableService.getNameBySystem("wf_approve_result", runWfListDTO.getApproveResult()));
//            runWfListDTO.setCurApproveResultName(arkCodeTableService.getNameBySystem("wf_approve_result", runWfListDTO.getCurApproveResult()));
//            runWfListDTO.setCurStatusName(arkCodeTableService.getNameBySystem("wf_status", runWfListDTO.getCurStatus()));
//            if (runWfListDTO.getCurNodeUserId() != null) {
//                String curNodeUserName = runWfMapper.getMemberIdTurnEmployeeName(runWfListDTO.getCurNodeUserId());
//                if (!Strings.isEmpty(curNodeUserName)) {
//                    runWfListDTO.setCurNodeUserName(curNodeUserName);
//                }
//            }
//            if (runWfListDTO.getBeginUserId() != null) {
//                String beginUserName = runWfMapper.getMemberIdTurnEmployeeName(runWfListDTO.getBeginUserId());
//                if (!Strings.isEmpty(beginUserName)) {
//                    runWfListDTO.setBeginUserName(beginUserName);
//                }
//            }

        }
        return runWfListDTOList;
    }

    @Override
    public RunWfEntity createRunWf(RunWfFormDTO runWfFormDTO, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = new RunWfEntity();
        BeanUtils.copyProperties(runWfFormDTO, runWfEntity);
        runWfEntity.setCreateTime(LocalDateTime.now());
        runWfEntity.setCreatorId(currentUser.getId());
        runWfEntity.setCreatorName(currentUser.getName());
        runWfEntity.setUpdatorId(Long.valueOf(0));
        runWfEntity.setIsDeleted((int) 0);
        runWfEntity.setDeletorId(Long.valueOf(0));

        this.save(runWfEntity);
        return runWfEntity;
    }

    @Override
    public RunWfEntity updateRunWf(Long id, RunWfFormDTO runWfFormDTO, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = getById(id);
        BeanUtils.copyProperties(runWfFormDTO, runWfEntity);
        runWfEntity.setUpdateTime(LocalDateTime.now());
        runWfEntity.setUpdatorId(currentUser.getId());
        runWfEntity.setUpdatorName(currentUser.getName());

        this.updateById(runWfEntity);
        return runWfEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RunWfEntity deleteRunWf(Long id, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = getRunWf(id, currentUser);
        runWfEntity.setDeleteTime(LocalDateTime.now());
        runWfEntity.setDeletorId(currentUser.getId());
        runWfEntity.setDeletorName(currentUser.getName());

        runWfEntity.setIsDeleted((int) 1);
        this.updateById(runWfEntity);
//        删除的时候，主表+子表+参数表+业务表
        //删除子表
        LambdaUpdateWrapper<RunNodeEntity> updateWrapper1 = new LambdaUpdateWrapper<>();
        updateWrapper1.set(RunNodeEntity::getIsDeleted, 1)
                .set(RunNodeEntity::getDeleteTime, LocalDateTime.now())
                .set(RunNodeEntity::getDeletorId, currentUser.getId())
                .set(RunNodeEntity::getDeletorName, currentUser.getName())
                .eq(RunNodeEntity::getRunWfId, runWfEntity.getId());
        runNodeService.update(updateWrapper1);
        //删除参数表
        LambdaUpdateWrapper<WfRunVarEntity> updateWrapper2 = new LambdaUpdateWrapper<>();
        updateWrapper2.set(WfRunVarEntity::getIsDeleted, 1)
                .set(WfRunVarEntity::getDeleteTime, LocalDateTime.now())
                .set(WfRunVarEntity::getDeletorId, currentUser.getId())
                .set(WfRunVarEntity::getDeletorName, currentUser.getName())
                .eq(WfRunVarEntity::getRunWfId, runWfEntity.getId());
        wfRunVarService.update(updateWrapper2);
        //删除业务表数据
        if (runWfEntity.getWfType().equals(1)) {//二级乡镇门头申请 st_store_header_application
            runWfMapper.updateStoreHeaderApplication(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(2)) {//二级乡镇门头核报  st_store_header_report
            runWfMapper.updateStoreHeaderReport(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(3)) {//建店申请 st_store_special_application
            runWfMapper.updateStoreSpecialApplication(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(4)) {//建店核报 st_store_special_report
            runWfMapper.updateStoreSpecialReport(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(5)) {//二次建店核报 st_store_special_second_report
            runWfMapper.updateStoreSpecialSecondReport(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(10)) {//押金退返 st_store_special_deposit_report
            runWfMapper.updateStoreSpecialDepositReport(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(9)) {//重大客诉 st_app_customer_appeal_major
            runWfMapper.updateAppealMajor(runWfEntity.getId(), currentUser.getId());
        }
        return runWfEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RunWfEntity deleteRunWfRepair(Long id, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = getById(id);

        runWfEntity.setIsDeleted((int) 0);
        this.updateById(runWfEntity);
//        删除的时候，主表+子表+参数表+业务表
        //删除子表
        LambdaUpdateWrapper<RunNodeEntity> updateWrapper1 = new LambdaUpdateWrapper<>();
        updateWrapper1.set(RunNodeEntity::getIsDeleted, 0)
                .eq(RunNodeEntity::getRunWfId, runWfEntity.getId());
        runNodeService.update(updateWrapper1);
        //删除参数表
        LambdaUpdateWrapper<WfRunVarEntity> updateWrapper2 = new LambdaUpdateWrapper<>();
        updateWrapper2.set(WfRunVarEntity::getIsDeleted, 0)
                .eq(WfRunVarEntity::getRunWfId, runWfEntity.getId());
        wfRunVarService.update(updateWrapper2);
        //恢复业务表数据
        if (runWfEntity.getWfType().equals(1)) {//二级乡镇门头申请 st_store_header_application
            runWfMapper.updateStoreHeaderApplicationRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(2)) {//二级乡镇门头核报  st_store_header_report
            runWfMapper.updateStoreHeaderReportRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(3)) {//建店申请 st_store_special_application
            runWfMapper.updateStoreSpecialApplicationRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(4)) {//建店核报 st_store_special_report
            runWfMapper.updateStoreSpecialReportRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(5)) {//二次建店核报 st_store_special_second_report
            runWfMapper.updateStoreSpecialSecondReportRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(10)) {//押金退返 st_store_special_deposit_report
            runWfMapper.updateStoreSpecialDepositReportRepair(runWfEntity.getId(), currentUser.getId());
        } else if (runWfEntity.getWfType().equals(9)) {//重大客诉 st_app_customer_appeal_major
            runWfMapper.updateAppealMajorRepair(runWfEntity.getId(), currentUser.getId());
        }
        return runWfEntity;
    }

    @Override
    public RunWfDTO mapperToDTO(RunWfDTO runWfDTO, MemberIdentity currentUser) {
        List<RunNodeDTO> runNodeDTOList = runNodeService.getListById(runWfDTO.getId());
        runWfDTO.setRunNodeDTOList(runNodeDTOList);

        // 处理权限按钮
        List<Action> wfActions = currentUser.getActions("wf_run_wf");
        List<Action> actions = getRunWfActionList(1, runWfDTO.getCurRunNodeId(), wfActions, currentUser);
        runWfDTO.setActions(actions);
        return runWfDTO;
    }

    @Override
    public List<RunWfDTO> mapperToDTO(List<RunWfDTO> runWfDTOList, MemberIdentity currentUser) {
//        List<Action> normalActions = new ArrayList<>();
        List<Action> actions = currentUser.getActions("wf_run_wf");
//        if (currentUser != null) {
//            if (actions.stream().filter(o -> o.getId().equals("view")).findFirst().isPresent()) {
//                normalActions.add(actions.stream().filter(o -> o.getId().equals("view")).findFirst().get());
//            }
//            if (actions.stream().filter(o -> o.getId().equals("export")).findFirst().isPresent()) {
//                normalActions.add(actions.stream().filter(o -> o.getId().equals("export")).findFirst().get());
//            }
////            if (actions.stream().filter(o -> o.getId().equals("delete")).findFirst().isPresent()) {
////                normalActions.add(actions.stream().filter(o -> o.getId().equals("delete")).findFirst().get());
////            }
////            if (actions.stream().filter(o -> o.getId().equals("delete_repair")).findFirst().isPresent()) {
////                normalActions.add(actions.stream().filter(o -> o.getId().equals("delete_repair")).findFirst().get());
////            }
//        }
        for (RunWfDTO runWfDTO : runWfDTOList) {
            List<Action> normalActions = new ArrayList<>();
            if (currentUser != null) {
                if (actions.stream().filter(o -> o.getId().equals("view")).findFirst().isPresent()) {
                    normalActions.add(actions.stream().filter(o -> o.getId().equals("view")).findFirst().get());
                }
                if (actions.stream().filter(o -> o.getId().equals("export")).findFirst().isPresent()) {
                    normalActions.add(actions.stream().filter(o -> o.getId().equals("export")).findFirst().get());
                }
                if (runWfDTO.getIsDeleted() == 0) {
                    if (actions.stream().filter(o -> o.getId().equals("delete")).findFirst().isPresent()) {
                        normalActions.add(actions.stream().filter(o -> o.getId().equals("delete")).findFirst().get());
                    }
                }
                if (runWfDTO.getIsDeleted() == 1) {
                    if (actions.stream().filter(o -> o.getId().equals("delete_repair")).findFirst().isPresent()) {
                        normalActions.add(actions.stream().filter(o -> o.getId().equals("delete_repair")).findFirst().get());
                    }
                }
                if (runWfDTO.getWfStatus() != null && runWfDTO.getWfStatus() != 3) {
                    if (actions.stream().filter(o -> o.getId().equals("replace-approver")).findFirst().isPresent()) {
                        normalActions.add(actions.stream().filter(o -> o.getId().equals("replace-approver")).findFirst().get());
                    }
                }
            }

            List<Action> allActions = new ArrayList<>();
            allActions.addAll(normalActions);
            List<Action> wfActions = getRunWfActionList(1, runWfDTO.getCurRunNodeId(), actions, currentUser);
            if (!CollectionUtils.isEmpty(wfActions)) {
                allActions.addAll(wfActions);
            }
            runWfDTO.setActions(allActions);
        }
//        //去重根据时间倒叙
//        runWfDTOList = runWfDTOList.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>
//                        (Comparator.comparing(RunWfDTO::getId))), ArrayList::new))
//                .stream().sorted(Comparator.comparing(RunWfDTO::getCreateTime).reversed()).collect(Collectors.toList());
        return runWfDTOList;
    }

    @Override
    public RunWfDetailDTO getRunWfDetail(Long id, MemberIdentity currentUser) {
        RunWfEntity runWfEntity = this.getOne(RestUtils.getQueryWrapperById(id));
        if (runWfEntity == null) {
            throw new ValidationException("未找到工作流");
        }
        RunWfDetailDTO runWfDetailDTO = new RunWfDetailDTO();
        /**基本信息*/
        RunWfDetail runWfDetail = new RunWfDetail();
        runWfDetail.setWfRunId(id);
        runWfDetail.setWfType(runWfEntity.getWfType());
        runWfDetail.setWfTypeName(arkCodeTableService.getNameBySystem("wf_def_define_wf_type", runWfEntity.getWfType()));
        runWfDetail.setWfId(runWfEntity.getWfId());
        runWfDetail.setWfStatus(runWfEntity.getWfStatus());
        runWfDetail.setWfStatusName(arkCodeTableService.getNameBySystem("wf_status", runWfEntity.getWfStatus()));
        runWfDetail.setWfNote(runWfEntity.getWfNote());
        runWfDetail.setApproveComments(runWfEntity.getApproveComments());
        runWfDetail.setApproveResult(runWfEntity.getApproveResult());
        runWfDetail.setApproveResultName(arkCodeTableService.getNameBySystem("wf_approve_result", runWfEntity.getApproveResult()));
        runWfDetail.setPreRunNodeId(runWfEntity.getPreRunNodeId());
        runWfDetail.setPreNodeId(runWfEntity.getPreNodeId());
        runWfDetail.setPreNodeStatus(runWfEntity.getPreNodeStatus());
        runWfDetail.setPreNodeUserId(runWfEntity.getPreNodeUserId());
        runWfDetail.setNextNodeId(runWfEntity.getNextNodeId());
        runWfDetail.setNextRunNodeId(runWfEntity.getNextRunNodeId());
        runWfDetail.setNextNodeStatus(runWfEntity.getNextNodeStatus());
        runWfDetail.setNextNodeUserId(runWfEntity.getNextNodeUserId());
        runWfDetail.setCurRunNodeId(runWfEntity.getCurRunNodeId());

        // 工作流办理权限
        List<Action> actions = currentUser.getActions("wf_run_wf");
        List<Action> wfActions = getRunWfActionList(2, runWfEntity.getCurRunNodeId(), actions, currentUser);
        if (!CollectionUtils.isEmpty(wfActions)) {
            runWfDetail.setActions(wfActions);
        }
        DefDefineEntity defDefineEntity = defDefineService.getById(runWfEntity.getWfId());
        if (defDefineEntity != null) {
            if (!Strings.isEmpty(defDefineEntity.getName())) {
                runWfDetail.setWfName(defDefineEntity.getName());
            }
        }
        runWfDetail.setWfCode(runWfEntity.getWfCode());
        runWfDetail.setWfTitle(runWfEntity.getWfTitle());
        Long employeeId = runWfMapper.getEmployeeId(runWfEntity.getBeginUserId());
        String employeeName = runWfMapper.getEmployeeName(runWfEntity.getBeginUserId());

        runWfDetail.setBeginUserId(employeeId);
        runWfDetail.setBeginUserName(employeeName);
//        EmployeeBasicDTO employeeBasicDTO = employeeClient.getBasicEmployeeById(runWfEntity.getBeginUserId());
//        if (employeeBasicDTO != null) {
//            if (!Strings.isEmpty(employeeBasicDTO.getName())) {
//                runWfDetail.setBeginUserName(employeeBasicDTO.getName());
//            }
//        }
        runWfDetail.setBeginTime(DateUtils.localDateTimeToDateStr(runWfEntity.getBeginTime(), DateUtils.dateTimeFormat));
        /**流程*/
        RunNodeDetailDTO runNodeDetailDTO = new RunNodeDetailDTO();
        runNodeDetailDTO.setBeginUserId(employeeId);
        runNodeDetailDTO.setBeginUserName(employeeName);
//        if (employeeBasicDTO != null) {
//            if (!Strings.isEmpty(employeeBasicDTO.getName())) {
//                runNodeDetailDTO.setBeginUserName(employeeBasicDTO.getName());
//            }
//        }
        runNodeDetailDTO.setBeginTime(DateUtils.localDateTimeToDateStr(runWfEntity.getBeginTime(), DateUtils.dateTimeFormat));
        List<RunNodeDetail> runNodeDetailList = runNodeService.getDetailListById(runWfEntity.getId(), currentUser);
        runNodeDetailDTO.setRunNodeDetailList(runNodeDetailList);


        runWfDetailDTO.setRunWfDetail(runWfDetail);
        runWfDetailDTO.setRunNodeDetailDTO(runNodeDetailDTO);
        return runWfDetailDTO;
    }

    /**
     * 执行工作流详情--消息推送
     *
     * @param id
     * @param currentUser
     * @return
     */
    @Override
    public List<MessageLogDTO> getMessageSend(Long id, MemberIdentity currentUser) {
        LambdaQueryWrapper<WfRunVarEntity> wfRunVarQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return null;
    }

    @Override
    public List<Action> getRunWfActionList(Integer type, Long runNodeId, List<Action> actions, MemberIdentity currentUser) {
        if (runNodeId == null || actions == null || actions.size() == 0) {
            return new ArrayList<>();
        }
        // 2.查询工作流指定节点数据
        RunWfNodeDTO runWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runNodeId);
        if (runWfNodeDTO == null) {
            return new ArrayList<>();
        }
        if (runWfNodeDTO.getWfStatus() != null && runWfNodeDTO.getWfStatus() != 3) {// 工作流未结束
            // 查询有权限的人（办理人+代办人）
            List<Long> runNodeUserIdList = runWfMapper.getWfRunNodeUserIds(runNodeId, runWfNodeDTO.getCurNodeId());
            List<Action> handleActions = new ArrayList<>();
            if (runWfNodeDTO.getIsCurMark() == 1 && runWfNodeDTO.getCurStatus() != 3 && runNodeUserIdList.contains(currentUser.getId())) {
                //当前登录人和当前节点的审批人是同一个人，并且在处理中的才能审批
                if (runWfNodeDTO.getIsCanAgree() != null && runWfNodeDTO.getIsCanAgree() == 1 && actions.stream().filter(o -> o.getId().equals("agree")).findFirst().isPresent()) {// 同意
                    if (StringUtils.isNotEmpty(runWfNodeDTO.getAgreeAlias())) {
                        Action action = handleAliasAction(actions, "agree", runWfNodeDTO.getAgreeAlias());
                        handleActions.add(action);
                    } else {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("agree")).findFirst().get());
                    }
                }
                if (runWfNodeDTO.getIsCanRefuse() != null &&runWfNodeDTO.getIsCanRefuse() == 1 && actions.stream().filter(o -> o.getId().equals("refuse")).findFirst().isPresent()) {// 拒绝
                    if (StringUtils.isNotEmpty(runWfNodeDTO.getRefuseAlias())) {
                        Action action = handleAliasAction(actions, "refuse", runWfNodeDTO.getRefuseAlias());
                        handleActions.add(action);
                    } else {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("refuse")).findFirst().get());
                    }
                }
                if (runWfNodeDTO.getIsCanDeal() != null && runWfNodeDTO.getIsCanDeal() == 1 && actions.stream().filter(o -> o.getId().equals("deal")).findFirst().isPresent()) {// 办理
                    if (StringUtils.isNotEmpty(runWfNodeDTO.getDealAlias())) {
                        Action action = handleAliasAction(actions, "deal", runWfNodeDTO.getDealAlias());
                        handleActions.add(action);
                    } else {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("deal")).findFirst().get());
                    }
                }
                if (runWfNodeDTO.getIsCanReturn() != null && runWfNodeDTO.getIsCanReturn() == 1 && actions.stream().filter(o -> o.getId().equals("return")).findFirst().isPresent()) {// 退回
                    if (StringUtils.isNotEmpty(runWfNodeDTO.getReturnAlias())) {
                        Action action = handleAliasAction(actions, "return", runWfNodeDTO.getReturnAlias());
                        handleActions.add(action);
                    } else {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("return")).findFirst().get());
                    }
                }
                if (runWfNodeDTO.getIsRepulse() != null && runWfNodeDTO.getIsRepulse() == 1 && actions.stream().filter(o -> o.getId().equals("is_repulse")).findFirst().isPresent()) {// 打回
                    if (StringUtils.isNotEmpty(runWfNodeDTO.getRepulseAlias())) {
                        Action action = handleAliasAction(actions, "is_repulse", runWfNodeDTO.getRepulseAlias());
                        handleActions.add(action);
                    } else {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("is_repulse")).findFirst().get());
                    }
                }
                if (actions.stream().filter(o -> o.getId().equals("restart")).findFirst().isPresent() && runWfNodeDTO.getCurNodeId().equals(runWfNodeDTO.getBeginNodeId())) {// 重新发起
                    // 重新发起，没有别名，只要是回到发起节点，就可以重新发起
                    handleActions.add(actions.stream().filter(o -> o.getId().equals("restart")).findFirst().get());
                }
            }

            // 撤回：下一流转节点未操作，上一流转节点才可以操作（下一节点是最新节点+当前节点办理人是当前人+并且有权限）
            if (actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().isPresent()) {
                if (type == 1 && runWfNodeDTO.getIsCurMark() != null && runWfNodeDTO.getIsCurMark() == 1) {
                    // 确认当前节点是最新节点，取上一节点。上一节点有撤回权限，当前人有撤回权限，当前人上一节点办理人
                    RunWfNodeDTO preRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getPreRunNodeId());
                    if (preRunWfNodeDTO != null) {
                        if (preRunWfNodeDTO.getIsCanWithdraw() != null && preRunWfNodeDTO.getIsCanWithdraw() == 1) {
                            List<Long> preRunNodeUserIdList = runWfMapper.getWfRunNodeUserIds(preRunWfNodeDTO.getId(), preRunWfNodeDTO.getId());
                            if (preRunNodeUserIdList.contains(currentUser.getId())) {
                                if (StringUtils.isNotEmpty(preRunWfNodeDTO.getWithdrawAlias())) {
                                    Action action = handleAliasAction(actions, "withdraw", runWfNodeDTO.getWithdrawAlias());
                                    handleActions.add(action);
                                } else {
                                    handleActions.add(actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().get());
                                }
                            }
                        }
                    }
                } else if (type == 2 && runWfNodeDTO.getNextRunNodeId() != null && runWfNodeDTO.getIsCanWithdraw() != null && runWfNodeDTO.getIsCanWithdraw() == 1 && runNodeUserIdList.contains(currentUser.getId())) {
                    // 查询下一执行节点数据
                    RunWfNodeDTO nextRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getNextRunNodeId());
                    if (nextRunWfNodeDTO != null && nextRunWfNodeDTO.getIsCurMark() != null && nextRunWfNodeDTO.getIsCurMark() == 1) {
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getWithdrawAlias())) {
                            Action action = handleAliasAction(actions, "withdraw", runWfNodeDTO.getWithdrawAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().get());
                        }
                    }
                }
            }
            // 撤销：只有开始节点发起人+代办人才能撤销
            if (actions.stream().filter(o -> o.getId().equals("recell")).findFirst().isPresent() && (type == 1 || (type == 2 && runNodeId.equals(runWfNodeDTO.getBeginRunNodeId())))) {
                // 找到开始节点，判断开始节点是否有权限。
                RunWfNodeDTO startRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getBeginRunNodeId());
                // 如果是主表查询，则只要判断当前人是开始节点的执行办理人或者代办人即可；如果是子表查询，要先确定当前执行节点是开始执行节点，再判断当前人是开始节点的执行办理人或者代办人即可
                if (startRunWfNodeDTO.getIsCanRevoke() != null && startRunWfNodeDTO.getIsCanRevoke() == 1) {
                    List<Long> startNodeUserIdList = runWfMapper.getWfRunNodeUserIds(runWfNodeDTO.getId(), runWfNodeDTO.getBeginRunNodeId());
                    if (startNodeUserIdList.contains(currentUser.getId())) {
                        if (StringUtils.isNotEmpty(startRunWfNodeDTO.getRevokeAlias())) {
                            Action action = handleAliasAction(actions, "recell", runWfNodeDTO.getRevokeAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("recell")).findFirst().get());
                        }
                    }
                }
            }
            //作废按钮：有权限的任意节点，都可以作废。当前工作流的每个节点的办理人才有权限
            if (actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().isPresent()) {// 作废
                if (type == 1) {
                    // 查询所有有作废权限的节点的（执行节点办理人+代办人）。tip：作废是作废整个工作流，不是针对一个节点
                    List<Long> invalidUserIds = runWfMapper.getWfRunInvalidUserIds(runWfNodeDTO.getRunWfId(), runWfNodeDTO.getWfId());
                    if (invalidUserIds.contains(currentUser.getId())) {
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().get());
                    }
                } else if (type == 2) {
                    if (runWfNodeDTO.getIsCanInvalid() != null && runWfNodeDTO.getIsCanInvalid() == 1 && runNodeUserIdList.contains(currentUser.getId())) {// 作废
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getInvalidAlias())) {
                            Action action = handleAliasAction(actions, "voidpf", runWfNodeDTO.getInvalidAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().get());
                        }
                    }
                }
            }

            List<Action> newList = new ArrayList<>();
            handleActions.stream().filter(distinctByKey(p -> p.getName())).forEach(newList::add);  //filter保留true的值
            return newList;
        }

        return new ArrayList<>();
    }

    @Override
    public List<RWActionDTO> getRunWfActionListByRunNodeIds(Integer type, List<Long> runNodeIds, List<Action> actions, MemberIdentity currentUser) {
        if (runNodeIds == null || actions == null || actions.size() == 0) {
            return new ArrayList<>();
        }
        // 2.查询工作流指定节点数据
        List<RunWfNodeDTO> runWfNodeDTOs = runWfMapper.selectRunNodeDetailDTO(runNodeIds);
        // 撤回操作获取上一节点信息
        List<RunWfNodeDTO> currMarkNodeDTOs = runWfNodeDTOs.stream().filter(dto -> dto.getIsCurMark() != null && dto.getIsCurMark() == 1 && type == 1).collect(Collectors.toList());
        List<Long> currMarkNodeIds = currMarkNodeDTOs.stream().map(dto -> dto.getPreRunNodeId()).collect(Collectors.toList());
        List<RunWfNodeDTO> preWfNodeDTOs = new ArrayList<>();
        if (currMarkNodeIds.size() > 0) {
            preWfNodeDTOs = runWfMapper.selectRunNodeDetailDTO(currMarkNodeIds);
        }
        // 撤回操作获取上一节点信息
        List<RunWfNodeDTO> nextNodeDTOs = runWfNodeDTOs.stream().filter(dto -> dto.getNextRunNodeId() != null && dto.getIsCanWithdraw() != null && dto.getIsCanWithdraw() == 1 && type == 2).collect(Collectors.toList());
        List<Long> nextNodeIds = nextNodeDTOs.stream().map(dto -> dto.getNextRunNodeId()).collect(Collectors.toList());
        List<RunWfNodeDTO> nextWfNodeDTOs = new ArrayList<>();
        if (nextNodeIds.size() > 0) {
            nextWfNodeDTOs = runWfMapper.selectRunNodeDetailDTO(nextNodeIds);
        }
        // 撤销
        List<RunWfNodeDTO> startNodeDTOs = runWfNodeDTOs.stream().filter(dto -> (type == 1 || (type == 2 && dto.getId().equals(dto.getBeginRunNodeId())))).collect(Collectors.toList());
        List<Long> startNodeIds = startNodeDTOs.stream().map(dto -> dto.getBeginRunNodeId()).collect(Collectors.toList());
        List<RunWfNodeDTO> startWfNodeDTOs = new ArrayList<>();
        if (startNodeIds.size() > 0) {
            startWfNodeDTOs = runWfMapper.selectRunNodeDetailDTO(startNodeIds);
        }
        // 作废
        List<Long> runWfIds = runWfNodeDTOs.stream().map(dto -> dto.getRunWfId()).distinct().collect(Collectors.toList());
        List<RWCanInvalidDTO> canInvalidDTOS = new ArrayList<>();
        if (runWfIds.size() > 0) {
            canInvalidDTOS = runWfMapper.getWfRunListInvalidUserIds(runWfIds);
        }

        List<RWActionDTO> allActions = new ArrayList<>();
        for (RunWfNodeDTO runWfNodeDTO : runWfNodeDTOs) {
            if (runWfNodeDTO.getWfStatus() != null && runWfNodeDTO.getWfStatus() != 3) {// 工作流未结束
                List<Long> runNodeUserIdList = new ArrayList<>();
                if (runWfNodeDTO.getCombinedUserIds() != null) {
                    runNodeUserIdList = Arrays.stream(runWfNodeDTO.getCombinedUserIds().split(","))
                            .map(String::trim)
                            .map(Long::parseLong)
                            .collect(Collectors.toList());
                }
                List<Action> handleActions = new ArrayList<>();
                if (runWfNodeDTO.getIsCurMark() == 1 && runWfNodeDTO.getCurStatus() != 3 && runNodeUserIdList.contains(currentUser.getId())) {
                    //当前登录人和当前节点的审批人是同一个人，并且在处理中的才能审批
                    if (runWfNodeDTO.getIsCanAgree() != null && runWfNodeDTO.getIsCanAgree() == 1 && actions.stream().filter(o -> o.getId().equals("agree")).findFirst().isPresent()) {// 同意
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getAgreeAlias())) {
                            Action action = handleAliasAction(actions, "agree", runWfNodeDTO.getAgreeAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("agree")).findFirst().get());
                        }
                    }
                    if (runWfNodeDTO.getIsCanRefuse() != null && runWfNodeDTO.getIsCanRefuse() == 1 && actions.stream().filter(o -> o.getId().equals("refuse")).findFirst().isPresent()) {// 拒绝
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getRefuseAlias())) {
                            Action action = handleAliasAction(actions, "refuse", runWfNodeDTO.getRefuseAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("refuse")).findFirst().get());
                        }
                    }
                    if (runWfNodeDTO.getIsCanDeal() != null && runWfNodeDTO.getIsCanDeal() == 1 && actions.stream().filter(o -> o.getId().equals("deal")).findFirst().isPresent()) {// 办理
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getDealAlias())) {
                            Action action = handleAliasAction(actions, "deal", runWfNodeDTO.getDealAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("deal")).findFirst().get());
                        }
                    }
                    if (runWfNodeDTO.getIsCanReturn() != null && runWfNodeDTO.getIsCanReturn() == 1 && actions.stream().filter(o -> o.getId().equals("return")).findFirst().isPresent()) {// 退回
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getReturnAlias())) {
                            Action action = handleAliasAction(actions, "return", runWfNodeDTO.getReturnAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("return")).findFirst().get());
                        }
                    }
                    if (runWfNodeDTO.getIsRepulse() != null && runWfNodeDTO.getIsRepulse() == 1 && actions.stream().filter(o -> o.getId().equals("is_repulse")).findFirst().isPresent()) {// 打回
                        if (StringUtils.isNotEmpty(runWfNodeDTO.getRepulseAlias())) {
                            Action action = handleAliasAction(actions, "is_repulse", runWfNodeDTO.getRepulseAlias());
                            handleActions.add(action);
                        } else {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("is_repulse")).findFirst().get());
                        }
                    }
                    if (actions.stream().filter(o -> o.getId().equals("restart")).findFirst().isPresent() && runWfNodeDTO.getCurNodeId().equals(runWfNodeDTO.getBeginNodeId())) {// 重新发起
                        // 重新发起，没有别名，只要是回到发起节点，就可以重新发起
                        handleActions.add(actions.stream().filter(o -> o.getId().equals("restart")).findFirst().get());
                    }
                }

                // 撤回：下一流转节点未操作，上一流转节点才可以操作（下一节点是最新节点+当前节点办理人是当前人+并且有权限）
                if (actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().isPresent()) {
                    if (type == 1 && runWfNodeDTO.getIsCurMark() != null && runWfNodeDTO.getIsCurMark() == 1) {
                        // 确认当前节点是最新节点，取上一节点。上一节点有撤回权限，当前人有撤回权限，当前人上一节点办理人
//                        RunWfNodeDTO preRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getPreRunNodeId());
                        RunWfNodeDTO preRunWfNodeDTO = preWfNodeDTOs.stream().filter(dto -> dto.getId().equals(runWfNodeDTO.getPreRunNodeId())).findFirst().orElse(null);
                        if (preRunWfNodeDTO != null) {
                            if (preRunWfNodeDTO.getIsCanWithdraw() != null && preRunWfNodeDTO.getIsCanWithdraw() == 1) {
//                                List<Long> preRunNodeUserIdList = runWfMapper.getWfRunNodeUserIds(preRunWfNodeDTO.getId(), preRunWfNodeDTO.getId());
                                List<Long> preRunNodeUserIdList = new ArrayList<>();
                                if (preRunWfNodeDTO.getCombinedUserIds() != null) {
                                    preRunNodeUserIdList = Arrays.stream(preRunWfNodeDTO.getCombinedUserIds().split(","))
                                            .map(String::trim)
                                            .map(Long::parseLong)
                                            .collect(Collectors.toList());
                                }
                                if (preRunNodeUserIdList.contains(currentUser.getId())) {
                                    if (StringUtils.isNotEmpty(preRunWfNodeDTO.getWithdrawAlias())) {
                                        Action action = handleAliasAction(actions, "withdraw", runWfNodeDTO.getWithdrawAlias());
                                        handleActions.add(action);
                                    } else {
                                        handleActions.add(actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().get());
                                    }
                                }
                            }
                        }
                    } else if (type == 2 && runWfNodeDTO.getNextRunNodeId() != null && runWfNodeDTO.getIsCanWithdraw() != null && runWfNodeDTO.getIsCanWithdraw() == 1 && runNodeUserIdList.contains(currentUser.getId())) {
                        // 查询下一执行节点数据
//                        RunWfNodeDTO nextRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getNextRunNodeId());
                        RunWfNodeDTO nextRunWfNodeDTO = nextWfNodeDTOs.stream().filter(dto -> dto.getId().equals(runWfNodeDTO.getNextRunNodeId())).findFirst().orElse(null);
                        if (nextRunWfNodeDTO != null && nextRunWfNodeDTO.getIsCurMark() != null && nextRunWfNodeDTO.getIsCurMark() == 1) {
                            if (StringUtils.isNotEmpty(runWfNodeDTO.getWithdrawAlias())) {
                                Action action = handleAliasAction(actions, "withdraw", runWfNodeDTO.getWithdrawAlias());
                                handleActions.add(action);
                            } else {
                                handleActions.add(actions.stream().filter(o -> o.getId().equals("withdraw")).findFirst().get());
                            }
                        }
                    }
                }
                // 撤销：只有开始节点发起人+代办人才能撤销
                if (actions.stream().filter(o -> o.getId().equals("recell")).findFirst().isPresent() && (type == 1 || (type == 2 && runWfNodeDTO.getId().equals(runWfNodeDTO.getBeginRunNodeId())))) {
                    // 找到开始节点，判断开始节点是否有权限。
//                    RunWfNodeDTO startRunWfNodeDTO = runWfMapper.selectRunNodeInfoDTO(runWfNodeDTO.getBeginRunNodeId());
                    RunWfNodeDTO startRunWfNodeDTO = startWfNodeDTOs.stream().filter(dto -> dto.getId().equals(runWfNodeDTO.getBeginRunNodeId())).findFirst().orElse(null);

                    // 如果是主表查询，则只要判断当前人是开始节点的执行办理人或者代办人即可；如果是子表查询，要先确定当前执行节点是开始执行节点，再判断当前人是开始节点的执行办理人或者代办人即可
                    if (startRunWfNodeDTO.getIsCanRevoke() != null && startRunWfNodeDTO.getIsCanRevoke() == 1) {
//                        List<Long> startNodeUserIdList = runWfMapper.getWfRunNodeUserIds(runWfNodeDTO.getRunWfId(), runWfNodeDTO.getBeginRunNodeId());
                        List<Long> startNodeUserIdList = new ArrayList<>();
                        if (startRunWfNodeDTO.getCombinedUserIds() != null) {
                            startNodeUserIdList = Arrays.stream(startRunWfNodeDTO.getCombinedUserIds().split(","))
                                    .map(String::trim)
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());
                        }
                        if (startNodeUserIdList.contains(currentUser.getId())) {
                            if (StringUtils.isNotEmpty(startRunWfNodeDTO.getRevokeAlias())) {
                                Action action = handleAliasAction(actions, "recell", runWfNodeDTO.getRevokeAlias());
                                handleActions.add(action);
                            } else {
                                handleActions.add(actions.stream().filter(o -> o.getId().equals("recell")).findFirst().get());
                            }
                        }
                    }
                }
                //作废按钮：有权限的任意节点，都可以作废。当前工作流的每个节点的办理人才有权限
                if (actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().isPresent()) {// 作废
                    if (type == 1) {
                        // 查询所有有作废权限的节点的（执行节点办理人+代办人）。tip：作废是作废整个工作流，不是针对一个节点
//                        List<Long> invalidUserIds = runWfMapper.getWfRunInvalidUserIds(runWfNodeDTO.getRunWfId(), runWfNodeDTO.getWfId());
                        List<RWCanInvalidDTO> filterCanInvalidDTOs = canInvalidDTOS.stream().filter(dto -> dto.getRunWfId().equals(runWfNodeDTO.getRunWfId())).collect(Collectors.toList());
                        List<Long> invalidUserIds = filterCanInvalidDTOs.stream().map(dto -> dto.getUserId()).collect(Collectors.toList());
                        if (invalidUserIds.contains(currentUser.getId())) {
                            handleActions.add(actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().get());
                        }
                    } else if (type == 2) {
                        if (runWfNodeDTO.getIsCanInvalid() != null && runWfNodeDTO.getIsCanInvalid() == 1 && runNodeUserIdList.contains(currentUser.getId())) {// 作废
                            if (StringUtils.isNotEmpty(runWfNodeDTO.getInvalidAlias())) {
                                Action action = handleAliasAction(actions, "voidpf", runWfNodeDTO.getInvalidAlias());
                                handleActions.add(action);
                            } else {
                                handleActions.add(actions.stream().filter(o -> o.getId().equals("voidpf")).findFirst().get());
                            }
                        }
                    }
                }

                List<Action> handleActionsList = new ArrayList<>();
                handleActions.stream().filter(distinctByKey(p -> p.getName())).forEach(handleActionsList::add);  //filter保留true的值

                RWActionDTO rwActionDTO = new RWActionDTO();
                rwActionDTO.setRunNodeId(runWfNodeDTO.getId());
                rwActionDTO.setActions(handleActionsList);
                allActions.add(rwActionDTO);
            }
        }

        return allActions;
    }

    private Action handleAliasAction(List<Action> actions, String actionName, String alias) {
        Action action = actions.stream().filter(o -> o.getId().equals(actionName)).collect(Collectors.toList()).get(0);
        return new Action(action.getId(), alias);
    }

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        //putIfAbsent方法添加键值对，如果map集合中没有该key对应的值，则直接添加，并返回null，如果已经存在对应的值，则依旧为原来的值。
        //如果返回null表示添加数据成功(不重复)，不重复(null==null :TRUE)
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
