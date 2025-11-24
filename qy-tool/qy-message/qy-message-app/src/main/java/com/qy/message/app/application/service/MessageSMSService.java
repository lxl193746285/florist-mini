package com.qy.message.app.application.service;

import com.qy.message.app.application.command.SendMessageSMSCommand;
import com.qy.message.app.domain.sender.SendSMSResult;

import java.util.List;
import java.util.Map;

public interface MessageSMSService {

    SendSMSResult sendSMS(SendMessageSMSCommand command);

}
