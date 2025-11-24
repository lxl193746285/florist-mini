package com.qy.workflow.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.security.permission.action.Action;
import com.qy.workflow.api.dto.RWActionDTO;
import com.qy.workflow.api.dto.RWActionFormDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 授权客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-tool", contextId = "arkcsd-workflow", configuration = FeignTokenRequestInterceptor.class)
public interface RunWfClient {
    /**
     * 获取工作流指定节点的操作按钮
     *
     * runNodeId 指定节点id，不指定则返回空
     * @return
     */
    @GetMapping("/v4/wf/run-wfs/api/wf-action/{id}")
    List<Action> getRunWfActionsList(
            @PathVariable(value = "id") Long runNodeId
    );

    /**
     * 获取多个工作流节点操作按钮的api
     *
     * actionFormDTO
     * @return
     */
    @PostMapping("/v4/wf/run-wfs/api/wf-actions")
    List<RWActionDTO> getRunWfExistActionsList(
            @Valid @RequestBody RWActionFormDTO actionFormDTO
    );
}
