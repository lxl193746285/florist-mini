package com.qy.rbac.app.application.assembler;

import com.qy.rbac.app.application.dto.MenuRuleDTO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.MenuRuleDO;
import com.qy.rbac.app.infrastructure.persistence.mybatis.dataobject.RuleScopeDO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单规则汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class MenuRuleAssembler {
    public abstract MenuRuleDTO toDTO(MenuRuleDO menuRuleDO, @Context List<RuleScopeDO> ruleScopeDOS);

    public abstract List<MenuRuleDTO> toDTO(List<MenuRuleDO> menuRuleDOs, @Context List<RuleScopeDO> ruleScopeDOS);

    @AfterMapping
    public void mapScope(MenuRuleDO menuRuleDO, @Context List<RuleScopeDO> ruleScopeDOS, MenuRuleDTO result) {
        if (ruleScopeDOS == null) { return; }
        RuleScopeDO ruleScopeDO = ruleScopeDOS.stream().filter(r -> r.getId().equals(menuRuleDO.getScopeId())).findFirst().orElse(null);
        if (ruleScopeDO == null) { return; }
        result.setIsSelectData(ruleScopeDO.getIsSelectData());
        result.setDataSource(ruleScopeDO.getDataSource());
        result.setSelectShowForm(ruleScopeDO.getSelectShowForm());
    }
}