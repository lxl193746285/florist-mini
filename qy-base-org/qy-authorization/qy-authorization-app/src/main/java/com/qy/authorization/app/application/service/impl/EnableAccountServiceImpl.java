package com.qy.authorization.app.application.service.impl;

import com.qy.authorization.app.application.command.CreateEnableAccountCommand;
import com.qy.authorization.app.application.command.DeleteEnableAccountCommand;
import com.qy.authorization.app.application.command.QueryEnableAccountCommand;
import com.qy.authorization.app.application.service.EnableAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author di
 */
@Service
public class EnableAccountServiceImpl implements EnableAccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createEnableAccount(CreateEnableAccountCommand command) {

        String sql = "insert into ark_enable_account(user_id,organization_id,system_id,account_id,create_time) value (" + command.getUserId() + "," + command.getOrganizationId() + ",'" + command.getSystemId() + "'," + command.getAccountId() + ", now())";

        jdbcTemplate.execute(sql);
    }

    @Override
    public Integer countByUserOrg(QueryEnableAccountCommand command) {
        String sql = "select count(*) from ark_enable_account where organization_id=" + command.getOrganizationId();
        if (command.getUserId() != null) {
            sql = sql + " and user_id=" + command.getUserId();
        }
        if (command.getAccountId() != null) {
            sql = sql + " and account_id=" + command.getAccountId();
        }
        if (command.getSystemId() != null && !"".equals(command.getSystemId())) {
            sql = sql + " and system_id='" + command.getSystemId() + "'";
        }
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void deleteEnableAccount(DeleteEnableAccountCommand command) {
        String sql = "delete from ark_enable_account where organization_id=" + command.getOrganizationId() + " and user_id=" + command.getUserId() + " and account_id=" + command.getAccountId() + " and system_id='" + command.getSystemId() + "'";
        jdbcTemplate.execute(sql);
    }
}
