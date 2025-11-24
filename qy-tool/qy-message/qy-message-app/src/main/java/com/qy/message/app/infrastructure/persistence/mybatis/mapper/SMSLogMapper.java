package com.qy.message.app.infrastructure.persistence.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.SMSLog;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.SMSLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SMSLogMapper extends BaseMapper<SMSLogDO> {


}