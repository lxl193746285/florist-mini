package com.qy.rest.sequence;

import org.springframework.stereotype.Component;

/**
 * 雪花算法序列实现
 *
 * @author legendjw
 */
@Component
public class SnowflakeSequence implements Sequence {
    public static SnowflakeImpl snowflake = new SnowflakeImpl();

    @Override
    public Long nextId() {
        return snowflake.nextId();
    }
}
