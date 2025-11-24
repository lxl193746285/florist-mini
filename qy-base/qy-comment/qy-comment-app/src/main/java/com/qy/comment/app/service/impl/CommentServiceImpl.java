package com.qy.comment.app.service.impl;

import com.qy.attachment.api.client.AttachmentClient;
import com.qy.attachment.api.command.RelateAttachmentsCommand;
import com.qy.attachment.api.dto.AttachmentBasicDTO;
import com.qy.comment.app.dto.CommentDTO;
import com.qy.comment.app.dto.CommentQueryDTO;
import com.qy.comment.app.entity.CommentEntity;
import com.qy.comment.app.enums.SystemTypeStatus;
import com.qy.comment.app.mapper.CommentMapper;
import com.qy.comment.app.service.CommentService;
import com.qy.comment.app.vo.AttachmentVo;
import com.qy.rest.exception.ValidationException;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.PageImpl;
import com.qy.rest.pagination.PageRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 评论 服务实现类
 *
 * @author huangh
 * @since 2022-04-02
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentEntity> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public Page<CommentDTO> getComments(CommentQueryDTO queryDTO) {
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(queryDTO.getLinkType() != null, CommentEntity::getLinkType, queryDTO.getLinkType())
                .eq(queryDTO.getLinkId() != null, CommentEntity::getLinkId, queryDTO.getLinkId())
                .eq(CommentEntity::getPrimaryCommentId, 0)
                .eq(queryDTO.getSystemType() != null, CommentEntity::getSystemType, queryDTO.getSystemType())
                .eq(CommentEntity::getIsDeleted, 0)
                .orderByDesc(CommentEntity::getCreateTime);
        IPage<CommentDTO> commentList = commentMapper.selectByLinkDatas(new PageDTO<>(queryDTO.getPage(), queryDTO.getPerPage()), queryWrapper);
//        IPage<CommentDTO> commentList = commentMapper.selectByLinkData(queryDTO.getLinkType(), queryDTO.getLinkId(), 0L);

        for (CommentDTO commentDTO : commentList.getRecords()) {
//            fillCommentFields(commentDTO);
            fillReplyCommentFields(commentDTO);
        }

        return new PageImpl<>(new PageRequest(queryDTO.getPage(), queryDTO.getPerPage()), commentList.getTotal(), commentList.getRecords());
    }

    @Override
    public Page<CommentDTO> getCommentsForPC(CommentQueryDTO queryDTO) {
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(queryDTO.getLinkType() != null, CommentEntity::getLinkType, queryDTO.getLinkType())
                .eq(queryDTO.getLinkId() != null, CommentEntity::getLinkId, queryDTO.getLinkId())
                .eq(queryDTO.getMemberId() != null, CommentEntity::getMemberId, queryDTO.getMemberId())
                .eq(queryDTO.getPrimaryCommentId()!= null,CommentEntity::getPrimaryCommentId, queryDTO.getPrimaryCommentId())
                .eq(queryDTO.getSystemType() != null, CommentEntity::getSystemType, queryDTO.getSystemType())
                .eq(CommentEntity::getIsDeleted, 0)
                .ge(null != queryDTO.getStartCreateDate(), CommentEntity::getCreateTime, queryDTO.getStartCreateDate())
                .le(null != queryDTO.getEndCreateDate(), CommentEntity::getCreateTime, queryDTO.getEndCreateDate())
                .orderByDesc(CommentEntity::getCreateTime);
        IPage<CommentDTO> commentList = commentMapper.selectByLinkDatas(new PageDTO<>(queryDTO.getPage(), queryDTO.getPerPage()), queryWrapper);
//        IPage<CommentDTO> commentList = commentMapper.selectByLinkData(queryDTO.getLinkType(), queryDTO.getLinkId(), 0L);

        for (CommentDTO commentDTO : commentList.getRecords()) {
//            fillCommentFields(commentDTO);
            fillReplyCommentFields(commentDTO);
        }
        return new PageImpl<>(new PageRequest(queryDTO.getPage(), queryDTO.getPerPage()), commentList.getTotal(), commentList.getRecords());
    }

    @Override
    public int getTotalCommentCount(CommentDTO commentDTO) {
        return getTotalCommentCount(commentDTO.getLinkId(),commentDTO.getLinkType());
    }

    @Override
    public List<CommentDTO> getCommentsByLinkData(String linkType, Long linkId) {
        List<CommentDTO> commentVos = commentMapper.selectByLinkData(linkType, linkId, 0L);

        for (CommentDTO commentDTO : commentVos) {
//            fillCommentFields(commentVo, currentUser);
            fillReplyCommentFields(commentDTO);
        }

        return commentVos;
    }


    public List<CommentDTO> getReplyComments(Long commentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("primaryCommentId", commentId);
        List<CommentDTO> commentVos = commentMapper.selectByParams(params);

        for (CommentDTO commentVo : commentVos) {
//            fillCommentFields(commentVo);
            commentVo.setPraiseNum(getPraiseNum(commentVo.getId()));
            List<AttachmentVo> attachmentVos = new ArrayList<>();
            final List<AttachmentBasicDTO> relatedBasicAttachments = attachmentClient.getRelatedBasicAttachments("comment", commentVo.getId(), "comment");
            for (AttachmentBasicDTO attachmentBasicDTO : relatedBasicAttachments) {
                AttachmentVo vo = new AttachmentVo();
                BeanUtils.copyProperties(attachmentBasicDTO, vo);
                if (Strings.isBlank(vo.getThumbUrl())) {
                    vo.setThumbUrl(vo.getUrl());
                }
                attachmentVos.add(vo);
            }
            commentVo.setAttachments(attachmentVos);
        }
        return commentVos;
    }

    private CommentDTO fillReplyCommentFields(CommentDTO commentDTO) {
        commentDTO.setReplyComments(getReplyComments(commentDTO.getId()));
        commentDTO.setReplyCommentCount(commentDTO.getReplyComments().size());
        commentDTO.setCommentCount(getCommentCount(commentDTO.getId()));
        commentDTO.setPraiseNum(getPraiseNum(commentDTO.getId()));
        commentDTO.setConcernsNum(getConcernsNum(commentDTO.getId()));
        commentDTO.setTotalCommentCount(getTotalCommentCount(commentDTO.getLinkId(),commentDTO.getLinkType()));
        List<AttachmentVo> attachmentVos = new ArrayList<>();
        final List<AttachmentBasicDTO> relatedBasicAttachments = attachmentClient.getRelatedBasicAttachments("comment", commentDTO.getId(), "comment");
        for (AttachmentBasicDTO attachmentBasicDTO : relatedBasicAttachments) {
            AttachmentVo vo = new AttachmentVo();
            BeanUtils.copyProperties(attachmentBasicDTO, vo);
            if (Strings.isBlank(vo.getThumbUrl())) {
                vo.setThumbUrl(vo.getUrl());
            }
            attachmentVos.add(vo);
        }
        commentDTO.setAttachments(attachmentVos);
        return commentDTO;
    }

    private int getPraiseNum(Long id) {
        return commentMapper.getPraiseNum(id, 1);
    }

    private int getConcernsNum(Long id) {
        return commentMapper.getPraiseNum(id, 2);
    }
    private int getCommentCount(Long id) {
        return commentMapper.getCommentCount(id);
    }

    private int getTotalCommentCount(Long linkId,String linkType) {
        return commentMapper.getTotalCommentCount(linkId,linkType);
    }
    /**
     * 新增评论
     *
     * @param commentDTO
     * @return
     */
    @Override
    @Transactional
    public void createComment(CommentDTO commentDTO
    ) {
        //查找主评论ID
        if (commentDTO.getReplyCommentId() != null && commentDTO.getReplyCommentId() != 0) {
            LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CommentEntity::getId, commentDTO.getReplyCommentId())
                    .eq(CommentEntity::getIsDeleted, 0);
            CommentEntity replyComment = this.getOne(queryWrapper);
            if (replyComment != null) {
                commentDTO.setPrimaryCommentId(replyComment.getPrimaryCommentId().intValue() != 0 ? replyComment.getPrimaryCommentId() : replyComment.getId());
//                CommentEntity commentEntity = this.getById(commentDTO.getPrimaryCommentId());
                commentDTO.setReplyName(replyComment.getCreatorName());
            }
        }

        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentDTO, commentEntity);
        if (commentDTO.getPrimaryCommentId() == null) {
            commentEntity.setPrimaryCommentId(0L);
        } else {
            commentEntity.setPrimaryCommentId(commentDTO.getPrimaryCommentId());
        }
        commentEntity.setCreateTime(LocalDateTime.now());
        commentEntity.setCreatorId(commentDTO.getMemberId());

        if (commentEntity.getSystemType()!=null && commentEntity.getSystemType()== SystemTypeStatus.OS.getIndex()){
            commentEntity.setCreatorName("平台客服-"+commentDTO.getCreatorName());
        }else{
            commentEntity.setCreatorName(commentDTO.getCreatorName());
        }

        this.save(commentEntity);

        if (!CollectionUtils.isEmpty(commentDTO.getAttachments())) {
            RelateAttachmentsCommand command = new RelateAttachmentsCommand();
            command.setAttachmentIds(new ArrayList<>());
            command.setDataId(commentEntity.getId());
            command.setModuleId("comment");
            command.setType("comment");
            for (AttachmentVo attachmentVo : commentDTO.getAttachments()) {
                command.getAttachmentIds().add(attachmentVo.getId());
            }

            attachmentClient.relateAttachments(command);
        }

    }

    @Override
    @Transactional
    public CommentDTO getCommentById(Long id) {
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentEntity::getId, id)
                .eq(CommentEntity::getIsDeleted, 0);
        CommentEntity comment = this.getOne(queryWrapper);
