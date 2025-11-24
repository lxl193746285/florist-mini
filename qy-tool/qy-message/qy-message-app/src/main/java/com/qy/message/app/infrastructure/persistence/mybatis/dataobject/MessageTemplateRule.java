package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import com.qy.message.app.application.enums.MessageTemplateRuleAction;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by:liuwei
 * @version 2018-08-30
 * @serial ark_msg_message_template_rule
 */
@Data
public class MessageTemplateRule implements Serializable {

    /**
     * id (主健ID) (无默认值)
     */
    private Integer id;

    /**
     * 模版id(必填项) (默认值为: 0)
     */
    private Integer templateId;

    /**
     * 周期(0：自定义1：年2：季度3：月4：周5：日6：小时7：SQL取值)(必填项) (默认值为: 0)
     */
    private Byte sendCycle;

    /**
     * 年(必填项) (默认值为: -1)
     */
    private Integer sendYear;
    private String sendYearName;

    /**
     * 季度(必填项) (默认值为: -1)
     */
    private Integer sendQuarter;
    private String sendQuarterName;
    /**
     * 月(必填项) (默认值为: -1)
     */
    private Integer sendMonth;
    private String sendMonthName;

    /**
     * 周(必填项) (默认值为: -1)
     */
    private Integer sendWeek;
    private String sendWeekName;

    /**
     * 日(必填项) (默认值为: -1)
     */
    private Integer sendDay;
    private String sendDayName ;

    /**
     * 小时(必填项) (默认值为: -1)
     */
    private Integer sendHour;
    private String sendHourName ;
    /**
     * 分钟(必填项) (默认值为: -1)
     */
    private Integer sendMinute;
    private String sendMinuteName ;

    /**
     * 系统变量(1:上班;2:下班;3:工作日)(必填项) (默认值为: )
     */
    private String systemVar;

    /**
     * 系统参数值(值可以为负数比如 上班 -5分钟 表示上班前5分钟下班 +5分钟 表示下班后5分钟)(必填项) (默认值为: 0)
     */
    private Integer systemVarValue;

    /**
     * 自定义周期(自定义必须保证返回值是返回的时间 YYYY-MM-DD HH：mm:ss 格式，否则无效)(必填项) (默认值为: )
     */
    private String customSql;

    /**
     * 时间模式(1:系统参数;2:指定参数)(必填项) (默认值为: 0)
     */
    private Byte timeMode;

    /**
     * 备注(可选项)  (默认值为: )
     */
    private String remark;

    private String sendCycleName ;

    private String systemVarName ;

