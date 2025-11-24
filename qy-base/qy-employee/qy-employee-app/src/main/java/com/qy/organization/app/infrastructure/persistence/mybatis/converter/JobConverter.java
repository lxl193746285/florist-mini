package com.qy.organization.app.infrastructure.persistence.mybatis.converter;

import com.qy.organization.app.domain.entity.Job;
import com.qy.organization.app.domain.valueobject.JobId;
import com.qy.organization.app.domain.valueobject.PinyinName;
import com.qy.organization.app.infrastructure.persistence.mybatis.dataobject.JobDO;
import org.mapstruct.Mapper;

/**
 * 岗位转化器
 *
 * @author legendjw
 */
@Mapper
public interface JobConverter {
    /**
     * DO类转化为岗位实体
     *
     * @param jobDO
     * @return
     */
    default Job toEntity(JobDO jobDO) {
        if (jobDO == null) {
            return null;
        }
        return Job.builder()
                .id(new JobId(jobDO.getId()))
                .name(new PinyinName(jobDO.getName(), jobDO.getNamePinyin()))
                .build();
    }
}