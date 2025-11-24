package com.qy.wf.querytable.service.impl;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.common.enums.ArkOperation;
import com.qy.common.service.ArkCodeTableService;
import com.qy.common.service.ArkOperationService;
import com.qy.security.permission.action.Action;
import com.qy.security.session.EmployeeIdentity;
import com.qy.utils.RestUtils;
import com.qy.wf.querytable.dto.*;
import com.qy.wf.querytable.entity.QueryTableEntity;
import com.qy.wf.querytable.enums.QueryTableAction;
import com.qy.wf.querytable.mapper.QueryTableMapper;
import com.qy.wf.querytable.service.QueryTableService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 工作流_设计_查询表单 服务实现类
 *
 * @author hh
 * @since 2022-11-19
 */
@Service
public class QueryTableServiceImpl extends ServiceImpl<QueryTableMapper, QueryTableEntity> implements QueryTableService {
    @Autowired
    private QueryTableMapper queryTableMapper;
    @Autowired
    private ArkCodeTableService arkCodeTableService;
    @Autowired
    private ArkOperationService operationService;

    @Autowired
    private CodeTableClient codeTableClient;

    @Override
    public  IPage<QueryTableEntity> getQueryTables(IPage iPage,QueryTableQueryDTO queryTableQueryDTO, EmployeeIdentity currentUser)
    {
       return  getQueryCustomTable(iPage, 1,queryTableQueryDTO, currentUser);
    }

