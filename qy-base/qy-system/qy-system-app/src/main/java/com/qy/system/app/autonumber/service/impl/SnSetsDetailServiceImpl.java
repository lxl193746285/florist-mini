package com.qy.system.app.autonumber.service.impl;

import com.qy.security.session.EmployeeIdentity;
import com.qy.system.app.autonumber.dto.SnSetsDetailDTO;
import com.qy.system.app.autonumber.dto.SnSetsDetailFormDTO;
import com.qy.system.app.autonumber.dto.SnSetsDetailQueryDTO;
import com.qy.system.app.autonumber.entity.SnSetsDetailEntity;
import com.qy.system.app.autonumber.mapper.SnSetsDetailMapper;
import com.qy.system.app.autonumber.service.SnSetsDetailService;
import com.qy.system.app.util.RestUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 编号规则设置子表 服务实现类
 *
 * @author ln
 * @since 2022-04-30
 */
@Service
public class SnSetsDetailServiceImpl extends ServiceImpl<SnSetsDetailMapper, SnSetsDetailEntity> implements SnSetsDetailService {
    @Autowired
    private SnSetsDetailMapper snSetsDetailMapper;

    @Override
    public SnSetsDetailEntity createSnSetsDetail(SnSetsDetailFormDTO snSetsDetailFormDTO, EmployeeIdentity currentUser) {
        SnSetsDetailEntity snSetsDetailEntity = new SnSetsDetailEntity();
        BeanUtils.copyProperties(snSetsDetailFormDTO, snSetsDetailEntity);
        snSetsDetailEntity.setIsDeleted(0);
        snSetsDetailEntity.setStatus(1);
        snSetsDetailEntity.setCompanyId(currentUser.getOrganizationId());
        this.save(snSetsDetailEntity);
        return snSetsDetailEntity;
    }

    @Override
    public SnSetsDetailEntity deleteSnSetsDetailByNoId(String noid, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<SnSetsDetailEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SnSetsDetailEntity::getNoid, noid);
//        remove(wrapper);
        List<SnSetsDetailEntity> snSetsDetailEntityList = list(wrapper);
        for (SnSetsDetailEntity snSetsDetailEntity : snSetsDetailEntityList) {
            snSetsDetailEntity.setNoid(noid);
            snSetsDetailEntity.setDeletorId(currentUser.getId());
            snSetsDetailEntity.setDeletorName(currentUser.getName());
            snSetsDetailEntity.setDeleteTime(LocalDateTime.now());
            snSetsDetailEntity.setIsDeleted(1);
            snSetsDetailEntity.setCompanyId(currentUser.getOrganizationId());
            snSetsDetailMapper.deleteBySnSetsId(snSetsDetailEntity);
        }
        return null;
    }

    @Override
    public List<SnSetsDetailDTO> getSnSetsDetails(SnSetsDetailQueryDTO snSetsDetailQueryDTO) {
        LambdaQueryWrapper<SnSetsDetailEntity> snSetsDetailQueryWrapper = RestUtils.getLambdaQueryWrapper();
        snSetsDetailQueryWrapper.eq(null != snSetsDetailQueryDTO.getNoid(), SnSetsDetailEntity::getNoid, snSetsDetailQueryDTO.getNoid())
                .eq(SnSetsDetailEntity::getCompanyId, snSetsDetailQueryDTO.getCompanyId())
                .eq(SnSetsDetailEntity::getIsDeleted, 0)
                .eq(null != snSetsDetailQueryDTO.getRecno(), SnSetsDetailEntity::getRecno, snSetsDetailQueryDTO.getRecno())
                .orderByAsc(SnSetsDetailEntity::getRecno);
        List<SnSetsDetailEntity> list = super.list(snSetsDetailQueryWrapper);
        List<SnSetsDetailDTO> snSetsDetailDTOList = new ArrayList<>();
        for(SnSetsDetailEntity entity : list) {
            SnSetsDetailDTO dto = new SnSetsDetailDTO();
            BeanUtils.copyProperties(entity, dto);
            snSetsDetailDTOList.add(dto);
        }
        return snSetsDetailDTOList;
    }

    @Override
    public SnSetsDetailEntity updateSnSetsDetail(SnSetsDetailFormDTO snSetsDetailFormDTO, EmployeeIdentity currentUser) {
        LambdaQueryWrapper<SnSetsDetailEntity> lambdaQueryWrapper = RestUtils.getLambdaQueryWrapper();
        lambdaQueryWrapper.eq(SnSetsDetailEntity::getCompanyId, currentUser.getOrganizationId());
        lambdaQueryWrapper.eq(SnSetsDetailEntity::getNoid, snSetsDetailFormDTO.getNoid());
        lambdaQueryWrapper.eq(SnSetsDetailEntity::getRecno, snSetsDetailFormDTO.getRecno());

        SnSetsDetailEntity snSetsDetailEntity = new SnSetsDetailEntity();
        BeanUtils.copyProperties(snSetsDetailFormDTO, snSetsDetailEntity);
        super.update(snSetsDetailEntity, lambdaQueryWrapper);
        return snSetsDetailEntity;
    }


}
