package com.qy.member.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.member.api.command.*;
import com.qy.member.api.dto.*;
import com.qy.member.api.query.MemberQuery;
import com.qy.rest.pagination.SimplePageImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-member", contextId = "qy-member-member", configuration = FeignTokenRequestInterceptor.class)
public interface MemberClient {
    /**
     * 获取会员
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/mbr/members")
    SimplePageImpl<MemberDTO> getMembers(@SpringQueryMap MemberQuery query);

    /**
     * 根据id获取基本会员信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/v4/api/mbr/members/basic-members/{id}")
    MemberBasicDTO getBasicMember(
            @PathVariable(value = "id") Long id
    );

    /**
     * 批量获取会员的基本信息
     *
     * @param ids 会员id集合
     * @return
     */
    @GetMapping("/v4/api/mbr/members/basic-members")
    List<MemberBasicDTO> getBasicMembers(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 根据id获取会员详细信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/v4/api/mbr/members/detail-members/{id}")
    MemberDetailDTO getDetailMember(
            @PathVariable(value = "id") Long id
    );

    /**
     * 获取开通会员信息
     *
     * @param id 会员id
     * @return
     */
    @GetMapping("/v4/api/mbr/members/{id}/open-member-info")
    OpenMemberInfoDTO getOpenMemberInfo(
            @PathVariable(value = "id") Long id
    );

    /**
     * 开通会员
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/open-member")
    MemberIdDTO openMember(
            @Valid @RequestBody OpenMemberCommand command
    );

    /**
     * 修改开通会员
     *
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/mbr/members/open-member")
    void updateOpenMember(
            @Valid @RequestBody UpdateOpenMemberCommand command
    );

    /**
     * 绑定会员开户组织
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/bind-member-open-account")
    void bindMemberOpenAccount(
            @Valid @RequestBody BindMemberOpenAccountCommand command
    );

    /**
     * 创建会员
     *
     * @param command
     */
    @PostMapping("/v4/api/mbr/members")
    MemberIdDTO createMember(
            @Valid @RequestBody CreateMemberCommand command
    );

    /**
     * 修改会员
     *
     * @param command
     */
    @PatchMapping("/v4/api/mbr/members")
    void updateMember(
            @Valid @RequestBody UpdateMemberCommand command
    );

    /**
     * 删除会员
     *
     * @param id 会员id
     */
    @DeleteMapping("/v4/api/mbr/members")
    ResponseEntity<Object> deleteMember(
            @RequestParam(value = "id") Long id
    );

    /**
     * 修改会员的等级
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/mbr/members/{id}/level")
    void modifyMemberLevel(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ModifyMemberLevelCommand command
    );

    /**
     * 门店审核
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/{id}/store-audit")
    void storeAuditMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuditMemberCommand command
    );

    /**
     * 平台审核
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/{id}/platform-audit")
    void platformAuditMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AuditMemberCommand command
    );


    /**
     * 获取会员
     *
     * @param query
     * @return
     */
    @GetMapping("/v4/api/mbr/members/basic-members/system-member")
    MemberBasicDTO getBasicSystemMember(@SpringQueryMap MemberQuery query);


    /**
     * 获取会员
     *
     * @return
     */
    @GetMapping("/v4/api/mbr/members/basic-members/account-system-member")
    MemberBasicDTO getBasicAccountSystemMember(
            @RequestParam(value = "account_id") Long accountId,
            @RequestParam(value = "system_id") Long systemId,
            @RequestParam(value = "org_id") Long orgId);

    /**
     * 根据accountId获取会员列表
     *
     * @param accountId
     * @return
     */
    @GetMapping("/v4/api/mbr/members/basic-members/member-by-account/{accountId}")
    List<MemberBasicDTO> getMembersByAccount(@PathVariable(value = "accountId") Long accountId);

    @GetMapping("/v4/api/mbr/members/basic-members/member-by-account-system")
    List<MemberBasicDTO> getMembersByAccountAndSystemId(
            @RequestParam(value = "accountId") Long accountId,
            @RequestParam(value = "systemId") Long systemId

    );

    /**
     * 授权权限组给会员
     *
     * @param id
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/{id}/assign-role")
    public ResponseEntity<Object> assignRoleToMember(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody AssignRoleToMemberCommand command
    );


    /**
     * 开通会员
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/open-member-without-account")
    MemberIdDTO openMemberWithoutAccount(
            @Valid @RequestBody OpenMemberCommand command
    );


    /**
     * 开通会员
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/members/open-member-account")
    void openMemberAccount(
            @Valid @RequestBody OpenMemberCommand command
    );

}