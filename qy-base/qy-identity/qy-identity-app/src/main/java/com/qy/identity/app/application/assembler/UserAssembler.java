package com.qy.identity.app.application.assembler;

import com.qy.identity.app.application.dto.UserBasicDTO;
import com.qy.identity.app.application.dto.UserDetailDTO;
import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户汇编器
 *
 * @author legendjw
 */
@Mapper
public interface UserAssembler {
    /**
     * 用户实体转为用户详情DTO
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "userId.id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "username.name", target = "username"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "email.address", target = "email"),
            @Mapping(source = "password.passwordHash", target = "passwordHash"),
            @Mapping(source = "avatar.url", target = "avatar"),
    })
    UserDetailDTO toUserDetail(User user);

    /**
     * 用户实体转为基本用户信息
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "userId.id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "avatar.url", target = "avatar"),
            @Mapping(source = "username.name", target = "username"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "email.address", target = "email"),
    })
    UserBasicDTO toBasicUser(User user);

    /**
     * 用户DO转为基本用户信息
     *
     * @param userDO
     * @return
     */
    UserBasicDTO toBasicUser(UserDO userDO);
}