package com.qy.attachment.app.domain.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 日期文件存储路径生成器
 *
 * @author legendjw
 */
@Component
public class DateFileStorePathGenerator implements FileStorePathGenerator {

    @Override
    public Path generateStorePath(String moduleId) {
        if (StringUtils.isBlank(moduleId)) {
            moduleId = "default";
        }
        LocalDate date = LocalDate.now();
        String path = String.format("%s/%s/%s/%s",
                moduleId,
                date.format(DateTimeFormatter.ofPattern("yyyy")),
                date.format(DateTimeFormatter.ofPattern("MM")),
                date.format(DateTimeFormatter.ofPattern("dd"))
        );
        return Paths.get(path);
    }
}
