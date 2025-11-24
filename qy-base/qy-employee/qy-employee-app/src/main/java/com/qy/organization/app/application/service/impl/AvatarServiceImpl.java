package com.qy.organization.app.application.service.impl;

import com.qy.attachment.api.dto.AttachmentDTO;
import com.qy.attachment.api.enums.StorageMode;
import com.qy.organization.app.application.service.AvatarService;
import com.qy.organization.app.domain.valueobject.Avatar;
import com.qy.util.AvatarUtils;
import com.qy.util.CharacterUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.util.UUID;

/**
 * 头像服务实现
 *
 * @author legendjw
 */
@Service
public class AvatarServiceImpl implements AvatarService {
    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    public static int defaultAvatarWidth = 200;
    public static int defaultAvatarHeight = 200;
    @Value("${qy.avatar.path}")
    private String avatarPath;
    @Value("${qy.avatar.url}")
    private String avatarUrl;

    @Override
    public Avatar generateAvatarByName(String name, String fileName) {
        //如果昵称过滤特殊字符后为空则随机生成一个昵称
        name = CharacterUtils.replaceEnjoyAndSpecialChar(name.toUpperCase());
        if (StringUtils.isBlank(name)) {
            name = RandomStringUtils.randomAlphabetic(6);
        }

        String avatarFileName = String.format("%s.jpg", fileName);
        try {
            AvatarUtils.generateAvatar(name, avatarPath, avatarFileName);
        } catch (Exception e) {
            logger.error("生成账号头像出错", e);
        }
        return new Avatar(String.format("%s/%s?v=%s", avatarUrl, avatarFileName, Instant.now().getEpochSecond()));
    }

    @Override
    public Avatar generateAvatarByAttachment(AttachmentDTO attachment, String fileName) {
        if (StringUtils.isBlank(fileName)) {
            fileName = UUID.randomUUID().toString().replace("-", "");
        }
        String avatarFileName = String.format("%s.jpg", fileName);
        String saveFile = avatarPath + File.separator + avatarFileName;
        try {
            Thumbnails.Builder builder = attachment.getStorageMode().intValue() == StorageMode.LOCAL.getId()
                    ? Thumbnails.of(new File(attachment.getPath())) : Thumbnails.of(new URL(attachment.getUrl()));
            builder.size(defaultAvatarWidth, defaultAvatarHeight).toFile(new File(saveFile));
        } catch (Exception e) {
            logger.error("根据附件生成账号头像出错", e);
        }
        return new Avatar(String.format("%s/%s?v=%s", avatarUrl, avatarFileName, Instant.now().getEpochSecond()));
    }
}