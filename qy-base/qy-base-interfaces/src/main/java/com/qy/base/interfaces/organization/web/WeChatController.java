package com.qy.base.interfaces.organization.web;

import com.qy.organization.app.domain.entity.ResDto;
import com.qy.organization.app.domain.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 微信服务
 */
@RestController
@RequestMapping("/v4/employee/wechat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    /**
     * 获取用户信息
     *
     * @param response
     * @return
     */
    @GetMapping("/wxuser")
    public ResDto getWxUser(
            @RequestParam(name = "code") String code,
            HttpServletResponse response
    ) {
        ResDto resDto = weChatService.getUserInfo(code);

        return resDto;
    }

}
