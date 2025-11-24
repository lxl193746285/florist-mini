package com.qy.organization.app.application.command;

import lombok.Data;

import java.io.Serializable;

/**
 * 员工在职命令
 *
 * @author legendjw
 */
@Data
public class EmployeeOnJobCommand implements Serializable {
    /**
     * 员工id
     */
    private Long id;
}