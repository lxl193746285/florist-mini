package com.qy.organization.app.application.service;

import com.qy.organization.app.application.command.CreateJobCommand;
import com.qy.organization.app.application.command.DeleteJobCommand;
import com.qy.organization.app.application.command.UpdateJobCommand;

/**
 * 岗位命令服务
 *
 * @author legendjw
 */
public interface JobCommandService {
    /**
     * 创建岗位命令
     *
     * @param command
     * @return
     */
    Long createJob(CreateJobCommand command);
    Long createJobForSh(CreateJobCommand command);

    /**
     * 编辑部门命令
     *
     * @param command
     */
    void updateJob(UpdateJobCommand command);
    void updateJobForSh(UpdateJobCommand command);

    /**
     * 删除部门命令
     *
     * @param command
     */
    void deleteJob(DeleteJobCommand command);
    void deleteJobForSh(DeleteJobCommand command);
}
