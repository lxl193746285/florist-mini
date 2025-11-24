package com.qy.codetable.app.application.assembler;

import com.qy.codetable.app.application.dto.CodeTableBasicDTO;
import com.qy.codetable.app.application.dto.CodeTableDTO;
import com.qy.codetable.app.application.security.CodeTablePermission;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import com.qy.security.session.Identity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码表汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class CodeTableAssembler {
    @Mapping(source = "codeTableDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    abstract public CodeTableDTO toDTO(CodeTableDO codeTableDO, @Context Identity identity);

    abstract public CodeTableBasicDTO toBasicDTO(CodeTableDO codeTableDO);

    @Named("mapActions")
    protected List<Action> mapActions(CodeTableDO codeTableDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        actions.add(CodeTablePermission.UPDATE.toAction());
        actions.add(CodeTablePermission.DELETE.toAction());
        return actions;
    }
}
