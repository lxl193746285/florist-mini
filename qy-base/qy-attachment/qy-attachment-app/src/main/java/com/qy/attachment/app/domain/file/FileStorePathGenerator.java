package com.qy.attachment.app.domain.file;

import java.nio.file.Path;

/**
 * 文件存储路径生成器
 *
 * @author legendjw
 */
public interface FileStorePathGenerator {
    /**
     * 生成一个存储文件的路径
     *
     * @return
     */
    Path generateStorePath(String moduleId);
}
