package com.qy.organization.app.domain.entity;

import com.qy.ddd.interfaces.Entity;
import com.qy.organization.app.domain.valueobject.JobId;
import com.qy.organization.app.domain.valueobject.PinyinName;
import lombok.Builder;
import lombok.Getter;

/**
 * 岗位
 *
 * @author legendjw
 */
@Getter
@Builder
public class Job implements Entity {
    /**
     * 岗位id
     */
    private JobId id;

    /**
     * 名称
     */
    private PinyinName name;
}
