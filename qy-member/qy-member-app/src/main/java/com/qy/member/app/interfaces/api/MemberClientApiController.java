package com.qy.member.app.interfaces.api;

import com.qy.member.app.application.command.*;
import com.qy.member.app.application.dto.*;
import com.qy.member.app.application.query.MemberQuery;
import com.qy.member.app.application.service.MemberClientService;
import com.qy.member.app.application.service.MemberCommandService;
import com.qy.member.app.application.service.MemberQueryService;
import com.qy.rest.pagination.Page;
import com.qy.rest.pagination.SimplePageImpl;
import com.qy.rest.util.ResponseUtils;
import com.qy.rest.util.ValidationUtils;
import com.qy.security.session.Identity;
import com.qy.security.session.SessionContext;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员内部服务
 *
 * @author legendjw
 */
@RestController
@RequestMapping(value = "/v4/api/mbr/member-client")
public class MemberClientApiController {
    private MemberClientService memberClientService;

    public MemberClientApiController(MemberClientService memberClientService) {
        this.memberClientService = memberClientService;
    }

    @GetMapping
    public List<MemberClientDTO> getMemberClients(Long memberId) {
        return memberClientService.getMemberClients(memberId);
    }
}