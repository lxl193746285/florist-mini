package com.qy.attachment.app.domain.file;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

/**
 * 随机文件名生成器
 *
 * @author legendjw
 */
@Component
public class RandomFileNameGenerator implements FileNameGenerator {

    @Override
    public String generateFileName(String rawFileName) {
        String fileExtension = getFileExtension(rawFileName);
        return String.format("%s%s%s",
                System.currentTimeMillis(),
                RandomUtils.nextInt(100000, 999999),
                fileExtension != "" ? "." + fileExtension : ""
        );
    }

    private String getFileExtension(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
}
