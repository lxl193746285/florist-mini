package com.qy.attachment.app.application.service.impl;

import com.qy.attachment.app.application.command.CreateThumbConfigCommand;
import com.qy.attachment.app.application.command.DeleteThumbConfigCommand;
import com.qy.attachment.app.application.command.UpdateThumbConfigCommand;
import com.qy.attachment.app.application.service.ThumbConfigCommandService;
import com.qy.attachment.app.domain.enums.ThumbConfigType;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbConfigDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.exception.NotFoundException;
import com.qy.rest.exception.ValidationException;
import com.qy.security.session.Identity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * 缩略图配置命令实现
 *
 * @author legendjw
 */
@Service
public class ThumbConfigCommandServiceImpl implements ThumbConfigCommandService {
    private AttachmentThumbConfigDataRepository attachmentThumbConfigDataRepository;

    public ThumbConfigCommandServiceImpl(AttachmentThumbConfigDataRepository attachmentThumbConfigDataRepository) {
        this.attachmentThumbConfigDataRepository = attachmentThumbConfigDataRepository;
    }

    @Override
    @Transactional
    public Long createAttachmentThumbConfig(CreateThumbConfigCommand command) {
        Identity identity = command.getIdentity();

        if (attachmentThumbConfigDataRepository.countByTypeIdAndModuleIdAndScenario(
                command.getTypeId(), command.getModuleId(), command.getScenario(), null) > 0) {
            throw new ValidationException("指定类型的模块场景已经设置");
        }

        AttachmentThumbConfigDO attachmentThumbConfigDO = new AttachmentThumbConfigDO();
        BeanUtils.copyProperties(command, attachmentThumbConfigDO, "identity", "uploadImageFormatLimit", "uploadFileFormatLimit", "uploadVideoFormatLimit");
        attachmentThumbConfigDO.setUploadImageFormatLimit(command.getUploadImageFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setUploadFileFormatLimit(command.getUploadFileFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setUploadVideoFormatLimit(command.getUploadVideoFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setTypeName(ThumbConfigType.getById(command.getTypeId()).getName());
        attachmentThumbConfigDO.setCreatorId(identity.getId());
        attachmentThumbConfigDO.setCreatorName(identity.getName());
        attachmentThumbConfigDO.setCreateTime(LocalDateTime.now());
        attachmentThumbConfigDO.setImageHandle(command.getImageHandle());
        //转为byte
        if (attachmentThumbConfigDO.getIsThumb().intValue() == 1) {
            attachmentThumbConfigDO.setSizeThreshold(new Float(command.getSizeThreshold() * 1024).intValue());
        }
        attachmentThumbConfigDO.setUploadImageMaxSize(new Float(command.getUploadImageMaxSize() * 1024).intValue());
        attachmentThumbConfigDO.setUploadFileMaxSize(new Float(command.getUploadFileMaxSize() * 1024).intValue());
        attachmentThumbConfigDO.setUploadVideoMaxSize(new Float(command.getUploadVideoMaxSize() * 1024).intValue());
        attachmentThumbConfigDataRepository.save(attachmentThumbConfigDO);

        return attachmentThumbConfigDO.getId();
    }

    @Override
    @Transactional
    public void updateAttachmentThumbConfig(UpdateThumbConfigCommand command) {
        AttachmentThumbConfigDO attachmentThumbConfigDO = attachmentThumbConfigDataRepository.findById(command.getId());
        if (attachmentThumbConfigDO == null) {
            throw new NotFoundException("未找到指定的客户缩略图配置");
        }
        if (attachmentThumbConfigDataRepository.countByTypeIdAndModuleIdAndScenario(
                command.getTypeId(), command.getModuleId(), command.getScenario(), attachmentThumbConfigDO.getId()) > 0) {
            throw new ValidationException("指定类型的模块场景已经设置");
        }
        Identity identity = command.getIdentity();

        BeanUtils.copyProperties(command, attachmentThumbConfigDO, "identity", "uploadImageFormatLimit", "uploadFileFormatLimit", "uploadVideoFormatLimit");
        attachmentThumbConfigDO.setUploadImageFormatLimit(command.getUploadImageFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setUploadFileFormatLimit(command.getUploadFileFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setUploadVideoFormatLimit(command.getUploadVideoFormatLimit().stream().collect(Collectors.joining(",")));
        attachmentThumbConfigDO.setTypeName(ThumbConfigType.getById(command.getTypeId()).getName());
        attachmentThumbConfigDO.setUpdatorId(identity.getId());
        attachmentThumbConfigDO.setUpdatorName(identity.getName());
        attachmentThumbConfigDO.setUpdateTime(LocalDateTime.now());
        attachmentThumbConfigDO.setImageHandle(command.getImageHandle());
        //转为byte
        if (attachmentThumbConfigDO.getIsThumb().intValue() == 1) {
            attachmentThumbConfigDO.setSizeThreshold(new Float(command.getSizeThreshold() * 1024).intValue());
        }
        attachmentThumbConfigDO.setUploadImageMaxSize(new Float(command.getUploadImageMaxSize() * 1024).intValue());
        attachmentThumbConfigDO.setUploadFileMaxSize(new Float(command.getUploadFileMaxSize() * 1024).intValue());
        attachmentThumbConfigDO.setUploadVideoMaxSize(new Float(command.getUploadVideoMaxSize() * 1024).intValue());
        attachmentThumbConfigDataRepository.save(attachmentThumbConfigDO);
    }

    @Override
    public void deleteAttachmentThumbConfig(DeleteThumbConfigCommand command) {
        Identity identity = command.getIdentity();

        attachmentThumbConfigDataRepository.remove(command.getId(), identity);
    }
}