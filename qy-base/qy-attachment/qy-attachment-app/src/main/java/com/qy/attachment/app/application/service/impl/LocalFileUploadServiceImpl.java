package com.qy.attachment.app.application.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.qy.attachment.app.application.command.LocalUploadFileCommand;
import com.qy.attachment.app.application.command.LocalUploadFilesCommand;
import com.qy.attachment.app.application.dto.AttachmentBasicDTO;
import com.qy.attachment.app.application.dto.AttachmentDTO;
import com.qy.attachment.app.application.service.LocalFileUploadService;
import com.qy.attachment.app.application.service.ThumbConfigQueryService;
import com.qy.attachment.app.config.AttachmentConfig;
import com.qy.attachment.app.domain.entity.FileAttachment;
import com.qy.attachment.app.domain.entity.ImageAttachment;
import com.qy.attachment.app.domain.entity.ImageThumbAttachment;
import com.qy.attachment.app.domain.enums.StorageMode;
import com.qy.attachment.app.domain.file.FileNameGenerator;
import com.qy.attachment.app.domain.file.FileStorePathGenerator;
import com.qy.attachment.app.domain.valueobject.AttachmentId;
import com.qy.attachment.app.domain.valueobject.ThumbAttachmentId;
import com.qy.attachment.app.domain.valueobject.User;
import com.qy.attachment.app.infrastructure.persistence.AttachmentDomainRepository;
import com.qy.attachment.app.infrastructure.persistence.AttachmentThumbDataRepository;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.exception.ValidationException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地文件上传命令服务实现
 *
 * @author legendjw
 */
@Service
public class LocalFileUploadServiceImpl implements LocalFileUploadService {
    private static final Logger logger = LoggerFactory.getLogger(LocalFileUploadServiceImpl.class);
    private FileStorePathGenerator fileStorePathGenerator;
    private FileNameGenerator fileNameGenerator;
    private AttachmentDomainRepository attachmentDomainRepository;
    private ThumbConfigQueryService thumbConfigQueryService;
    private AttachmentThumbDataRepository attachmentThumbDataRepository;
    private AttachmentConfig attachmentConfig;

    public LocalFileUploadServiceImpl(FileStorePathGenerator fileStorePathGenerator, FileNameGenerator fileNameGenerator, AttachmentDomainRepository attachmentDomainRepository, ThumbConfigQueryService thumbConfigQueryService, AttachmentThumbDataRepository attachmentThumbDataRepository, AttachmentConfig attachmentConfig) {
        this.fileStorePathGenerator = fileStorePathGenerator;
        this.fileNameGenerator = fileNameGenerator;
        this.attachmentDomainRepository = attachmentDomainRepository;
        this.thumbConfigQueryService = thumbConfigQueryService;
        this.attachmentThumbDataRepository = attachmentThumbDataRepository;
        this.attachmentConfig = attachmentConfig;
    }

