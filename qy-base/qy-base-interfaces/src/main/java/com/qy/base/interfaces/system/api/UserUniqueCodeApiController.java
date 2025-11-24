package com.qy.base.interfaces.system.api;

import com.qy.rest.util.ResponseUtils;
import com.qy.security.session.MemberSystemSessionContext;
import com.qy.system.app.comment.BaseController;
import com.qy.system.app.useruniquecode.dto.UserUniqueCodeBasicDTO;
import com.qy.system.app.useruniquecode.dto.UserUniqueCodeFormDTO;
import com.qy.system.app.useruniquecode.service.UserUniqueCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户设备唯一码
 *
 * @author wwd
 * @since 2024-04-19
 */
@RestController
@Validated
@RequestMapping("/v4/api/system/user-unique-codes")
public class UserUniqueCodeApiController extends BaseController {
    @Autowired
    private MemberSystemSessionContext context;
    @Autowired
    private UserUniqueCodeService userUniqueCodeService;

    /**
     * 创建单个用户设备唯一码
     *
     * @param userUniqueCodeFormDTO
     * @return
     */
    @PostMapping
    public void createUserUniqueCode(
        @RequestBody UserUniqueCodeFormDTO userUniqueCodeFormDTO
    ) {
        userUniqueCodeService.createUserUniqueCode(userUniqueCodeFormDTO);
    }

    @GetMapping
    public UserUniqueCodeBasicDTO getUserUniqueCode(
        @RequestParam(value = "member_id") Long memberId,
        @RequestParam(value = "org_id") Long orgId
    ) {
        return userUniqueCodeService.getUserUniqueCodeDTO(memberId, orgId);
    }
}

