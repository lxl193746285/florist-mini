package com.qy.base.interfaces.authorization.api;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * jwk内部服务接口
 *
 * @author legendjw
 */
@RestController
public class JwkSetRestController {
    private JWKSet jwkSet;

    public JwkSetRestController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }
}