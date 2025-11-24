package com.qy.crm.customer.app.application.listener;

import com.qy.crm.customer.app.application.service.CustomerCommandService;
import com.qy.identity.api.client.UserClient;
import com.qy.identity.api.dto.UserBasicDTO;
import com.qy.identity.api.enums.UserSource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户消息监听
 *
 * @author legendjw
 */
@Component
public class UserCreatedListener {
    private UserClient userClient;
    private CustomerCommandService customerCommandService;

    public UserCreatedListener(UserClient userClient, CustomerCommandService customerCommandService) {
        this.userClient = userClient;
        this.customerCommandService = customerCommandService;
    }

    /**
     * 创建用户对应的客户
     *
     * @param event
     */
    @Async
    @EventListener
    public void createUserCustomer(UserCreatedEvent event) {
        if (event.getCreateSource().equals(UserSource.REGISTER.getId())) {
            UserBasicDTO userBasicDTO = userClient.getBasicUserById(event.getUserId());
            customerCommandService.createUserCustomer(userBasicDTO);
        }
    }
}
