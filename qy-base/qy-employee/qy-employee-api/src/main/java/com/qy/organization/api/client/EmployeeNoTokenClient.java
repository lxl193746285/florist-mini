package com.qy.organization.api.client;

import com.qy.organization.api.dto.EmployeeBasicDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 员工客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-employee-no-token")
public interface EmployeeNoTokenClient {

    /**
     * 根据员工手机号获取基本员工信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/v4/api/employee/employees/basic-employees/by-phone/{phone}")
    EmployeeBasicDTO getBasicEmployeeByPhone(
            @PathVariable(value = "phone") String phone
    );
}
