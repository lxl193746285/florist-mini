package com.qy.codetable.api.client;

import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.feign.config.FeignTokenRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 代码表客户端
 *
 * @author legendjw
 */
@FeignClient(name = "qy-base", contextId = "qy-system-code-table")
public interface CodeTableClient {
    /**
     * 获取指定系统代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/system/codes/items")
    List<CodeTableItemBasicDTO> getSystemBasicCodeTableItems(
            @RequestParam(name = "code") String code
    );

    /**
     * 获取指定组织下代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/organization/codes/items")
    List<CodeTableItemBasicDTO> getOrganizationBasicCodeTableItems(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code
    );

    /**
     * 获取指定组织下代码表的所有细项
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/personal/codes/items")
    List<CodeTableItemBasicDTO> getPersonalBasicCodeTableItems(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code
    );

    /**
     * 获取系统代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/system/codes/item")
    CodeTableItemBasicDTO getSystemCodeTableItem(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );

    /**
     * 获取系统代码表细项名称
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/system/codes/item-name")
    String getSystemCodeTableItemName(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );

    /**
     * 获取组织代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/organization/codes/item")
    CodeTableItemBasicDTO getOrganizationCodeTableItem(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );

    /**
     * 获取组织代码表细项名称
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/organization/codes/item-name")
    String getOrganizationCodeTableItemName(
            @RequestParam(name = "organization_id") Long organizationId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );

    /**
     * 获取个人代码表细项信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/personal/codes/item")
    CodeTableItemBasicDTO getPersonalCodeTableItem(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );

    /**
     * 获取个人代码表细项名称
     *
     * @param code
     * @return
     */
    @GetMapping("/v4/api/code-tables/personal/codes/item-name")
    String getPersonalCodeTableItemName(
            @RequestParam(name = "user_id") Long userId,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "value") String value
    );
}