    private String timeModeName ;


//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public String getSendYearName() {
//        sendYearName = getNameByType("ark_msg_message_template_rule_send_time_year", sendYear);
//        return sendYearName;
//    }
//
//    public void setSendYearName(String sendYearName) {
//        this.sendYearName = sendYearName;
//    }
//
//    public String getSendQuarterName() {
//        sendQuarterName = getNameByType("ark_msg_message_template_rule_send_time_quarter", sendQuarter);
//        return sendQuarterName;
//    }
//
//    public void setSendQuarterName(String sendQuarterName) {
//        this.sendQuarterName = sendQuarterName;
//    }
//
//    public String getSendMonthName() {
//        sendMonthName = getNameByType("ark_msg_message_template_rule_send_time_month", sendMonth);
//        return sendMonthName;
//    }
//
//    public void setSendMonthName(String sendMonthName) {
//        this.sendMonthName = sendMonthName;
//    }
//
//    public String getSendWeekName() {
//        sendWeekName = getNameByType("ark_msg_message_template_rule_send_time_week_num", sendWeek);
//        return sendWeekName;
//    }
//
//    public void setSendWeekName(String sendWeekName) {
//        this.sendWeekName = sendWeekName;
//    }
//
//    public String getSendDayName() {
//        sendDayName = getNameByType("ark_msg_message_template_rule_send_time_day", sendDay);
//        return sendDayName;
//    }
//
//    public void setSendDayName(String sendDayName) {
//        this.sendDayName = sendDayName;
//    }
//
//    public String getSendHourName() {
//        sendHourName = getNameByType("ark_msg_message_template_rule_send_time_hour", sendHour);
//        return sendHourName;
//    }
//
//    public void setSendHourName(String sendHourName) {
//        this.sendHourName = sendHourName;
//    }
//
//    public String getSendMinuteName() {
//        sendMinuteName = getNameByType("ark_msg_message_template_rule_send_time_minute", sendMinute);
//        return sendMinuteName;
//    }
//
//    public void setSendMinuteName(String sendMinuteName) {
//        this.sendMinuteName = sendMinuteName;
//    }
//
    private List<MessageTemplateRuleAction> messageTemplateParameterActions = new ArrayList<>();
//
//
//    protected static final long serialVersionUID = 1L;
//
//
//    public Integer getSendYear() {
//        return sendYear;
//    }
//
//    public void setSendYear(Integer sendYear) {
//        this.sendYear = sendYear;
//    }
//
//
//    public Integer getSendQuarter() {
//        return sendQuarter;
//    }
//
//    public void setSendQuarter(Integer sendQuarter) {
//        this.sendQuarter = sendQuarter;
//    }
//
//    public Integer getSendMonth() {
//        return sendMonth;
//    }
//
//    public void setSendMonth(Integer sendMonth) {
//        this.sendMonth = sendMonth;
//    }
//
//    public Integer getSendWeek() {
//        return sendWeek;
//    }
//
//    public void setSendWeek(Integer sendWeek) {
//        this.sendWeek = sendWeek;
//    }
//
//    public Integer getSendDay() {
//        return sendDay;
//    }
//
//    public void setSendDay(Integer sendDay) {
//        this.sendDay = sendDay;
//    }
//
//    public Integer getSendHour() {
//        return sendHour;
//    }
//
//    public void setSendHour(Integer sendHour) {
//        this.sendHour = sendHour;
//    }
//
//    public Integer getSendMinute() {
//        return sendMinute;
//    }
//
//    public void setSendMinute(Integer sendMinute) {
//        this.sendMinute = sendMinute;
//    }
//
//    public Byte getTimeMode() {
//        return timeMode;
//    }
//
//    public void setTimeMode(Byte timeMode) {
//        this.timeMode = timeMode;
//    }
//
//    public String getSendCycleName() {
//        //SystemCodeInfoExample codeInfoExample = new SystemCodeInfoExample();
//        //if (null != sendCycle) {
//        //    codeInfoExample.createCriteria().andTypeIdEqualTo("ark_msg_message_template_rule_cycle")
//        //            .andValueEqualTo(Integer.valueOf(sendCycle.toString()));
//        //    sendCycleName = systemCodeInfoMapper.selectByExample(codeInfoExample).get(0).getName();
//        //    return sendCycleName;
//        //}
//        return "";
//    }
//
//    public void setSendCycleName(String sendCycleName) {
//        this.sendCycleName = sendCycleName;
//    }
//
//    public String getSystemVarName() {
//        //SystemCodeInfoExample codeInfoExample = new SystemCodeInfoExample();
//        //if (!StringUtils.isEmpty(systemVar)) {
//        //    codeInfoExample.createCriteria()
//        //            .andTypeIdEqualTo("ark_msg_message_template_rule_system_var")
//        //            .andValueEqualTo(Integer.valueOf(systemVar));
//        //    systemVarName = systemCodeInfoMapper.selectByExample(codeInfoExample).get(0).getName();
//        //    return systemVarName;
//        //}
//        return "";
//    }
//
//    public void setSystemVarName(String systemVarName) {
//        this.systemVarName = systemVarName;
//    }
//
//
//    public String getTimeModeName() {
//        //SystemCodeInfoExample codeInfoExample = new SystemCodeInfoExample();
//        //if (null != timeMode && timeMode > 0) {
//        //    codeInfoExample.createCriteria().andTypeIdEqualTo("ark_msg_message_template_rule_time_mode")
//        //            .andValueEqualTo(Integer.valueOf(timeMode));
//        //    timeModeName = systemCodeInfoMapper.selectByExample(codeInfoExample).get(0).getName();
//        //    return timeModeName;
//        //}
//        return "";
//    }
//
//    public void setTimeModeName(String timeModeName) {
//        this.timeModeName = timeModeName;
//    }
//
//    public List<MessageTemplateRuleAction> getMessageTemplateParameterActions() {
//        return messageTemplateParameterActions;
//    }
//
//    public void setMessageTemplateParameterActions(
//            List<MessageTemplateRuleAction> messageTemplateParameterActions) {
//        this.messageTemplateParameterActions = messageTemplateParameterActions;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getTemplateId() {
//        return templateId;
//    }
//
//    public void setTemplateId(Integer templateId) {
//        this.templateId = templateId;
//    }
//
//    public Byte getSendCycle() {
//        return sendCycle;
//    }
//
//    public void setSendCycle(Byte sendCycle) {
//        this.sendCycle = sendCycle;
//    }
//
//
//    public String getSystemVar() {
//        return systemVar;
//    }
//
//    public void setSystemVar(String systemVar) {
//        this.systemVar = systemVar == null ? null : systemVar.trim();
//    }
//
//    public Integer getSystemVarValue() {
//        return systemVarValue;
//    }
//
//    public void setSystemVarValue(Integer systemVarValue) {
//        this.systemVarValue = systemVarValue;
//    }
//
//    public String getCustomSql() {
//        return customSql;
//    }
//
//    public void setCustomSql(String customSql) {
//        this.customSql = customSql == null ? null : customSql.trim();
//    }
//
//    /**
//     * 根据码表的typeId,以及value获取value数值
//     *
//     * @param value
//     * @return
//     */
//    private String getNameByType(String typeId, Integer value) {
//        if (null == value) {
//            return "";
//        }
//        //SystemCodeInfoExample systemCodeInfoExample = new SystemCodeInfoExample();
//        //systemCodeInfoExample.createCriteria().andTypeIdEqualTo(typeId).andValueEqualTo(value);
//        //String name = systemCodeInfoMapper.selectByExample(systemCodeInfoExample).get(0).getName();
//        //if (StringUtils.isEmpty(name)) {
//        //    return "";
//        //}
//        //return name;
//        return "";
//    }
}
