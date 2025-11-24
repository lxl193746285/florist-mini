package com.qy.message.app.application.service;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

public interface SqlService {

    String getContentMsg(String sql);

    List<Integer> getDataIds(String sql);

    List<Object> getDatas(String sql);

    List<Map<String, Object>> getDats(String sql);

    <T> List<T> getList(String sql, RowMapper<T> rowMapper);

    int createData(String sql);

    Integer getCount(String sql);

    int createDataReturnId(String sql);
}