    @Override
    public IPage<QueryTableEntity> getQueryCustomTable(IPage iPage,Integer type, QueryTableQueryDTO queryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<QueryTableEntity> queryWrapper = RestUtils.getLambdaQueryWrapper();
        LocalDate startDate;
        LocalDate endDate;
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getStartCreateDate())) {
            startDate = LocalDate.parse(queryDTO.getStartCreateDate());
            queryWrapper.ge(null != startDate, QueryTableEntity::getCreateTime, startDate);
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(queryDTO.getEndCreateDate())) {
            endDate = LocalDate.parse(queryDTO.getEndCreateDate());
            if (null != endDate) {
                queryWrapper.le(QueryTableEntity::getCreateTime, endDate.atTime(23, 59, 59));
            }
        }

        queryWrapper.eq(queryDTO.getCompanyId()!=null,QueryTableEntity::getCompanyId,queryDTO.getCompanyId());
        queryWrapper.eq(queryDTO.getWfId()!=null,QueryTableEntity::getWfId,queryDTO.getWfId());
        queryWrapper.eq(queryDTO.getStyle()!=null,QueryTableEntity::getStyle,queryDTO.getStyle());
        queryWrapper.eq(queryDTO.getColumnCount()!=null,QueryTableEntity::getColumnCount,queryDTO.getColumnCount());
        queryWrapper.eq(queryDTO.getStatus()!=null,QueryTableEntity::getStatus,queryDTO.getStatus());
        queryWrapper.eq(queryDTO.getTableType()!=null,QueryTableEntity::getTableType,queryDTO.getTableType());
        queryWrapper.orderByDesc(QueryTableEntity::getCreateTime);
        return super.page(iPage, queryWrapper);
    }

    @Override
    public List<QueryTableEntity> getQueryTables(QueryTableQueryDTO queryTableQueryDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<QueryTableEntity> queryTableQueryWrapper = RestUtils.getLambdaQueryWrapper();
        return super.list(queryTableQueryWrapper);
    }

    @Override
    public QueryTableEntity getQueryTable(Long id, EmployeeIdentity currentUser) {
        QueryTableEntity queryTableEntity = this.getOne(RestUtils.getQueryWrapperById(id));

        if (queryTableEntity == null) {
            throw new RuntimeException("未找到 工作流_设计_查询表单");
        }
        return queryTableEntity;
    }

    @Override
    public QueryTableEntity createQueryTable(QueryTableFormDTO queryTableFormDTO, EmployeeIdentity currentUser) {
        QueryTableEntity queryTableEntity = new QueryTableEntity();
        BeanUtils.copyProperties(queryTableFormDTO, queryTableEntity);
        queryTableEntity.setCreateTime(LocalDateTime.now());
        queryTableEntity.setCreatorId(currentUser.getId());
        queryTableEntity.setCreatorName(currentUser.getName());
        queryTableEntity.setIsDeleted(0);

        this.save(queryTableEntity);
        return queryTableEntity;
    }

    @Override
    public QueryTableEntity updateQueryTable(Long id, QueryTableFormDTO queryTableFormDTO, EmployeeIdentity currentUser) {
        QueryTableEntity queryTableEntity = getById(id);
        BeanUtils.copyProperties(queryTableFormDTO, queryTableEntity);
        queryTableEntity.setUpdateTime(LocalDateTime.now());
        queryTableEntity.setUpdatorId(currentUser.getId());
        queryTableEntity.setUpdatorName(currentUser.getName());

        this.updateById(queryTableEntity);
        return queryTableEntity;
    }

    @Override
    public QueryTableEntity deleteQueryTable(Long id, EmployeeIdentity currentUser) {
        QueryTableEntity queryTableEntity = getQueryTable(id, currentUser);
        queryTableEntity.setDeleteTime(LocalDateTime.now());
        queryTableEntity.setDeletorId(currentUser.getId());
        queryTableEntity.setDeletorName(currentUser.getName());

        queryTableEntity.setIsDeleted(1);
        this.updateById(queryTableEntity);
        return queryTableEntity;
    }

    @Override
    public QueryTableDTO mapperToDTO(QueryTableEntity queryTableEntity, EmployeeIdentity currentUser) {
        QueryTableDTO queryTableDTO = new QueryTableDTO();
        BeanUtils.copyProperties(queryTableEntity, queryTableDTO);
        queryTableDTO.setStatusName(arkCodeTableService.getNameBySystem("common_status", queryTableDTO.getStatus()));
        queryTableDTO.setStyleName(arkCodeTableService.getNameBySystem("style_type", queryTableDTO.getStyle()));
        queryTableDTO.setDataRuleName(arkCodeTableService.getNameBySystem("query_table_data_rule", queryTableDTO.getDataRule()));
        queryTableDTO.setTableTypeName(arkCodeTableService.getNameBySystem("wf_node_table", queryTableDTO.getTableType()));
        if (currentUser != null) {
            //权限按钮
            List<Action> actions = new ArrayList<>();
//            if (currentUser.hasPermission(QueryTableAction.VIEW.getPermission())) {
//                actions.add(ArkOperation.fromIEnumAction(QueryTableAction.VIEW));
//            }
//            if (currentUser.hasPermission(QueryTableAction.EDIT.getPermission())) {
//                actions.add(ArkOperation.fromIEnumAction(QueryTableAction.EDIT));
//            }
//            if (currentUser.hasPermission(QueryTableAction.DELETE.getPermissionAction())) {
//                actions.add(ArkOperation.fromIEnumAction(QueryTableAction.DELETE));
//            }
            actions.add(ArkOperation.fromIEnumAction(QueryTableAction.VIEW));
            actions.add(ArkOperation.fromIEnumAction(QueryTableAction.EDIT));
            actions.add(ArkOperation.fromIEnumAction(QueryTableAction.DELETE));
            queryTableDTO.setActions(actions);
        }
        return queryTableDTO;
    }

    @Override
    public List<QueryTableDTO> mapperToDTO(List<QueryTableEntity> queryTableEntityList, EmployeeIdentity currentUser) {
        List<QueryTableDTO> queryTableDTOs = new ArrayList<>();
        for (QueryTableEntity queryTableEntity : queryTableEntityList) {
            QueryTableDTO queryTableDTO = mapperToDTO(queryTableEntity, currentUser);
            queryTableDTOs.add(queryTableDTO);
        }


        return queryTableDTOs;
    }
}
