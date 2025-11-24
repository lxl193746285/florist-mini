package com.qy.authorization.app.application.service;

import com.qy.authorization.app.application.command.CreateEnableAccountCommand;
import com.qy.authorization.app.application.command.DeleteEnableAccountCommand;
import com.qy.authorization.app.application.command.QueryEnableAccountCommand;

/**
 * @author di
 * @date 2023-05-10 10:28
 */
public interface EnableAccountService {

    void createEnableAccount(CreateEnableAccountCommand command);

    Integer countByUserOrg(QueryEnableAccountCommand command);

    void deleteEnableAccount(DeleteEnableAccountCommand command);
}
