package com.qy.attachment.app.domain.file;

/**
 * 文件名生成器
 *
 * @author legendjw
 */
public interface FileNameGenerator {
    /**
     * 根据文件原始名生成一个新的文件名
     *
     * @param rawFileName
     * @return
     */
    String generateFileName(String rawFileName);
}
