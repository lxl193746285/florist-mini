package com.qy.authorization.app.application.service;

import com.qy.security.session.ClientAndJti;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 同一账号同一客户端只能保持一个在线登录
 *
 * @author legendjw
 */
@Component
public class SameAccountLoginLimit {
    List<String> sameClients = Arrays.asList("android", "ios");
    private RedisTemplate redisTemplate;

    public SameAccountLoginLimit(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 保存最新的用户token
     *
     * @param contextId
     * @param userId
     * @param clientId
     * @param jti
     */
    public void storeUserToken(String contextId, String userId, String clientId, String jti) {
        ValueOperations<String, List<ClientAndJti>> valueOperations = redisTemplate.opsForValue();
        String userTokenKey = getUserTokenKey(contextId, userId);
        if (redisTemplate.hasKey(userTokenKey)) {
            List<ClientAndJti> clientAndJtis = valueOperations.get(userTokenKey);
            ClientAndJti clientAndJti = new ClientAndJti(clientId, jti);
            if (clientAndJtis.stream().anyMatch(c -> c.getClientId().equals(clientId) || (sameClients.contains(clientId) && sameClients.contains(c.getClientId())))) {
                clientAndJtis = clientAndJtis.stream().map(c -> {
                    if (c.getClientId().equals(clientId) || (sameClients.contains(clientId) && sameClients.contains(c.getClientId()))) {
                        return clientAndJti;
                    }
                    else {
                        return c;
                    }
                }).collect(Collectors.toList());
            }
            else {
                clientAndJtis.add(clientAndJti);
            }

            valueOperations.set(userTokenKey, clientAndJtis);
        }
        else {
            ClientAndJti clientAndJti = new ClientAndJti(clientId, jti);
            List<ClientAndJti> clientAndJtis = new ArrayList<>();
            clientAndJtis.add(clientAndJti);
            valueOperations.set(userTokenKey, clientAndJtis);
        }
    }

    /**
     * 验证指定的刷新token是否是最新的
     *
     * @param contextId
     * @param userId
     * @param clientId
     * @param ati
     * @return
     */
    public boolean validateRefreshToken(String contextId, String userId, String clientId, String ati) {
        ValueOperations<String, List<ClientAndJti>> valueOperations = redisTemplate.opsForValue();
        String userTokenKey = getUserTokenKey(contextId, userId);
        if (!redisTemplate.hasKey(userTokenKey)) {
            return true;
        }
        List<ClientAndJti> clientAndJtis = valueOperations.get(userTokenKey);
        ClientAndJti clientAndJti = clientAndJtis.stream().filter(c -> c.getClientId().equals(clientId)).findFirst().orElse(null);
        if (clientAndJti == null) { return false; }
        return clientAndJti.getJti().equals(ati);
    }

    /**
     * 获取用户token存储的key
     *
     * @param contextId
     * @param userId
     * @return
     */
    private String getUserTokenKey(String contextId, String userId) {
        return String.format("%s_token_%s", contextId, userId);
    }
}
