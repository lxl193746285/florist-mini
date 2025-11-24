package com.qy.member.api.client;

import com.qy.feign.config.FeignTokenRequestInterceptor;
import com.qy.member.api.command.CreateMemberSystemCommand;
import com.qy.member.api.command.MemberSystemAuthorizationCommand;
import com.qy.member.api.command.UpdateMemberSystemCommand;
import com.qy.member.api.dto.MemberSystemBasicDTO;
import com.qy.member.api.dto.MemberSystemBasicExtendDTO;
import com.qy.member.api.dto.MemberSystemIdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员系统客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-member", contextId = "qy-member-system", configuration = FeignTokenRequestInterceptor.class)
public interface MemberSystemClient {
    /**
     * 根据ID查询基本会员系统
     *
     * @param id 会员系统id
     * @return
     */
    @GetMapping("/v4/api/mbr/member-systems/basic-member-system/{id}")
    MemberSystemBasicDTO getBasicMemberSystemById(
            @PathVariable(value = "id") Long id
    );

    /**
     * 根据IDs查询基本会员系统
     *
     * @param ids 会员系统ids
     * @return
     */
    @GetMapping("/v4/api/mbr/member-systems/basic-member-system/by-ids")
    List<MemberSystemBasicDTO> getBasicMemberSystemsByIds(
            @RequestParam(value = "ids") List<Long> ids
    );

    /**
     * 查询组织下的基本会员系统
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping("/v4/api/mbr/member-systems/basic-member-systems")
    List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationId(
            @RequestParam(value = "organization_id") Long organizationId
    );

    /**
     * 查询组织下的基本会员系统扩展
     *
     * @param organizationId 组织id
     * @return
     */
    @GetMapping("/v4/api/mbr/member-systems/basic-member-systems-extend")
    List<MemberSystemBasicExtendDTO> getBasicMemberSystemsExtendByOrganizationId(
            @RequestParam(value = "organization_id") Long organizationId
    );

    /**
     * 查询组织和指定来源下的基本会员系统
     *
     * @param organizationId 组织id
     * @param source 来源
     * @return
     */
    @GetMapping("/v4/api/mbr/member-systems/basic-member-systems/by-source")
    List<MemberSystemBasicDTO> getBasicMemberSystemsByOrganizationIdAndSource(
            @RequestParam(value = "organization_id") Long organizationId,
            @RequestParam(value = "source") Integer source
    );

    /**
     * 创建单个会员系统
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/member-systems")
    MemberSystemIdDTO createMemberSystem(
            @Valid @RequestBody CreateMemberSystemCommand command
    );

    /**
     * 修改单个会员系统
     *
     * @param id
     * @param command
     * @return
     */
    @PatchMapping("/v4/api/mbr/member-systems/{id}")
    void updateMemberSystem(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UpdateMemberSystemCommand command
    );

    /**
     * 删除单个会员系统
     *
     * @param id
     */
    @DeleteMapping("/v4/api/mbr/member-systems/{id}")
    void deleteMemberSystem(
            @PathVariable(value = "id") Long id
    );

    /**
     * 删除单个会员系统
     *
     * @param id
     */
    @DeleteMapping("/v4/api/mbr/member-systems/test")
    void test(
            @PathVariable(value = "id") Long id
    );

    /**
     * 创建会员系统组织关系
     *
     * @param command
     * @return
     */
    @PostMapping("/v4/api/mbr/member-systems/organization")
    MemberSystemIdDTO createMemberSystemOrganization(
            @Valid @RequestBody MemberSystemAuthorizationCommand command
    );

    /**
     * 删除会员系统组织关系
     *
     * @param organizationId
     * @return
     */
    @DeleteMapping("/v4/api/mbr/member-systems/organization/{id}")
    MemberSystemIdDTO deleteMemberSystemOrganization(
            @PathVariable(value = "id") Long organizationId
    );

}