//        CommentEntity comment = this.getById(id);
        if (null == comment) {
            throw new ValidationException("未找到评论");
        }

        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
//        fillCommentFields(commentVo);
        fillReplyCommentFields(commentDTO);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (comment.getCreateTime() != null) {
            commentDTO.setCreateTimeName(df.format(comment.getCreateTime()));
        }
        if (comment.getUpdateTime() != null) {
            commentDTO.setUpdateTimeName(df.format(comment.getUpdateTime()));
        }
        return commentDTO;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        CommentEntity comment = this.getById(id);
        //删除主评论需要删除所有的子评论
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentEntity::getPrimaryCommentId, id);
        List<CommentEntity> commentEntities = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(commentEntities)) {
            for (CommentEntity commentEntity : commentEntities) {
                commentEntity.setIsDeleted((byte) 1);
                commentEntity.setDeletorId(commentEntity.getCreatorId());
                commentEntity.setDeletorName(commentEntity.getCreatorName());
                commentEntity.setDeleteTime(LocalDateTime.now());
                this.updateById(commentEntity);
            }
        }


        getUpdateReplyComment(comment);


        comment.setIsDeleted((byte) 1);
        comment.setDeletorId(comment.getCreatorId());
        comment.setDeletorName(comment.getCreatorName());
        comment.setDeleteTime(LocalDateTime.now());
        this.updateById(comment);

    }

    private void getUpdateReplyComment(CommentEntity comment) {
        LambdaQueryWrapper<CommentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentEntity::getReplyCommentId, comment.getId());
        List<CommentEntity> commentEntities = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(commentEntities)) {
            for (CommentEntity commentEntity : commentEntities) {
                commentEntity.setIsDeleted((byte) 1);
                commentEntity.setDeletorId(commentEntity.getCreatorId());
                commentEntity.setDeletorName(commentEntity.getCreatorName());
                commentEntity.setDeleteTime(LocalDateTime.now());
                this.updateById(commentEntity);
            }
            for (CommentEntity commentEntity : commentEntities) {
                getUpdateReplyComment(commentEntity);
            }
        }
    }

    @Override
    @Transactional
    public void updateComment(CommentDTO commentDTO) {
        CommentEntity oldComment = this.getById(commentDTO.getId());
        if (oldComment == null) {
            throw new RuntimeException("未找到此条评论");
        }
        oldComment.setContent(commentDTO.getContent());
        oldComment.setUpdatorId(commentDTO.getUpdatorId());
        oldComment.setUpdatorName(commentDTO.getUpdatorName());
        oldComment.setUpdateTime(LocalDateTime.now());
        this.updateById(oldComment);
    }

    @Override
    public CommentEntity getOneCommentById(Long id) {
        return this.getById(id);
    }


}
