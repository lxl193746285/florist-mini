package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.service.SqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SqlServiceImpl implements SqlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Nullable
    private DataSource dataSource;


    private static final Logger logger = LoggerFactory.getLogger(SqlService.class);

    public String getContentMsg(String sql) {
        logger.debug("打印" + sql);
        List<String> getContentMsg = jdbcTemplate.queryForList(sql, String.class);
        logger.debug("打印" + sql);
        if (getContentMsg.size() == 0) return "";
        return getContentMsg.get(0);
    }

    @Override
    public List<Integer> getDataIds(String sql) {
        logger.debug("打印" + sql);
        List<Integer> ids = jdbcTemplate.queryForList(sql, Integer.class);
        logger.debug("打印" + sql);
        return ids;

    }

    @Override
    public List<Map<String, Object>> getDats(String sql) {
        List<Map<String, Object>> t = new ArrayList<Map<String, Object>>();
        logger.debug("打印" + sql);
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        logger.debug("打印" + sql);
        t.addAll(data);
        return t;

    }

    @Override
    public <T> List<T> getList(String sql, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public int createData(String sql) {
        logger.debug("打印" + sql);
        return jdbcTemplate.update(sql);
    }

    @Override
    public Integer getCount(String sql) {
        logger.debug("打印" + sql);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int createDataReturnId(String sql) {
        System.out.println(sql);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( new PreparedStatementCreator(){
                                 @Override
                                 public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                     PreparedStatement ps = conn.prepareStatement(sql, new String[] {"id"});
                                     //ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                                     return ps;
                                 }
                             },
                keyHolder);
        return keyHolder.getKey().intValue();

        /*NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int autoIncId = 0;

        jdbcTemplate.update( sql, parameters, keyHolder, new String[] {"ID" });
        autoIncId = keyHolder.getKey().intValue();

        System.out.println(autoIncId);

        return autoIncId;*/
    }

    @Override
    public List<Object> getDatas(String sql) {
        logger.debug("打印" + sql);
        return jdbcTemplate.queryForList(sql, Object.class);
    }
}