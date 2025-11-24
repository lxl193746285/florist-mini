package com.qy.codetable.app.application.assembler;

import com.qy.codetable.app.application.dto.CodeTableItemBasicDTO;
import com.qy.codetable.app.application.dto.CodeTableItemDTO;
import com.qy.codetable.app.application.security.CodeTableItemPermission;
import com.qy.codetable.app.infrastructure.persistence.CodeTableItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableItemDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码表细项汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class CodeTableItemAssembler {
    @Autowired
    private CodeTableItemDataRepository codeTableItemDataRepository;

    @Mapping(source = "parentId", target = "parentName", qualifiedByName = "mapParent")
    @Mapping(source = "codeTableItemDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public CodeTableItemDTO toDTO(CodeTableItemDO codeTableItemDO, @Context Identity identity);

    @Mappings(
            @Mapping(source = "value", target = "id")
    )
    abstract public CodeTableItemBasicDTO toClientDTO(CodeTableItemDO codeTableItemDO);

    @Named("mapParent")
    protected String mapParent(Long parentId) {
        if (parentId.longValue() == 0L) { return ""; }
        CodeTableItemDO codeTableItemDO = codeTableItemDataRepository.findById(parentId);
        return codeTableItemDO != null ? codeTableItemDO.getName() : "";
    }

    @Named("mapActions")
    protected List<Action> mapActions(CodeTableItemDO codeTableItemDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        actions.add(CodeTableItemPermission.UPDATE.toAction());
        actions.add(CodeTableItemPermission.DELETE.toAction());
        return actions;
    }
}
