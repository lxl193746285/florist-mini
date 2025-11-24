package com.qy.base.interfaces.system.api;

import com.qy.system.app.autonumber.service.SnSetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 编号规则设置 前端控制器
 *
 * @author ln
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/v4/api/system/autonumber")
public class AutoNumberController {

    @Autowired
    private SnSetsService snSetsService;

    /**
     * 获取编号
     * @param noId
     * @param companyId
     * @param userId
     * @return
     */
    @GetMapping("/getAutoNumber")
    public String getAutoNumber(
            @RequestParam(value = "no_id") String noId,
            @RequestParam(value = "company_id") Long companyId,
            @RequestParam(value = "user_id") Long userId
    ) {

        String autoNumber = snSetsService.getAutoNumber(noId, companyId, userId);

        return autoNumber;
    }

}
