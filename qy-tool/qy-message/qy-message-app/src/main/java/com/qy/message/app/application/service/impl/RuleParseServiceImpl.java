package com.qy.message.app.application.service.impl;

import com.qy.message.app.application.dto.Regulation;
import com.qy.message.app.application.service.MessageTemplateRuleService;
import com.qy.message.app.application.service.RuleParseService;
import com.qy.message.app.application.service.SqlService;
import com.qy.message.app.infrastructure.persistence.mybatis.dataobject.MessageTemplateRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleParseServiceImpl implements RuleParseService {
	@Autowired
	private MessageTemplateRuleService messageTemplateRuleService;
	@Autowired
	private SqlService sqlService;

	public List<Regulation> ruleParse(Integer templateId) {
	    return new ArrayList<>();
		// 周期(0：自定义1：年2：季度3：月4：周5：日6：小时7：SQL取值)
		//List<MessageTemplateRule> messageTemplateRules = messageTemplateRuleService.getAllMessageTemplateRules(templateId);
		////if (CollectionUtils.isNotEmpty(messageTemplateRules)) {
		//	DateTime dateTime = new DateTime();
		//	StringBuffer stringBuffer = new StringBuffer();
		//	List<Regulation> regulations = new ArrayList<Regulation>();
		//	messageTemplateRules.stream().forEach(r -> {
		//		Regulation regulation = new Regulation();
		//		stringBuffer.append(DateUtils.nowDatestr());
		//		if (r.getSendCycle() == 7) {
		//			regulation.setDateTimeStr(sqlService.getContentMsg(r.getCustomSql()));
		//			regulation.setTemplateId(r.getTemplateId());
		//			regulation.setRuleId(r.getId());
		//			regulations.add(regulation);
		//		}
		//		if (r.getSendCycle() == 6) {
		//			if (r.getTimeMode() == 1) {

		//			} else
		//				time(stringBuffer, regulations, r, regulation);
		//		}
		//		if (r.getSendCycle() == 5) {
		//			int day = dateTime.getDayOfMonth();
		//			if (day == r.getSendDay() || r.getSendDay() <= 0) {
		//				if (r.getTimeMode() == 1) {

		//				} else
		//					time(stringBuffer, regulations, r, regulation);
		//			}

		//		}
		//		if (r.getSendCycle() == 4) {
		//			int week = dateTime.getDayOfWeek();
		//			if (week == r.getSendWeek() || r.getSendWeek() <= 0) {
		//					time(stringBuffer, regulations, r, regulation);
		//			}
		//		}
		//		if (r.getSendCycle() == 3) {
		//			int Month = dateTime.getMonthOfYear();
		//			if (Month == r.getSendMonth() || r.getSendMonth() <= 0) {
		//				int day = dateTime.getDayOfMonth();
		//				if (day == r.getSendDay() || r.getSendDay() <= 0) {
		//					if (r.getTimeMode() == 1) {

		//					} else
		//						time(stringBuffer, regulations, r, regulation);
		//				}
		//			}

		//		}
		//		if (r.getSendCycle() == 2) {
		//			int Month = dateTime.getMonthOfYear();
		//			if (quarter(r.getSendQuarter(), Month) || r.getSendQuarter() <= 0) {
		//				int day = dateTime.getDayOfMonth();
		//				if (day == r.getSendDay() || r.getSendDay() <= 0) {
		//					if (r.getTimeMode() == 1) {

		//					} else
		//						time(stringBuffer, regulations, r, regulation);
		//				}
		//			}

		//		}
		//		if (r.getSendCycle() == 1) {
		//			int year = dateTime.getYear();
		//			if (year == r.getSendYear() || r.getSendYear() <= 0) {
		//				int Month = dateTime.getMonthOfYear();
		//				if (Month == r.getSendMonth() || r.getSendMonth() <= 0) {
		//					int day = dateTime.getDayOfMonth();
		//					if (day == r.getSendDay() || r.getSendDay() <= 0) {
		//						if (r.getTimeMode() == 1) {

		//						} else
		//							time(stringBuffer, regulations, r, regulation);
		//					}
		//				}
		//			}

		//		}
		//		if (r.getSendCycle() == 0) {
		//			if (r.getCustomSql().contains(DateUtils.nowDatestr())) {
		//				stringBuffer.setLength(0);
		//				stringBuffer.append(r.getCustomSql());
		//			} else {
		//				stringBuffer.setLength(0);
		//			}
		//			regulation.setTemplateId(r.getTemplateId());
		//			regulation.setDateTimeStr(stringBuffer.toString());
		//			regulation.setRuleId(r.getId());
		//			regulations.add(regulation);
		//		}
		//		stringBuffer.setLength(0);
		//	});
		//	return regulations;
		//} else {
		//	return null;
		//}

	}

	/**
	 * @param stringBuffer
	 * @param regulations
	 * @param r
	 * @param regulation
	 */
	private void time(StringBuffer stringBuffer, List<Regulation> regulations, MessageTemplateRule r,
			Regulation regulation) {
		if (r.getSendHour() < 10) {
			stringBuffer.append(" 0" + r.getSendHour());
		} else {
			stringBuffer.append(" " + r.getSendHour());
		}
		if (r.getSendMinute() < 10) {
			stringBuffer.append(":0" + r.getSendMinute() + ":00");
		} else {
			stringBuffer.append(":" + r.getSendMinute() + ":00");
		}
		regulation.setTemplateId(r.getTemplateId());
		regulation.setDateTimeStr(stringBuffer.toString());
		regulation.setRuleId(r.getId());
		regulations.add(regulation);
	}

	public boolean quarter(Integer quarter, int month) {
		boolean result = false;
		if (quarter == 1)
			if (1 <= month && month <= 3)
				result = true;
		if (quarter == 2)
			if (4 <= month && month <= 6)
				result = true;
		if (quarter == 3)
			if (7 <= month && month <= 9)
				result = true;
		if (quarter == 4)
			if (10 <= month && month <= 12)
				result = true;
		return result;

	}

//	public static void main(String[] args) {
//		DateTime dateTime = new DateTime("2018-09-03");
//		System.err.println(dateTime.getDayOfWeek());
//	}
}
