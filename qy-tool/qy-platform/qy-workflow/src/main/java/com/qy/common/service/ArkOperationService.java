package com.qy.common.service;

import com.qy.common.dto.ArkBaseDTO;
import com.qy.common.enums.IArkCreateEntity;
import com.qy.common.enums.IArkEntity;
import com.qy.common.enums.IArkPermissionAction;
import com.qy.security.permission.action.Action;
import com.qy.security.session.EmployeeIdentity;
import com.qy.security.session.OrganizationSessionContext;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArkOperationService {
    @Autowired
    private OrganizationSessionContext organizationSessionContext;
    /**
     * 列表返回操作按钮
     *
     * @param iEnumActions
     * @param dtoList
     * @param currentUser
     */
    public void initOperations(IArkPermissionAction[] iEnumActions, List<? extends ArkBaseDTO> dtoList, EmployeeIdentity currentUser) {
        //权限
        List<Action> arkOperationList = new ArrayList<>();
        for (IArkPermissionAction iEnumAction : iEnumActions) {
            //验证是否有操作权限
            if (organizationSessionContext.getEmployee(currentUser.getOrganizationId()).hasPermission(iEnumAction.getPermissionAction().getPermission())) {
                arkOperationList.add(iEnumAction.getPermissionAction().toAction());
            }

        }

        dtoList.forEach(p -> {
            p.setActions(arkOperationList);
        });
    }

    /**
     * 获取列表权限
     *
     * @param iEnumActions
     * @param currentUser
     * @return
     */
    public Map<String, Action> getEnumToAction(IArkPermissionAction[] iEnumActions, EmployeeIdentity currentUser) {
        //权限
        Map<String, Action> enumIdToAction = Maps.newConcurrentMap();
        for (IArkPermissionAction iEnumAction : iEnumActions) {

            //注意要过滤掉 页面顶部一排按钮权限
            if (null == iEnumAction.getPermissionAction().getListAction() || false == iEnumAction.getPermissionAction().getListAction()) {
                continue;
            }

            //验证是否有操作权限
            if (organizationSessionContext.getEmployee(currentUser.getOrganizationId()).hasPermission(iEnumAction.getPermissionAction().getPermission())) {
                enumIdToAction.put(iEnumAction.getPermissionAction().getId(), iEnumAction.getPermissionAction().toAction());
            }
        }

        return enumIdToAction;
    }

    /**
     * 统一创建属性赋值
     *
     * @param entity
     * @param currentUser
     */
    public void initCreateEntity(IArkEntity entity, EmployeeIdentity currentUser) {
        entity.setCreatorId(currentUser.getOrganizationId());
        entity.setCreatorName(currentUser.getName());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
    }

    public void initCreateEntity(IArkCreateEntity entity, EmployeeIdentity currentUser) {
        entity.setCreatorId(currentUser.getOrganizationId());
        entity.setCreatorName(currentUser.getName());
        entity.setCreateTime(LocalDateTime.now());
    }

    /**
     * 统一更新属性赋值
     *
     * @param entity
     * @param currentUser
     */
    public void initUpdateEntity(IArkEntity entity, EmployeeIdentity currentUser) {
        entity.setUpdatorId(currentUser.getOrganizationId());
        entity.setUpdatorName(currentUser.getName());
        entity.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 统一删除属性赋值
     *
     * @param entity
     * @param currentUser
     */
    public void initDeleteEntity(IArkEntity entity, EmployeeIdentity currentUser) {
        entity.setDeletorId(currentUser.getOrganizationId());
        entity.setDeletorName(currentUser.getName());
        entity.setDeleteTime(LocalDateTime.now());
        entity.setIsDeleted((byte) 1);
    }

//    public void initAuditEntity(IArkAudit entity, MemberIdentity currentUser) {
////        entity.setAuditRemark(currentUser.getEmployeeIdentityId());
////        entity.setAuditNoPassReason(currentUser.getRealname());
////        entity.setAuditNoPassReason(LocalDateTime.now());
//    }
}
