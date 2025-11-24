package com.qy.attachment.app.application.service.impl;

import com.qy.attachment.app.application.assembler.AttachmentAssembler;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.application.dto.ConfigDTO;
import com.qy.attachment.app.application.service.AttachmentQueryService;
import com.qy.attachment.app.application.service.ThumbConfigQueryService;
import com.qy.attachment.app.config.AttachmentConfig;
import com.qy.attachment.app.domain.file.FileStorePathGenerator;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.infrastructure.persistence.AttachmentDomainRepository;
import com.qy.attachment.app.infrastructure.persistence.AttachmentRelationDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentRelationDO;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 附件查询服务实现
 *
 * @author legendjw
 */
@Service
public class AttachmentQueryServiceImpl implements AttachmentQueryService {
    private AttachmentAssembler attachmentAssembler;
    private AttachmentDomainRepository attachmentDomainRepository;
    private AttachmentRelationDataRepository attachmentRelationDataRepository;
    private ThumbConfigQueryService thumbConfigQueryService;
    private AttachmentConfig attachmentConfig;
    private FileStorePathGenerator fileStorePathGenerator;

    public AttachmentQueryServiceImpl(AttachmentAssembler attachmentAssembler, AttachmentDomainRepository attachmentDomainRepository, AttachmentRelationDataRepository attachmentRelationDataRepository, ThumbConfigQueryService thumbConfigQueryService, AttachmentConfig attachmentConfig, FileStorePathGenerator fileStorePathGenerator) {
        this.attachmentAssembler = attachmentAssembler;
        this.attachmentDomainRepository = attachmentDomainRepository;
        this.attachmentRelationDataRepository = attachmentRelationDataRepository;
        this.thumbConfigQueryService = thumbConfigQueryService;
        this.attachmentConfig = attachmentConfig;
        this.fileStorePathGenerator = fileStorePathGenerator;
    }

    @Override
    public ConfigDTO getConfigs(String moduleId, String type, String scenario) {
        AttachmentThumbConfigDO attachmentThumbConfigDO = thumbConfigQueryService.getModuleThumbConfigs(moduleId, scenario);

        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setStorageMode(attachmentConfig.storageMode);
        configDTO.setImageHandle(attachmentThumbConfigDO.getImageHandle());
        configDTO.setUploadMaxSize(attachmentConfig.uploadMaxSize);
        configDTO.setSaveFilePath(fileStorePathGenerator.generateStorePath(moduleId).toString());

        if (attachmentThumbConfigDO != null) {
            if (type.equals("image")) {
                configDTO.setUploadMaxSize(attachmentThumbConfigDO.getUploadImageMaxSize());
                configDTO.setUploadFormatLimit(
                        StringUtils.isNotBlank(attachmentThumbConfigDO.getUploadImageFormatLimit()) ?
                                Arrays.asList(attachmentThumbConfigDO.getUploadImageFormatLimit().split(",")) :
                                new ArrayList<>()
                );
                configDTO.setLimitImageHeight(attachmentThumbConfigDO.getLimitImageHeight());
                configDTO.setLimitImageWidth(attachmentThumbConfigDO.getLimitImageWidth());
            } else if (type.equals("file")) {
                configDTO.setUploadMaxSize(attachmentThumbConfigDO.getUploadFileMaxSize());
                configDTO.setUploadFormatLimit(
                        StringUtils.isNotBlank(attachmentThumbConfigDO.getUploadFileFormatLimit()) ?
                                Arrays.asList(attachmentThumbConfigDO.getUploadFileFormatLimit().split(",")) :
                                new ArrayList<>()
                );
            } else if (type.equals("video")) {
                configDTO.setUploadMaxSize(attachmentThumbConfigDO.getUploadVideoMaxSize());
                configDTO.setUploadFormatLimit(
                        StringUtils.isNotBlank(attachmentThumbConfigDO.getUploadVideoFormatLimit()) ?
                                Arrays.asList(attachmentThumbConfigDO.getUploadVideoFormatLimit().split(",")) :
                                new ArrayList<>()
                );
            }
        }

        return configDTO;
    }

    @Override
    public AttachmentDTO getAttachmentById(AttachmentId id) {
        AttachmentDO attachmentDO = attachmentDomainRepository.findById(id);
        return attachmentAssembler.toDTO(attachmentDO);
    }

    @Override
    public AttachmentBasicDTO getBasicAttachmentById(AttachmentId id) {
        AttachmentDO attachmentDO = attachmentDomainRepository.findById(id);
        return attachmentAssembler.toBasicDTO(attachmentDO);
    }

    @Override
    public List<AttachmentBasicDTO> getBasicAttachmentByIds(List<AttachmentId> ids) {
        List<AttachmentDO> attachmentDOs = attachmentDomainRepository.findByIds(ids);
        if (attachmentDOs.isEmpty()) {
            return new ArrayList<>();
        }
        return attachmentDOs.stream().map(a -> attachmentAssembler.toBasicDTO(a)).collect(Collectors.toList());
    }

    @Override
    public AttachmentBasicDTO getRelatedBasicAttachment(String moduleId, Long dataId, String type) {
        AttachmentRelationDO relationDO = attachmentRelationDataRepository.getAttachmentRelation(moduleId, dataId, type);
        if (relationDO == null) {
            return null;
        }
        AttachmentDO attachmentDO = attachmentDomainRepository.findById(new AttachmentId(relationDO.getAttachmentId()));
        return attachmentAssembler.toBasicDTO(attachmentDO);
    }

    @Override
    public List<AttachmentBasicDTO> getRelatedBasicAttachments(String moduleId, Long dataId, String type) {
        List<AttachmentRelationDO> relationDOS = attachmentRelationDataRepository.getAttachmentRelations(moduleId, dataId, type);
        if (relationDOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<AttachmentId> attachmentIds = relationDOS.stream().map(a -> new AttachmentId(a.getAttachmentId())).collect(Collectors.toList());
        List<AttachmentDO> attachmentDOs = attachmentDomainRepository.findByIds(attachmentIds);
        if (attachmentDOs.isEmpty()) {
            return new ArrayList<>();
        }
        return attachmentDOs.stream()
                .sorted(Comparator.comparing(a -> {
                    AttachmentRelationDO relationDO = relationDOS.stream().filter(r -> r.getAttachmentId().equals(a.getId())).findFirst().orElse(null);
                    return relationDO != null ? relationDO.getSort() : 0;
                }))
                .map(a -> attachmentAssembler.toBasicDTO(a)).collect(Collectors.toList());
    }
}
