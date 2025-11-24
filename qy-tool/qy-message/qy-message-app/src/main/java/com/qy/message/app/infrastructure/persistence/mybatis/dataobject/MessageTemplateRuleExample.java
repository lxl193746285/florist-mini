package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.util.ArrayList;
import java.util.List;

public class MessageTemplateRuleExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTemplateRuleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andSendCycleIsNull() {
            addCriterion("send_cycle is null");
            return (Criteria) this;
        }

        public Criteria andSendCycleIsNotNull() {
            addCriterion("send_cycle is not null");
            return (Criteria) this;
        }

        public Criteria andSendCycleEqualTo(Byte value) {
            addCriterion("send_cycle =", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleNotEqualTo(Byte value) {
            addCriterion("send_cycle <>", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleGreaterThan(Byte value) {
            addCriterion("send_cycle >", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleGreaterThanOrEqualTo(Byte value) {
            addCriterion("send_cycle >=", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleLessThan(Byte value) {
            addCriterion("send_cycle <", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleLessThanOrEqualTo(Byte value) {
            addCriterion("send_cycle <=", value, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleIn(List<Byte> values) {
            addCriterion("send_cycle in", values, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleNotIn(List<Byte> values) {
            addCriterion("send_cycle not in", values, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleBetween(Byte value1, Byte value2) {
            addCriterion("send_cycle between", value1, value2, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendCycleNotBetween(Byte value1, Byte value2) {
            addCriterion("send_cycle not between", value1, value2, "sendCycle");
            return (Criteria) this;
        }

        public Criteria andSendYearIsNull() {
            addCriterion("send_year is null");
            return (Criteria) this;
        }

        public Criteria andSendYearIsNotNull() {
            addCriterion("send_year is not null");
            return (Criteria) this;
        }

        public Criteria andSendYearEqualTo(Integer value) {
            addCriterion("send_year =", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearNotEqualTo(Integer value) {
            addCriterion("send_year <>", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearGreaterThan(Integer value) {
            addCriterion("send_year >", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_year >=", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearLessThan(Integer value) {
            addCriterion("send_year <", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearLessThanOrEqualTo(Integer value) {
            addCriterion("send_year <=", value, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearIn(List<Integer> values) {
            addCriterion("send_year in", values, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearNotIn(List<Integer> values) {
            addCriterion("send_year not in", values, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearBetween(Integer value1, Integer value2) {
            addCriterion("send_year between", value1, value2, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendYearNotBetween(Integer value1, Integer value2) {
            addCriterion("send_year not between", value1, value2, "sendYear");
            return (Criteria) this;
        }

        public Criteria andSendQuarterIsNull() {
            addCriterion("send_quarter is null");
            return (Criteria) this;
        }

        public Criteria andSendQuarterIsNotNull() {
            addCriterion("send_quarter is not null");
            return (Criteria) this;
        }

        public Criteria andSendQuarterEqualTo(Integer value) {
            addCriterion("send_quarter =", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterNotEqualTo(Integer value) {
            addCriterion("send_quarter <>", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterGreaterThan(Integer value) {
            addCriterion("send_quarter >", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_quarter >=", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterLessThan(Integer value) {
            addCriterion("send_quarter <", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterLessThanOrEqualTo(Integer value) {
            addCriterion("send_quarter <=", value, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterIn(List<Integer> values) {
            addCriterion("send_quarter in", values, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterNotIn(List<Integer> values) {
            addCriterion("send_quarter not in", values, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterBetween(Integer value1, Integer value2) {
            addCriterion("send_quarter between", value1, value2, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendQuarterNotBetween(Integer value1, Integer value2) {
            addCriterion("send_quarter not between", value1, value2, "sendQuarter");
            return (Criteria) this;
        }

        public Criteria andSendMonthIsNull() {
            addCriterion("send_month is null");
            return (Criteria) this;
        }

        public Criteria andSendMonthIsNotNull() {
            addCriterion("send_month is not null");
            return (Criteria) this;
        }

        public Criteria andSendMonthEqualTo(Integer value) {
            addCriterion("send_month =", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthNotEqualTo(Integer value) {
            addCriterion("send_month <>", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthGreaterThan(Integer value) {
            addCriterion("send_month >", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_month >=", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthLessThan(Integer value) {
            addCriterion("send_month <", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthLessThanOrEqualTo(Integer value) {
            addCriterion("send_month <=", value, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthIn(List<Integer> values) {
            addCriterion("send_month in", values, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthNotIn(List<Integer> values) {
            addCriterion("send_month not in", values, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthBetween(Integer value1, Integer value2) {
            addCriterion("send_month between", value1, value2, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendMonthNotBetween(Integer value1, Integer value2) {
            addCriterion("send_month not between", value1, value2, "sendMonth");
            return (Criteria) this;
        }

        public Criteria andSendWeekIsNull() {
            addCriterion("send_week is null");
            return (Criteria) this;
        }

        public Criteria andSendWeekIsNotNull() {
            addCriterion("send_week is not null");
            return (Criteria) this;
        }

        public Criteria andSendWeekEqualTo(Integer value) {
            addCriterion("send_week =", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekNotEqualTo(Integer value) {
            addCriterion("send_week <>", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekGreaterThan(Integer value) {
            addCriterion("send_week >", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_week >=", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekLessThan(Integer value) {
            addCriterion("send_week <", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekLessThanOrEqualTo(Integer value) {
            addCriterion("send_week <=", value, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekIn(List<Integer> values) {
            addCriterion("send_week in", values, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekNotIn(List<Integer> values) {
            addCriterion("send_week not in", values, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekBetween(Integer value1, Integer value2) {
            addCriterion("send_week between", value1, value2, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendWeekNotBetween(Integer value1, Integer value2) {
            addCriterion("send_week not between", value1, value2, "sendWeek");
            return (Criteria) this;
        }

        public Criteria andSendDayIsNull() {
            addCriterion("send_day is null");
            return (Criteria) this;
        }

        public Criteria andSendDayIsNotNull() {
            addCriterion("send_day is not null");
            return (Criteria) this;
        }

        public Criteria andSendDayEqualTo(Integer value) {
            addCriterion("send_day =", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayNotEqualTo(Integer value) {
            addCriterion("send_day <>", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayGreaterThan(Integer value) {
            addCriterion("send_day >", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_day >=", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayLessThan(Integer value) {
            addCriterion("send_day <", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayLessThanOrEqualTo(Integer value) {
            addCriterion("send_day <=", value, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayIn(List<Integer> values) {
            addCriterion("send_day in", values, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayNotIn(List<Integer> values) {
            addCriterion("send_day not in", values, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayBetween(Integer value1, Integer value2) {
            addCriterion("send_day between", value1, value2, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendDayNotBetween(Integer value1, Integer value2) {
            addCriterion("send_day not between", value1, value2, "sendDay");
            return (Criteria) this;
        }

        public Criteria andSendHourIsNull() {
            addCriterion("send_hour is null");
            return (Criteria) this;
        }

        public Criteria andSendHourIsNotNull() {
            addCriterion("send_hour is not null");
            return (Criteria) this;
        }

        public Criteria andSendHourEqualTo(Integer value) {
            addCriterion("send_hour =", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourNotEqualTo(Integer value) {
            addCriterion("send_hour <>", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourGreaterThan(Integer value) {
            addCriterion("send_hour >", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_hour >=", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourLessThan(Integer value) {
            addCriterion("send_hour <", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourLessThanOrEqualTo(Integer value) {
            addCriterion("send_hour <=", value, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourIn(List<Integer> values) {
            addCriterion("send_hour in", values, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourNotIn(List<Integer> values) {
            addCriterion("send_hour not in", values, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourBetween(Integer value1, Integer value2) {
            addCriterion("send_hour between", value1, value2, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendHourNotBetween(Integer value1, Integer value2) {
            addCriterion("send_hour not between", value1, value2, "sendHour");
            return (Criteria) this;
        }

        public Criteria andSendMinuteIsNull() {
            addCriterion("send_minute is null");
            return (Criteria) this;
        }

        public Criteria andSendMinuteIsNotNull() {
            addCriterion("send_minute is not null");
            return (Criteria) this;
        }

        public Criteria andSendMinuteEqualTo(Integer value) {
            addCriterion("send_minute =", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteNotEqualTo(Integer value) {
            addCriterion("send_minute <>", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteGreaterThan(Integer value) {
            addCriterion("send_minute >", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_minute >=", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteLessThan(Integer value) {
            addCriterion("send_minute <", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("send_minute <=", value, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteIn(List<Integer> values) {
            addCriterion("send_minute in", values, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteNotIn(List<Integer> values) {
            addCriterion("send_minute not in", values, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteBetween(Integer value1, Integer value2) {
            addCriterion("send_minute between", value1, value2, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSendMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("send_minute not between", value1, value2, "sendMinute");
            return (Criteria) this;
        }

        public Criteria andSystemVarIsNull() {
            addCriterion("system_var is null");
            return (Criteria) this;
        }

        public Criteria andSystemVarIsNotNull() {
            addCriterion("system_var is not null");
            return (Criteria) this;
        }

        public Criteria andSystemVarEqualTo(String value) {
            addCriterion("system_var =", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarNotEqualTo(String value) {
            addCriterion("system_var <>", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarGreaterThan(String value) {
            addCriterion("system_var >", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarGreaterThanOrEqualTo(String value) {
            addCriterion("system_var >=", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarLessThan(String value) {
            addCriterion("system_var <", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarLessThanOrEqualTo(String value) {
            addCriterion("system_var <=", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarLike(String value) {
            addCriterion("system_var like", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarNotLike(String value) {
            addCriterion("system_var not like", value, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarIn(List<String> values) {
            addCriterion("system_var in", values, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarNotIn(List<String> values) {
            addCriterion("system_var not in", values, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarBetween(String value1, String value2) {
            addCriterion("system_var between", value1, value2, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarNotBetween(String value1, String value2) {
            addCriterion("system_var not between", value1, value2, "systemVar");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueIsNull() {
            addCriterion("system_var_value is null");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueIsNotNull() {
            addCriterion("system_var_value is not null");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueEqualTo(Integer value) {
            addCriterion("system_var_value =", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueNotEqualTo(Integer value) {
            addCriterion("system_var_value <>", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueGreaterThan(Integer value) {
            addCriterion("system_var_value >", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("system_var_value >=", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueLessThan(Integer value) {
            addCriterion("system_var_value <", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueLessThanOrEqualTo(Integer value) {
            addCriterion("system_var_value <=", value, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueIn(List<Integer> values) {
            addCriterion("system_var_value in", values, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueNotIn(List<Integer> values) {
            addCriterion("system_var_value not in", values, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueBetween(Integer value1, Integer value2) {
            addCriterion("system_var_value between", value1, value2, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andSystemVarValueNotBetween(Integer value1, Integer value2) {
            addCriterion("system_var_value not between", value1, value2, "systemVarValue");
            return (Criteria) this;
        }

        public Criteria andCustomSqlIsNull() {
            addCriterion("custom_sql is null");
            return (Criteria) this;
        }

        public Criteria andCustomSqlIsNotNull() {
            addCriterion("custom_sql is not null");
            return (Criteria) this;
        }

        public Criteria andCustomSqlEqualTo(String value) {
            addCriterion("custom_sql =", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlNotEqualTo(String value) {
            addCriterion("custom_sql <>", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlGreaterThan(String value) {
            addCriterion("custom_sql >", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlGreaterThanOrEqualTo(String value) {
            addCriterion("custom_sql >=", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlLessThan(String value) {
            addCriterion("custom_sql <", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlLessThanOrEqualTo(String value) {
            addCriterion("custom_sql <=", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlLike(String value) {
            addCriterion("custom_sql like", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlNotLike(String value) {
            addCriterion("custom_sql not like", value, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlIn(List<String> values) {
            addCriterion("custom_sql in", values, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlNotIn(List<String> values) {
            addCriterion("custom_sql not in", values, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlBetween(String value1, String value2) {
            addCriterion("custom_sql between", value1, value2, "customSql");
            return (Criteria) this;
        }

        public Criteria andCustomSqlNotBetween(String value1, String value2) {
            addCriterion("custom_sql not between", value1, value2, "customSql");
            return (Criteria) this;
        }

        public Criteria andTimeModeIsNull() {
            addCriterion("time_mode is null");
            return (Criteria) this;
        }

        public Criteria andTimeModeIsNotNull() {
            addCriterion("time_mode is not null");
            return (Criteria) this;
        }

        public Criteria andTimeModeEqualTo(Byte value) {
            addCriterion("time_mode =", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeNotEqualTo(Byte value) {
            addCriterion("time_mode <>", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeGreaterThan(Byte value) {
            addCriterion("time_mode >", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("time_mode >=", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeLessThan(Byte value) {
            addCriterion("time_mode <", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeLessThanOrEqualTo(Byte value) {
            addCriterion("time_mode <=", value, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeIn(List<Byte> values) {
            addCriterion("time_mode in", values, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeNotIn(List<Byte> values) {
            addCriterion("time_mode not in", values, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeBetween(Byte value1, Byte value2) {
            addCriterion("time_mode between", value1, value2, "timeMode");
            return (Criteria) this;
        }

        public Criteria andTimeModeNotBetween(Byte value1, Byte value2) {
            addCriterion("time_mode not between", value1, value2, "timeMode");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    public static class Criterion {

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}