package com.qy.message.app.application.service;

/**
 * 定时消息服务
 */
public interface TimingMessageService {
    /**
     * 更新当天的定时消息
     */
    void updateTodayTimingMessage();

    /**
     * 自动更新当天的定时消息
     */
    void autoUpdateTodayTimingMessage();
}
