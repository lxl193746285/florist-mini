package com.qy.rest.sequence;

/**
 * 序列接口
 *
 * @author legendjw
 */
public interface Sequence {
    /**
     * 获取一个id
     *
     * @return
     */
    Long nextId();
}
