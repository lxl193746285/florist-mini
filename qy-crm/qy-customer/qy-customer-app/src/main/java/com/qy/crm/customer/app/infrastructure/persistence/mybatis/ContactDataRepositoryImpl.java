package com.qy.crm.customer.app.infrastructure.persistence.mybatis;

import com.qy.crm.customer.app.application.query.ContactQuery;
import com.qy.crm.customer.app.infrastructure.persistence.ContactDataRepository;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.dataobject.ContactRelationDO;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.ContactMapper;
import com.qy.crm.customer.app.infrastructure.persistence.mybatis.mapper.ContactRelationMapper;
import com.qy.rest.constant.LogicDeleteConstant;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.qy.security.session.EmployeeIdentity;
import com.qy.uims.security.permission.MultiOrganizationFilterQuery;
import com.qy.uims.security.permission.OrganizationFilterQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 联系人数据资源库实现
 *
 * @author legendjw
 */
@Repository
public class ContactDataRepositoryImpl implements ContactDataRepository {
    private ContactMapper contactMapper;
    private ContactRelationMapper contactRelationMapper;

    public ContactDataRepositoryImpl(ContactMapper contactMapper, ContactRelationMapper contactRelationMapper) {
        this.contactMapper = contactMapper;
        this.contactRelationMapper = contactRelationMapper;
    }

    @Override
    public Page<ContactDO> findByQuery(ContactQuery query, MultiOrganizationFilterQuery filterQuery) {
        LambdaQueryWrapper<ContactDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ContactDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .orderByDesc(ContactDO::getCreateTime);
        if (filterQuery == null) {
            queryWrapper.eq(ContactDO::getId, 0);
        }
        else {
            queryWrapper.in(ContactDO::getOrganizationId, filterQuery.getOrganizationIds());
            queryWrapper.and(i -> {
                for (OrganizationFilterQuery permissionFilterQuery : filterQuery.getPermissionFilterQueries()) {
                    i.or(j ->
                            j.eq(ContactDO::getOrganizationId, permissionFilterQuery.getOrganizationId())
                                    .eq(permissionFilterQuery.getEmployeeId() != null, ContactDO::getCreatorId, permissionFilterQuery.getEmployeeId())
                    );
                }
            });
        }

        if (StringUtils.isNotBlank(query.getKeywords())) {
            queryWrapper.and(i -> i.like(ContactDO::getName, query.getKeywords()).or().like(ContactDO::getNamePinyin, query.getKeywords()));
        }
        if (query.getOrganizationId() != null) {
            queryWrapper.eq(ContactDO::getOrganizationId, query.getOrganizationId());
        }
        if (query.getRelatedModuleId() != null && query.getRelatedDataId() != null) {
            List<Long> relatedContactIds = getRelatedContactIds(query.getRelatedModuleId(), query.getRelatedDataId());
            if (relatedContactIds.isEmpty()) {
                queryWrapper.eq(ContactDO::getId, 0L);
            }
            else {
                queryWrapper.in(ContactDO::getId, relatedContactIds);
            }
        }

        IPage<ContactDO> iPage = contactMapper.selectPage(new PageDTO<>(query.getPage(), query.getPerPage()), queryWrapper);
        return new PageImpl<>(new PageRequest(query.getPage(), query.getPerPage()), iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<ContactDO> findByIds(List<Long> ids) {
        return contactMapper.selectBatchIds(ids);
    }

    @Override
    public ContactDO findById(Long id) {
        LambdaQueryWrapper<ContactDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(ContactDO::getId, id)
                .eq(ContactDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return contactMapper.selectOne(queryWrapper);
    }

    @Override
    public ContactDO findRelatedSuperAdmin(String relatedModuleId, Long relatedDataId) {
        List<Long> contactIds = getRelatedContactIds(relatedModuleId, relatedDataId);
        if (contactIds.isEmpty()) { return null; }
        LambdaQueryWrapper<ContactDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(ContactDO::getId, contactIds)
                .eq(ContactDO::getIsSuperAdmin, 1)
                .eq(ContactDO::getIsDeleted, LogicDeleteConstant.NOT_DELETED)
                .last("limit 1");
        return contactMapper.selectOne(queryWrapper);
    }

    @Override
    public Long save(ContactDO contactDO) {
        if (contactDO.getId() == null) {
            contactMapper.insert(contactDO);
        }
        else {
            contactMapper.updateById(contactDO);
        }
        return contactDO.getId();
    }

    @Override
    public void remove(Long id, EmployeeIdentity identity) {
        ContactDO contactDO = findById(id);
        contactDO.setIsDeleted((byte) LogicDeleteConstant.DELETED);
        contactDO.setDeletorId(identity != null ? identity.getId() : 0L);
        contactDO.setDeletorName(identity != null ? identity.getName() : "");
        contactDO.setDeleteTime(LocalDateTime.now());
        contactMapper.updateById(contactDO);
    }

    @Override
    public List<Long> getRelatedContactIds(String relatedModuleId, Long relatedDataId) {
        LambdaQueryWrapper<ContactRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(ContactRelationDO::getContactId)
                .eq(ContactRelationDO::getModuleId, relatedModuleId)
                .eq(ContactRelationDO::getDataId, relatedDataId);
        List<ContactRelationDO> relationDOS = contactRelationMapper.selectList(queryWrapper);
        return relationDOS.stream().map(ContactRelationDO::getContactId).collect(Collectors.toList());
    }

    @Override
    public Long saveRelation(String relatedModuleId, Long relatedDataId, Long contactId) {
        ContactRelationDO relationDO = new ContactRelationDO();
        relationDO.setContactId(contactId);
        relationDO.setModuleId(relatedModuleId);
        relationDO.setDataId(relatedDataId);
        relationDO.setCreateTime(LocalDateTime.now());
        contactRelationMapper.insert(relationDO);

        return relationDO.getId();
    }

    @Override
    public int removeRelation(Long contactId) {
        LambdaQueryWrapper<ContactRelationDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContactRelationDO::getContactId, contactId);
        return contactRelationMapper.delete(queryWrapper);
    }

    @Override
    public void setContactIsSuperAdmin(String relatedModuleId, Long relatedDataId, Long contactId) {
        List<Long> contractIds = getRelatedContactIds(relatedModuleId, relatedDataId);
        ContactDO updateContactDO = new ContactDO();
        updateContactDO.setIsSuperAdmin((byte) 0);
        LambdaQueryWrapper<ContactDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ContactDO::getId, contractIds);
        contactMapper.update(updateContactDO, queryWrapper);

        updateContactDO.setIsSuperAdmin((byte) 1);
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ContactDO::getId, contactId);
        contactMapper.update(updateContactDO, queryWrapper);
    }
}