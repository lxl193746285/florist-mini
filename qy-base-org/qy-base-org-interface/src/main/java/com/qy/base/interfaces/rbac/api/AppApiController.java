package com.qy.base.interfaces.rbac.api;

import com.qy.rbac.app.application.dto.AppBasicDTO;
import com.qy.rbac.app.application.service.AppCommandService;
import com.qy.rbac.app.application.service.AppQueryService;
import com.qy.security.session.SessionContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应用
 *
 * @author legendjw
 */
@RestController
@RequestMapping("/v4/api/rbac/apps")
public class AppApiController {
    private SessionContext sessionContext;
    private AppCommandService appCommandService;
    private AppQueryService appQueryService;

    public AppApiController(SessionContext sessionContext, AppCommandService appCommandService, AppQueryService appQueryService) {
        this.sessionContext = sessionContext;
        this.appCommandService = appCommandService;
        this.appQueryService = appQueryService;
    }

    /**
     * 获取应用列表
     *
     * @return
     */
    @GetMapping
    public List<AppBasicDTO> getAppByIds(
            @RequestParam(value = "app_ids") List<Long> appIds) {
        return appQueryService.getBasicAppsByIds(appIds);
    }
}

