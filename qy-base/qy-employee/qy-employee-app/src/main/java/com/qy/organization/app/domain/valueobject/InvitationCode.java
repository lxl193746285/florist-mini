package com.qy.organization.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 邀请码
 *
 * @author legendjw
 */
@Getter
public class InvitationCode implements ValueObject {
    /**
     * 邀请码
     */
    private String code;

    public InvitationCode() {
        this.code = RandomStringUtils.randomAlphanumeric(128);
    }

    public InvitationCode(String code) {
        this.code = code;
    }
}
