package com.qy.codetable.app.application.assembler;

import com.qy.codetable.app.application.dto.CodeTableDefaultItemDTO;
import com.qy.codetable.app.application.security.CodeTableItemPermission;
import com.qy.codetable.app.infrastructure.persistence.CodeTableDefaultItemDataRepository;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDefaultItemDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码表默认细项汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class CodeTableDefaultItemAssembler {
    @Autowired
    private CodeTableDefaultItemDataRepository codeTableDefaultItemDataRepository;

    @Mapping(source = "parentId", target = "parentName", qualifiedByName = "mapParent")
    @Mapping(source = "codeTableDefaultItemDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public CodeTableDefaultItemDTO toDTO(CodeTableDefaultItemDO codeTableDefaultItemDO, @Context Identity identity);

    @Named("mapParent")
    protected String mapParent(Long parentId) {
        if (parentId.longValue() == 0L) { return ""; }
        CodeTableDefaultItemDO codeTableItemDO = codeTableDefaultItemDataRepository.findById(parentId);
        return codeTableItemDO != null ? codeTableItemDO.getName() : "";
    }

    @Named("mapActions")
    protected List<Action> mapActions(CodeTableDefaultItemDO codeTableDefaultItemDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        actions.add(CodeTableItemPermission.UPDATE.toAction());
        actions.add(CodeTableItemPermission.DELETE.toAction());
        return actions;
    }
}
