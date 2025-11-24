package com.qy.identity.app.domain.valueobject;

import com.qy.ddd.interfaces.ValueObject;
import com.qy.identity.app.domain.password.PasswordAtLeastEightDigitValidator;
import com.qy.identity.app.domain.password.PasswordValidator;
import com.qy.rest.exception.ValidationException;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码
 *
 * @author legendjw
 */
@Data
public class Password implements ValueObject {
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String password;
    private String passwordHash;

    /**
     * 默认密码
     *
     * @return
     */
    public static Password defaultPassword() {
        return new Password("12345678");
    }

    public Password(String password) {
        validate(password);
        this.password = password;
    }

    public Password(String password, String passwordHash) {
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public Password(String password, PasswordEncoder passwordEncoder) {
        validate(password);
        this.password = password;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 获取hash后的密码
     *
     * @return
     */
    public String getPasswordHash() {
        if (passwordHash == null) {
            passwordHash = passwordEncoder.encode(this.password);
        }
        return passwordHash;
    }

    /**
     * 验证密码是否一致
     *
     * @param password
     * @return
     */
    public boolean matches(String password) {
        return passwordEncoder.matches(password, passwordHash);
    }

    /**
     * 验证密码是否符合密码规则
     *
     * @param password
     */
    public void validate(String password) {
        PasswordValidator passwordValidator = new PasswordAtLeastEightDigitValidator();
        if (!passwordValidator.validate(password)) {
            throw new ValidationException(passwordValidator.getRequireHint());
        }
    }
}