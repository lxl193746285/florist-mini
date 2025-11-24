package com.qy.member.app.interfaces.client;

import com.qy.member.app.application.command.CreateMemberClientCommand;
import com.qy.member.app.application.dto.MemberClientDTO;
import com.qy.member.app.application.service.MemberClientService;
import com.qy.security.session.MemberSystemSessionContext;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员客户端
 *
 * @author legendjw
 */
@RestController
@Api(tags = "会员账号")
@RequestMapping(value = "/v4/mbr/member-client")
public class MemberClientController {
    private MemberClientService memberClientService;
    private MemberSystemSessionContext sessionContext;

    public MemberClientController(MemberClientService memberClientService, MemberSystemSessionContext sessionContext) {
        this.memberClientService = memberClientService;
        this.sessionContext = sessionContext;
    }

    /**
     * 设置会员客户端
     *
     * @param command
     */
    @PostMapping(value = "/set")
    public void createBatch(
            @RequestBody CreateMemberClientCommand command) {
        memberClientService.createBatch(command, sessionContext.getMember());
    }

    /**
     * 获取会员客户端
     *
     * @return
     */
    @GetMapping
    public List<MemberClientDTO> getMemberClients(
            @RequestParam(value = "memberId") Long memberId
    ) {
        return memberClientService.getMemberClients(memberId);
    }

}