package com.qy.codetable.app.application.assembler;

import com.qy.codetable.app.application.dto.CodeTableCategoryBasicDTO;
import com.qy.codetable.app.application.dto.CodeTableCategoryDTO;
import com.qy.codetable.app.application.security.CodeTableCategoryPermission;
import com.qy.codetable.app.infrastructure.persistence.mybatis.dataobject.CodeTableCategoryDO;
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
 * 代码表分类汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class CodeTableCategoryAssembler {
    @Mapping(source = "codeTableCategoryDO", target = "actions", qualifiedByName = "mapActions")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    public abstract CodeTableCategoryDTO toDTO(CodeTableCategoryDO codeTableCategoryDO, @Context Identity identity);

    public abstract List<CodeTableCategoryDTO> toDTOs(List<CodeTableCategoryDO> codeTableCategoryDOs, @Context Identity identity);

    public abstract List<CodeTableCategoryBasicDTO> toBasicDTO(List<CodeTableCategoryDO> codeTableCategoryDO);

    @Named("mapActions")
    protected List<Action> mapActions(CodeTableCategoryDO codeTableCategoryDO, @Context Identity identity) {
        List<Action> actions = new ArrayList<>();
        actions.add(CodeTableCategoryPermission.UPDATE.toAction());
        actions.add(CodeTableCategoryPermission.DELETE.toAction());
        return actions;
    }
}
