package com.qy.common.service;

import com.qy.codetable.api.client.CodeTableClient;
import com.qy.codetable.api.dto.CodeTableItemBasicDTO;
import com.qy.common.enums.CommonEnumCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArkCodeTableService {

    @Autowired
    private CodeTableClient codeTableClient;

    /**
     * @param companyId
     * @param value
     * @return
     */
    public String getGoodsUnitName(Long companyId, Integer value) {
        return codeTableClient.getOrganizationCodeTableItemName(companyId, "store_psi_unit", value + "");
    }

    public String getNameBySystem(String code, Integer value) {
        return codeTableClient.getSystemCodeTableItemName(code, value + "");
    }

    public String getNameBySystemStrValue(String code, String value) {
        return codeTableClient.getSystemCodeTableItemName(code, value + "");
    }

    /**
     * 组织自己的代码表
     *
     * @param companyId
     * @param code
     * @return
     */
    public Map<Integer, String> getOrganizationByCode(Long companyId, String code) {
        final List<CodeTableItemBasicDTO> allCodeTableItems = codeTableClient.getOrganizationBasicCodeTableItems(companyId, code);
        final Map<Integer, String> valueToName = allCodeTableItems.stream().collect(Collectors.toMap(p -> Integer.parseInt(p.getId()), p -> p.getName()));

        return valueToName;
    }

    public LinkedHashMap<Integer, String> getSystemListByCode(String code) {
        final List<CodeTableItemBasicDTO> allCodeTableItems = codeTableClient.getSystemBasicCodeTableItems(code);
        final LinkedHashMap<Integer, String> valueToName = new LinkedHashMap<>();
        for (CodeTableItemBasicDTO dto : allCodeTableItems) {
            valueToName.put(Integer.parseInt(dto.getId()), dto.getName());
        }

        return valueToName;
    }

    public LinkedHashMap<String, String> getSystemListByCodeStr(String code) {
        final List<CodeTableItemBasicDTO> allCodeTableItems = codeTableClient.getSystemBasicCodeTableItems(code);
        final LinkedHashMap<String, String> valueToName = new LinkedHashMap<>();
        for (CodeTableItemBasicDTO dto : allCodeTableItems) {
            valueToName.put(dto.getId(), dto.getName());
        }

        return valueToName;
    }

    /**
     * 公共审核状态（-1：草稿；0：待审核；1：通过；2：不通过）
     *
     * @return
     */
    public Map<Integer, String> getCommonAuditStatus() {
        return getSystemListByCode(CommonEnumCode.AUDIT_STATUS.getCode());
    }


    /**
     * 公共的状态（1：启用，0：禁用）
     *
     * @return
     */
    public Map<Integer, String> getCommonStatus() {
        return getSystemListByCode(CommonEnumCode.STATUS.getCode());
    }

}
