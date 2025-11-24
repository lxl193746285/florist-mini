package com.qy.attachment.app.application.assembler;

import com.qy.attachment.app.application.dto.AttachmentThumbConfigDTO;
import com.qy.attachment.app.infrastructure.persistence.security.AttachmentConfigPermission;
import com.qy.attachment.app.infrastructure.persistence.mybatis.dataobject.AttachmentThumbConfigDO;
import com.qy.rest.constant.DateTimeFormatConstant;
import com.qy.security.permission.action.Action;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 缩略图配置汇编器
 *
 * @author legendjw
 */
@Mapper
public abstract class ThumbConfigAssembler {
    @Mapping(source = "uploadImageMaxSize", target = "uploadImageMaxSize", qualifiedByName = "mapKB")
    @Mapping(source = "uploadFileMaxSize", target = "uploadFileMaxSize", qualifiedByName = "mapKB")
    @Mapping(source = "uploadVideoMaxSize", target = "uploadVideoMaxSize", qualifiedByName = "mapKB")
    @Mapping(source = "sizeThreshold", target = "sizeThreshold", qualifiedByName = "mapKB")
    @Mapping(source = "uploadImageFormatLimit", target = "uploadImageFormatLimit", qualifiedByName = "mapList")
    @Mapping(source = "uploadFileFormatLimit", target = "uploadFileFormatLimit", qualifiedByName = "mapList")
    @Mapping(source = "uploadVideoFormatLimit", target = "uploadVideoFormatLimit", qualifiedByName = "mapList")
    @Mapping(source = "createTime", target = "createTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "updateTime", target = "updateTimeName", dateFormat = DateTimeFormatConstant.DEFAULT_DATE_TIME_FORMAT)
    @Mapping(source = "attachmentThumbConfigDO", target = "actions", qualifiedByName = "mapActions")
    public abstract AttachmentThumbConfigDTO toDTO(AttachmentThumbConfigDO attachmentThumbConfigDO);

    @Named("mapKB")
    public float mapKB(Integer m) {
        return new BigDecimal(m).divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Named("mapList")
    public List<String> mapList(String list) {
        if (StringUtils.isBlank(list)) {
            return new ArrayList<>();
        }
        return Arrays.asList(list.split(","));
    }

    @Named("mapActions")
    protected List<Action> mapActions(AttachmentThumbConfigDO attachmentThumbConfigDO) {
        List<Action> actions = new ArrayList<>();
        actions.add(AttachmentConfigPermission.VIEW.toAction());
        actions.add(AttachmentConfigPermission.UPDATE.toAction());
        actions.add(AttachmentConfigPermission.DELETE.toAction());
        return actions;
    }
}
