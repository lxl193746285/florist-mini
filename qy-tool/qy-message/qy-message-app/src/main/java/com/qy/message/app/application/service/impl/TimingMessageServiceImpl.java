package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.Regulation;
import com.qy.message.app.application.service.RuleParseService;
import com.qy.message.app.application.service.TimingMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimingMessageServiceImpl implements TimingMessageService {
    @Autowired
    private RuleParseService ruleParseService;
    //@Autowired
    //private QueueService queueService;

    @Override
    public void updateTodayTimingMessage() {
        List<Regulation> regulations = ruleParseService.ruleParse(null);
        if (regulations.isEmpty()) {
            return;
        }
        //regulations.forEach(regulation -> {
        //    SendTimingMessageJob messageJob = new SendTimingMessageJob();
        //    messageJob.setId(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()) + "-" + regulation.getTemplateId().toString() + "-" + regulation.getRuleId().toString());
        //    messageJob.setName("自动定时模板消息");
        //    messageJob.setRegulation(regulation);
        //    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //    LocalDateTime currentTime = LocalDateTime.now();
        //    LocalDateTime ldt = LocalDateTime.parse(regulation.getDateTimeStr(), df);
        //    if (ldt.isAfter(currentTime)) {
        //        queueService.push(messageJob, ldt);
        //    }
        //});
    }

    @Override
    //@Scheduled(cron = "1 0 0 * * ? ")
    public void autoUpdateTodayTimingMessage() {
        updateTodayTimingMessage();
    }
}
