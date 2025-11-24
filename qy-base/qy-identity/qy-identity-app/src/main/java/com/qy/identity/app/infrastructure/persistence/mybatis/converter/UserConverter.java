package com.qy.identity.app.infrastructure.persistence.mybatis.converter;

import com.qy.identity.app.domain.entity.User;
import com.qy.identity.app.domain.enums.UserSource;
import com.qy.identity.app.domain.enums.UserStatus;
import com.qy.identity.app.domain.valueobject.*;
import com.qy.identity.app.infrastructure.persistence.mybatis.dataobject.UserDO;
import com.qy.identity.app.domain.valueobject.*;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 用户转化器
 *
 * @author legendjw
 */
@Mapper
public interface UserConverter {
    /**
     * 用户实体转化为DO类
     *
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "userId.id", target = "id"),
            @Mapping(source = "username.name", target = "username"),
            @Mapping(source = "phone.number", target = "phone"),
            @Mapping(source = "email.address", target = "email"),
            @Mapping(source = "password.passwordHash", target = "passwordHash"),
            @Mapping(source = "avatar.url", target = "avatar"),
            @Mapping(source = "status.id", target = "status"),
            @Mapping(source = "source.id", target = "source"),
            @Mapping(source = "createTime", target = "createTime"),
    })
    UserDO toDO(User user);

    /**
     * DO类转化为用户实体
     *
     * @param userDO
     * @return
     */
    default User toEntity(UserDO userDO) {
        if (userDO == null) {
            return null;
        }
        return User.builder()
                .userId(new UserId(userDO.getId()))
                .name(userDO.getName())
                .username(new Username(userDO.getUsername()))
                .phone(new PhoneNumber(userDO.getPhone()))
                .email(StringUtils.isBlank(userDO.getEmail()) ? null : new Email(userDO.getEmail()))
                .password(new Password(null, userDO.getPasswordHash()))
                .avatar(new Avatar(userDO.getAvatar()))
                .status(UserStatus.getById(userDO.getStatus()))
                .source(UserSource.getById(userDO.getSource()))
                .createTime(userDO.getCreateTime())
                .build();
    }
}