    @Override
    public AttachmentId uploadFile(LocalUploadFileCommand command) {
        MultipartFile multipartFile = command.getFile();
        if (multipartFile.isEmpty()) {
            throw new ValidationException("请选择文件进行上传");
        }
        //if (multipartFile.getSize() > attachmentConfig.uploadMaxSize) {
        //    throw new ValidationException(String.format("文件上传大小超出系统设置最大值：%s", FileUtils.byteCountToDisplaySize(attachmentConfig.uploadMaxSize)));
        //}
        String saveFileName = fileNameGenerator.generateFileName(multipartFile.getOriginalFilename());
        Path savePath = fileStorePathGenerator.generateStorePath(command.getModuleId());
        Path saveFilePath = Paths.get(String.format("%s%s%s", savePath.toString(), File.separator, saveFileName));
        Path fullSavePath = Paths.get(String.format("%s%s%s", attachmentConfig.localUploadPath, File.separator, savePath.toString()));
        Path fullSaveFilePath = Paths.get(String.format("%s%s%s%s%s", attachmentConfig.localUploadPath, File.separator, savePath.toString(), File.separator, saveFileName));
        if (!fullSavePath.toFile().exists()) {
            fullSavePath.toFile().mkdirs();
        }
        try {
            File uploadFile = new File(fullSaveFilePath.toString());
            multipartFile.transferTo(uploadFile);
            String mimeType = getFileMimeType(uploadFile);
            FileAttachment attachment = FileAttachment.builder()
                    .organizationId(command.getOrganizationId())
                    .storageMode(StorageMode.LOCAL)
                    .name(multipartFile.getOriginalFilename())
                    .path(saveFilePath.toString())
                    .url(String.format("%s/%s", attachmentConfig.localAccessDomain, saveFilePath.toString().replaceAll("\\\\", "/")))
                    .mimeType(mimeType)
                    .size((int) multipartFile.getSize())
                    .md5(DigestUtils.md5Hex(new FileInputStream(uploadFile)))
                    .creator(command.getIdentityId() == null ? null : new User(command.getIdentityId(), command.getIdentityName()))
                    .build();

            if (attachment.isImage()) {
                BufferedImage image = ImageIO.read(uploadFile);
                int width = image.getWidth();
                int height = image.getHeight();

                ImageAttachment imageAttachment = ImageAttachment.builder()
                        .organizationId(attachment.getOrganizationId())
                        .storageMode(attachment.getStorageMode())
                        .name(attachment.getName())
                        .path(attachment.getPath())
                        .url(attachment.getUrl())
                        .mimeType(mimeType)
                        .size(attachment.getSize())
                        .md5(attachment.getMd5())
                        .width(width)
                        .height(height)
                        .creator(attachment.getCreator())
                        .build();
                AttachmentId attachmentId = attachmentDomainRepository.saveImageAttachment(imageAttachment);
                imageAttachment.setId(attachmentId);

                //缩略图片
                thumbImage(command.getModuleId(), imageAttachment);

                return attachmentId;
            }
            else {
                return attachmentDomainRepository.saveFileAttachment(attachment);
            }
        } catch (IOException e) {
            String errorMessage = "文件上传异常";
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage);
        }
    }

    @Override
    public List<AttachmentId> uploadFiles(LocalUploadFilesCommand command) {
        List<AttachmentId> attachmentIds = new ArrayList<>();
        for (MultipartFile file : command.getFiles()) {
            LocalUploadFileCommand uploadFileCommand = new LocalUploadFileCommand();
            uploadFileCommand.setFile(file);
            uploadFileCommand.setIdentityId(command.getIdentityId());
            uploadFileCommand.setIdentityName(command.getIdentityName());
            uploadFileCommand.setOrganizationId(command.getOrganizationId());
            uploadFileCommand.setModuleId(command.getModuleId());
            attachmentIds.add(uploadFile(uploadFileCommand));
        }
        return attachmentIds;
    }

    @Override
    public AttachmentBasicDTO createAttachment(AttachmentDTO attachmentDTO) {
        ImageAttachment imageAttachment = ImageAttachment.builder()
                .name(attachmentDTO.getName())
                .path(attachmentDTO.getPath())
                .storageMode(StorageMode.ALIYUN_OSS)
                .url(attachmentDTO.getUrl())
                .mimeType(attachmentDTO.getMimeType())
                .size(attachmentDTO.getSize()).build();
        AttachmentId attachmentId = attachmentDomainRepository.saveImageAttachment(imageAttachment);
        attachmentDTO.setId(attachmentId.getId());
        AttachmentBasicDTO basicDTO = new AttachmentBasicDTO();
        BeanUtils.copyProperties(attachmentDTO, basicDTO);
        return basicDTO;
    }

    /**
     * 缩略图片
     *
     * @param imageAttachment
     * @return
     */
    private List<ThumbAttachmentId> thumbImage(String moduleId, ImageAttachment imageAttachment) throws IOException {
        List<AttachmentThumbConfigDO> thumbConfigDOS = thumbConfigQueryService.getModuleThumbConfigs(moduleId);
        if (thumbConfigDOS.isEmpty()) { return new ArrayList<>(); }

        List<ImageThumbAttachment> thumbAttachments = new ArrayList<>();
        File file = new File(String.format("%s%s%s", attachmentConfig.localUploadPath, File.separator, imageAttachment.getPath()));
        Path savePath = Paths.get(imageAttachment.getPath().replace(File.separator + file.getName(), ""));
        File parentFile = file.getParentFile();
        String pathName = "";
        if (imageAttachment.getPath().contains("/")){
            pathName = imageAttachment.getPath().substring(imageAttachment.getPath().lastIndexOf("/") + 1);
        }else {
            pathName = imageAttachment.getPath().substring(imageAttachment.getPath().lastIndexOf("\\") + 1);
        }
        //循环读取配置进行不通场景下的缩略图缩略
        for (AttachmentThumbConfigDO thumbConfigDO : thumbConfigDOS) {
            String thumbFileName = String.format("%s.%s.%s", "thumb", thumbConfigDO.getScenario(), pathName);
            String thumbFilePath = String.format("%s%s%s", parentFile.getPath(), File.separator, thumbFileName);
            File thumbFile = new File(thumbFilePath);
            Path path = Paths.get(thumbFile.getPath().replace(java.io.File.separator + thumbFile.getName(), ""));
            if (!path.toFile().exists()){
                path.toFile().mkdirs();
            }
            //图片大于设置的阀值才进行压缩
            if (thumbConfigDO.getIsThumb().intValue() == 1 && imageAttachment.getSize() > thumbConfigDO.getSizeThreshold().intValue()) {
                //如果图片尺寸小于设置的缩略尺寸则使用原图尺寸
                int width = Integer.min(thumbConfigDO.getWidth().intValue(), imageAttachment.getWidth());
                int height = Integer.min(thumbConfigDO.getHeight().intValue(), imageAttachment.getHeight());
                //缩略图片
                if (thumbConfigDO.getIsResize().intValue() == 1) {
                    Thumbnails.of(file)
                            .width(width)
                            .height(height)
                            .outputQuality(new BigDecimal(thumbConfigDO.getQuality()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).floatValue())
                            .toFile(thumbFile);
                }
                //裁剪图片
                else if (thumbConfigDO.getIsCrop().intValue() == 1) {
                    Thumbnails.of(file)
                            .sourceRegion(Positions.CENTER, width, height)
                            .scale(1.0)
                            .outputQuality(new BigDecimal(thumbConfigDO.getQuality()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).floatValue())
                            .toFile(thumbFile);
                }
                String saveFilePath = String.format("%s%s%s", savePath.toString(), File.separator, thumbFileName);
                ImageThumbAttachment thumbAttachment = ImageThumbAttachment.builder()
                        .organizationId(imageAttachment.getOrganizationId())
                        .imageAttachmentId(imageAttachment.getId())
                        .storageMode(imageAttachment.getStorageMode())
                        .scenario(thumbConfigDO.getScenario())
                        .name(thumbFile.getName())
                        .path(saveFilePath)
                        .url(String.format("%s/%s", attachmentConfig.localAccessDomain, saveFilePath.toString().replaceAll("\\\\", "/")))
                        .mimeType(imageAttachment.getMimeType())
                        .width(width)
                        .height(height)
                        .size((int) thumbFile.length())
                        .md5(DigestUtils.md5Hex(new FileInputStream(thumbFile)))
                        .creator(imageAttachment.getCreator())
                        .build();
                thumbAttachments.add(thumbAttachment);
            }
            //直接使用原图作为缩略图
            else {
                ImageThumbAttachment thumbAttachment = ImageThumbAttachment.builder()
                        .organizationId(imageAttachment.getOrganizationId())
                        .imageAttachmentId(imageAttachment.getId())
                        .storageMode(imageAttachment.getStorageMode())
                        .scenario(thumbConfigDO.getScenario())
                        .name(imageAttachment.getName())
                        .path(imageAttachment.getPath())
                        .url(imageAttachment.getUrl())
                        .mimeType(imageAttachment.getMimeType())
                        .width(imageAttachment.getWidth())
                        .height(imageAttachment.getHeight())
                        .size(imageAttachment.getSize())
                        .md5(imageAttachment.getMd5())
                        .creator(imageAttachment.getCreator())
                        .build();
                thumbAttachments.add(thumbAttachment);
            }
        }
        List<ThumbAttachmentId> thumbAttachmentIds = new ArrayList<>();
        for (ImageThumbAttachment thumbAttachment : thumbAttachments) {
            thumbAttachmentIds.add(attachmentThumbDataRepository.save(thumbAttachment));
        }

        return thumbAttachmentIds;
    }

    /**
     * 获取文件mime type
     *
     * @param file
     * @return
     */
    private String getFileMimeType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file.getName());
    }
}
