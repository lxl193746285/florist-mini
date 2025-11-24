package com.qy.organization.app.application.listener;

import com.qy.organization.api.client.DepartmentClient;
import com.qy.organization.api.command.CreateDepartmentCommand;
import com.qy.organization.api.command.CreateTopDepartmentCommand;
import com.qy.organization.app.domain.event.OrganizationCreatedEvent;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 组织初始化
 *
 * @author legendjw
 */
@Component
public class OrganizationInitListener  {
    @Autowired
    private DepartmentClient departmentClient;

    @EventListener
    public void onOrganizationCreated(OrganizationCreatedEvent organizationCreatedEvent) {
        CreateTopDepartmentCommand command = new CreateTopDepartmentCommand();
        command.setOrganizationId(organizationCreatedEvent.getOrganizationId());
        //创建组织初始部门
        departmentClient.createOrganizationTopDepartment(command);
    }
}
