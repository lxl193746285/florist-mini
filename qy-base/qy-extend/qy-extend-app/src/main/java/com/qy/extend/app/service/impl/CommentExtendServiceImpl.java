package com.qy.extend.app.service.impl;

import com.qy.extend.app.dto.CommentExtendDTO;
import com.qy.extend.app.dto.CommentExtendFormDTO;
import com.qy.extend.app.dto.CommentExtendQueryDTO;
import com.qy.extend.app.entity.CommentExtendEntity;
import com.qy.extend.app.mapper.CommentExtendMapper;
import com.qy.extend.app.service.CommentExtendService;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentExtendServiceImpl extends ServiceImpl<CommentExtendMapper, CommentExtendEntity> implements CommentExtendService {
    @Autowired
    private CommentExtendMapper commentExtendMapper;

    @Override
    public Page<CommentExtendDTO> getCommentExtends(CommentExtendQueryDTO queryDTO) {
        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentExtendEntity::getIsDeleted, 0)
                .eq(queryDTO.getCompanyId() != null, CommentExtendEntity::getCompanyId, queryDTO.getCompanyId())
                .eq(queryDTO.getCategory() != null, CommentExtendEntity::getCategory, queryDTO.getCategory())
                .eq(queryDTO.getSystemType() != null, CommentExtendEntity::getSystemType, queryDTO.getSystemType())
                .eq(queryDTO.getLinkId() != null, CommentExtendEntity::getLinkId, queryDTO.getLinkId())
                .eq(!StringUtils.isEmpty(queryDTO.getType()), CommentExtendEntity::getType, queryDTO.getType());
        IPage<CommentExtendDTO> commentList = commentExtendMapper.selectByLinkDatas(new PageDTO<>(queryDTO.getPage(), queryDTO.getPerPage()), queryWrapper);

        return new PageImpl<>(new PageRequest(queryDTO.getPage(), queryDTO.getPerPage()), commentList.getTotal(), commentList.getRecords());
    }

    @Override
    public CommentExtendEntity getCommentExtend(Long linkId, String type, Long memberId) {
        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentExtendEntity::getIsDeleted, 0)
                .eq(linkId != null, CommentExtendEntity::getLinkId, linkId)
                .eq(memberId != null, CommentExtendEntity::getMemberId, memberId)
                .eq(!StringUtils.isEmpty(type), CommentExtendEntity::getType, type);
        CommentExtendEntity commentExtendEntity = this.getOne(queryWrapper);
        return commentExtendEntity;
    }



//    @Override
//    public IPage<CommentExtendEntity> getArkSystemCommentExtends(IPage iPage, CommentExtendQueryDTO queryDTO) {
//        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(CommentExtendEntity::getIsDeleted,0);
//        return super.page(iPage, queryWrapper);
//    }

    @Override
    public List<CommentExtendEntity> getArkSystemCommentExtends(CommentExtendQueryDTO queryDTO) {
        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentExtendEntity::getIsDeleted, 0);
        return super.list(queryWrapper);
    }

    @Override
    public CommentExtendEntity getArkSystemCommentExtend(Long id) {
        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentExtendEntity::getIsDeleted, 0);
        queryWrapper.eq(CommentExtendEntity::getId, id);
        CommentExtendEntity CommentExtendEntity = this.getOne(queryWrapper);

        if (CommentExtendEntity == null) {
            throw new RuntimeException("未找到 评论扩展");
        }

        return CommentExtendEntity;
    }

    @Override
    public CommentExtendEntity createArkSystemCommentExtend(CommentExtendFormDTO formDTO) {
        final CommentExtendEntity commentExtend = getCommentExtend(formDTO.getLinkId(), formDTO.getType(), formDTO.getMemberId());
        if (commentExtend != null) {
            throw new ValidationException("您已经点过赞了");
        }

        CommentExtendEntity CommentExtendEntity = new CommentExtendEntity();
        BeanUtils.copyProperties(formDTO, CommentExtendEntity);

        this.save(CommentExtendEntity);
        return CommentExtendEntity;
    }

    @Override
    public CommentExtendEntity updateArkSystemCommentExtend(CommentExtendFormDTO formDTO) {
        CommentExtendEntity CommentExtendEntity = getById(formDTO.getId());
        BeanUtils.copyProperties(formDTO, CommentExtendEntity);
        CommentExtendEntity.setUpdateTime(LocalDateTime.now());

        this.updateById(CommentExtendEntity);
        return CommentExtendEntity;
    }

    @Override
    public CommentExtendEntity deleteArkSystemCommentExtend(CommentExtendFormDTO formDTO) {
        CommentExtendEntity CommentExtendEntity = getArkSystemCommentExtend(formDTO.getId());
        CommentExtendEntity.setDeletorId(formDTO.getDeletorId());
        CommentExtendEntity.setDeletorName(formDTO.getDeletorName());
        CommentExtendEntity.setDeleteTime(LocalDateTime.now());
        CommentExtendEntity.setIsDeleted((byte) 1);
        this.updateById(CommentExtendEntity);
        return CommentExtendEntity;
    }
    @Override
    public CommentExtendEntity deleteBy(CommentExtendFormDTO formDTO) {
        LambdaQueryWrapper<CommentExtendEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentExtendEntity::getIsDeleted, 0);
        queryWrapper.eq(CommentExtendEntity::getLinkId, formDTO.getLinkId())
                .eq(CommentExtendEntity::getType,formDTO.getType())
                .eq(CommentExtendEntity::getMemberId,formDTO.getMemberId());
        CommentExtendEntity CommentExtendEntity = this.getOne(queryWrapper);
        if (null == CommentExtendEntity){
            throw new ValidationException("未找到点赞记录"+formDTO);
        }
        CommentExtendEntity.setDeletorId(formDTO.getDeletorId());
        CommentExtendEntity.setDeletorName(formDTO.getDeletorName());
        CommentExtendEntity.setDeleteTime(LocalDateTime.now());
        CommentExtendEntity.setIsDeleted((byte) 1);
        this.updateById(CommentExtendEntity);
        return CommentExtendEntity;
    }
    @Override
    public CommentExtendDTO mapperToDTO(CommentExtendEntity CommentExtendEntity) {
        if (null == CommentExtendEntity){
            return new CommentExtendDTO();
        }
        CommentExtendDTO arkSystemCommentExtendDTO = new CommentExtendDTO();
        BeanUtils.copyProperties(CommentExtendEntity, arkSystemCommentExtendDTO);

        return arkSystemCommentExtendDTO;
    }

    @Override
    public List<CommentExtendDTO> mapperToDTO(List<CommentExtendEntity> CommentExtendEntityList) {
        List<CommentExtendDTO> arkSystemCommentExtendDTOs = new ArrayList<>();
        for (CommentExtendEntity CommentExtendEntity : CommentExtendEntityList) {
            CommentExtendDTO arkSystemCommentExtendDTO = mapperToDTO(CommentExtendEntity);
            arkSystemCommentExtendDTOs.add(arkSystemCommentExtendDTO);
        }


        return arkSystemCommentExtendDTOs;
    }


}

