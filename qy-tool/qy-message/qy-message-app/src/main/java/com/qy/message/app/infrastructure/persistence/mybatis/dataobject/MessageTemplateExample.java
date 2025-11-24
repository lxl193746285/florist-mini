package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.util.ArrayList;
import java.util.List;

public class MessageTemplateExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTemplateExample() {
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

        public Criteria andModelIdIsNull() {
            addCriterion("model_id is null");
            return (Criteria) this;
        }

        public Criteria andModelIdIsNotNull() {
            addCriterion("model_id is not null");
            return (Criteria) this;
        }

        public Criteria andModelIdEqualTo(String value) {
            addCriterion("model_id =", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotEqualTo(String value) {
            addCriterion("model_id <>", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThan(String value) {
            addCriterion("model_id >", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdGreaterThanOrEqualTo(String value) {
            addCriterion("model_id >=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThan(String value) {
            addCriterion("model_id <", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLessThanOrEqualTo(String value) {
            addCriterion("model_id <=", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdLike(String value) {
            addCriterion("model_id like", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotLike(String value) {
            addCriterion("model_id not like", value, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdIn(List<String> values) {
            addCriterion("model_id in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotIn(List<String> values) {
            addCriterion("model_id not in", values, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdBetween(String value1, String value2) {
            addCriterion("model_id between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andModelIdNotBetween(String value1, String value2) {
            addCriterion("model_id not between", value1, value2, "modelId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIsNull() {
            addCriterion("function_id is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIsNotNull() {
            addCriterion("function_id is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdEqualTo(String value) {
            addCriterion("function_id =", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotEqualTo(String value) {
            addCriterion("function_id <>", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThan(String value) {
            addCriterion("function_id >", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThanOrEqualTo(String value) {
            addCriterion("function_id >=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThan(String value) {
            addCriterion("function_id <", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThanOrEqualTo(String value) {
            addCriterion("function_id <=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLike(String value) {
            addCriterion("function_id like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotLike(String value) {
            addCriterion("function_id not like", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIn(List<String> values) {
            addCriterion("function_id in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotIn(List<String> values) {
            addCriterion("function_id not in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdBetween(String value1, String value2) {
            addCriterion("function_id between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotBetween(String value1, String value2) {
            addCriterion("function_id not between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNull() {
            addCriterion("is_enable is null");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNotNull() {
            addCriterion("is_enable is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnableEqualTo(Byte value) {
            addCriterion("is_enable =", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotEqualTo(Byte value) {
            addCriterion("is_enable <>", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThan(Byte value) {
            addCriterion("is_enable >", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_enable >=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThan(Byte value) {
            addCriterion("is_enable <", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThanOrEqualTo(Byte value) {
            addCriterion("is_enable <=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableIn(List<Byte> values) {
            addCriterion("is_enable in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotIn(List<Byte> values) {
            addCriterion("is_enable not in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableBetween(Byte value1, Byte value2) {
            addCriterion("is_enable between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotBetween(Byte value1, Byte value2) {
            addCriterion("is_enable not between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeIsNull() {
            addCriterion("is_send_system_notice is null");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeIsNotNull() {
            addCriterion("is_send_system_notice is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeEqualTo(Byte value) {
            addCriterion("is_send_system_notice =", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeNotEqualTo(Byte value) {
            addCriterion("is_send_system_notice <>", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeGreaterThan(Byte value) {
            addCriterion("is_send_system_notice >", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_send_system_notice >=", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeLessThan(Byte value) {
            addCriterion("is_send_system_notice <", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeLessThanOrEqualTo(Byte value) {
            addCriterion("is_send_system_notice <=", value, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeIn(List<Byte> values) {
            addCriterion("is_send_system_notice in", values, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeNotIn(List<Byte> values) {
            addCriterion("is_send_system_notice not in", values, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeBetween(Byte value1, Byte value2) {
            addCriterion("is_send_system_notice between", value1, value2, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendSystemNoticeNotBetween(Byte value1, Byte value2) {
            addCriterion("is_send_system_notice not between", value1, value2, "isSendSystemNotice");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIsNull() {
            addCriterion("is_send_weixin is null");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIsNotNull() {
            addCriterion("is_send_weixin is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinEqualTo(Byte value) {
            addCriterion("is_send_weixin =", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotEqualTo(Byte value) {
            addCriterion("is_send_weixin <>", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinGreaterThan(Byte value) {
            addCriterion("is_send_weixin >", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_send_weixin >=", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinLessThan(Byte value) {
            addCriterion("is_send_weixin <", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinLessThanOrEqualTo(Byte value) {
            addCriterion("is_send_weixin <=", value, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinIn(List<Byte> values) {
            addCriterion("is_send_weixin in", values, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotIn(List<Byte> values) {
            addCriterion("is_send_weixin not in", values, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinBetween(Byte value1, Byte value2) {
            addCriterion("is_send_weixin between", value1, value2, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendWeixinNotBetween(Byte value1, Byte value2) {
            addCriterion("is_send_weixin not between", value1, value2, "isSendWeixin");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageIsNull() {
            addCriterion("is_send_phone_message is null");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageIsNotNull() {
            addCriterion("is_send_phone_message is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageEqualTo(Byte value) {
            addCriterion("is_send_phone_message =", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageNotEqualTo(Byte value) {
            addCriterion("is_send_phone_message <>", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageGreaterThan(Byte value) {
            addCriterion("is_send_phone_message >", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_send_phone_message >=", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageLessThan(Byte value) {
            addCriterion("is_send_phone_message <", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageLessThanOrEqualTo(Byte value) {
            addCriterion("is_send_phone_message <=", value, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageIn(List<Byte> values) {
            addCriterion("is_send_phone_message in", values, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageNotIn(List<Byte> values) {
            addCriterion("is_send_phone_message not in", values, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageBetween(Byte value1, Byte value2) {
            addCriterion("is_send_phone_message between", value1, value2, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendPhoneMessageNotBetween(Byte value1, Byte value2) {
            addCriterion("is_send_phone_message not between", value1, value2, "isSendPhoneMessage");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIsNull() {
            addCriterion("is_send_email is null");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIsNotNull() {
            addCriterion("is_send_email is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailEqualTo(Byte value) {
            addCriterion("is_send_email =", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotEqualTo(Byte value) {
            addCriterion("is_send_email <>", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailGreaterThan(Byte value) {
            addCriterion("is_send_email >", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_send_email >=", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailLessThan(Byte value) {
            addCriterion("is_send_email <", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailLessThanOrEqualTo(Byte value) {
            addCriterion("is_send_email <=", value, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailIn(List<Byte> values) {
            addCriterion("is_send_email in", values, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotIn(List<Byte> values) {
            addCriterion("is_send_email not in", values, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailBetween(Byte value1, Byte value2) {
            addCriterion("is_send_email between", value1, value2, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendEmailNotBetween(Byte value1, Byte value2) {
            addCriterion("is_send_email not between", value1, value2, "isSendEmail");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushIsNull() {
            addCriterion("is_send_apppush is null");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushIsNotNull() {
            addCriterion("is_send_apppush is not null");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushEqualTo(Byte value) {
            addCriterion("is_send_apppush =", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushNotEqualTo(Byte value) {
            addCriterion("is_send_apppush <>", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushGreaterThan(Byte value) {
            addCriterion("is_send_apppush >", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_send_apppush >=", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushLessThan(Byte value) {
            addCriterion("is_send_apppush <", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushLessThanOrEqualTo(Byte value) {
            addCriterion("is_send_apppush <=", value, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushIn(List<Byte> values) {
            addCriterion("is_send_apppush in", values, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushNotIn(List<Byte> values) {
            addCriterion("is_send_apppush not in", values, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushBetween(Byte value1, Byte value2) {
            addCriterion("is_send_apppush between", value1, value2, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsSendApppushNotBetween(Byte value1, Byte value2) {
            addCriterion("is_send_apppush not between", value1, value2, "isSendApppush");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogIsNull() {
            addCriterion("is_wirtelog is null");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogIsNotNull() {
            addCriterion("is_wirtelog is not null");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogEqualTo(Byte value) {
            addCriterion("is_wirtelog =", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogNotEqualTo(Byte value) {
            addCriterion("is_wirtelog <>", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogGreaterThan(Byte value) {
            addCriterion("is_wirtelog >", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_wirtelog >=", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogLessThan(Byte value) {
            addCriterion("is_wirtelog <", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogLessThanOrEqualTo(Byte value) {
            addCriterion("is_wirtelog <=", value, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogIn(List<Byte> values) {
            addCriterion("is_wirtelog in", values, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogNotIn(List<Byte> values) {
            addCriterion("is_wirtelog not in", values, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogBetween(Byte value1, Byte value2) {
            addCriterion("is_wirtelog between", value1, value2, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andIsWirtelogNotBetween(Byte value1, Byte value2) {
            addCriterion("is_wirtelog not between", value1, value2, "isWirtelog");
            return (Criteria) this;
        }

        public Criteria andContentModeIsNull() {
            addCriterion("content_mode is null");
            return (Criteria) this;
        }

        public Criteria andContentModeIsNotNull() {
            addCriterion("content_mode is not null");
            return (Criteria) this;
        }

        public Criteria andContentModeEqualTo(Byte value) {
            addCriterion("content_mode =", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeNotEqualTo(Byte value) {
            addCriterion("content_mode <>", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeGreaterThan(Byte value) {
            addCriterion("content_mode >", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("content_mode >=", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeLessThan(Byte value) {
            addCriterion("content_mode <", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeLessThanOrEqualTo(Byte value) {
            addCriterion("content_mode <=", value, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeIn(List<Byte> values) {
            addCriterion("content_mode in", values, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeNotIn(List<Byte> values) {
            addCriterion("content_mode not in", values, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeBetween(Byte value1, Byte value2) {
            addCriterion("content_mode between", value1, value2, "contentMode");
            return (Criteria) this;
        }

        public Criteria andContentModeNotBetween(Byte value1, Byte value2) {
            addCriterion("content_mode not between", value1, value2, "contentMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeIsNull() {
            addCriterion("title_mode is null");
            return (Criteria) this;
        }

        public Criteria andTitleModeIsNotNull() {
            addCriterion("title_mode is not null");
            return (Criteria) this;
        }

        public Criteria andTitleModeEqualTo(Byte value) {
            addCriterion("title_mode =", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeNotEqualTo(Byte value) {
            addCriterion("title_mode <>", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeGreaterThan(Byte value) {
            addCriterion("title_mode >", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("title_mode >=", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeLessThan(Byte value) {
            addCriterion("title_mode <", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeLessThanOrEqualTo(Byte value) {
            addCriterion("title_mode <=", value, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeIn(List<Byte> values) {
            addCriterion("title_mode in", values, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeNotIn(List<Byte> values) {
            addCriterion("title_mode not in", values, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeBetween(Byte value1, Byte value2) {
            addCriterion("title_mode between", value1, value2, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleModeNotBetween(Byte value1, Byte value2) {
            addCriterion("title_mode not between", value1, value2, "titleMode");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdIsNull() {
            addCriterion("weixin_model_id is null");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdIsNotNull() {
            addCriterion("weixin_model_id is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdEqualTo(String value) {
            addCriterion("weixin_model_id =", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdNotEqualTo(String value) {
            addCriterion("weixin_model_id <>", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdGreaterThan(String value) {
            addCriterion("weixin_model_id >", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_model_id >=", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdLessThan(String value) {
            addCriterion("weixin_model_id <", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdLessThanOrEqualTo(String value) {
            addCriterion("weixin_model_id <=", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdLike(String value) {
            addCriterion("weixin_model_id like", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdNotLike(String value) {
            addCriterion("weixin_model_id not like", value, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdIn(List<String> values) {
            addCriterion("weixin_model_id in", values, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdNotIn(List<String> values) {
            addCriterion("weixin_model_id not in", values, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdBetween(String value1, String value2) {
            addCriterion("weixin_model_id between", value1, value2, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andWeixinModelIdNotBetween(String value1, String value2) {
            addCriterion("weixin_model_id not between", value1, value2, "weixinModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdIsNull() {
            addCriterion("phone_message_model_id is null");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdIsNotNull() {
            addCriterion("phone_message_model_id is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdEqualTo(String value) {
            addCriterion("phone_message_model_id =", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdNotEqualTo(String value) {
            addCriterion("phone_message_model_id <>", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdGreaterThan(String value) {
            addCriterion("phone_message_model_id >", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdGreaterThanOrEqualTo(String value) {
            addCriterion("phone_message_model_id >=", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdLessThan(String value) {
            addCriterion("phone_message_model_id <", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdLessThanOrEqualTo(String value) {
            addCriterion("phone_message_model_id <=", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdLike(String value) {
            addCriterion("phone_message_model_id like", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdNotLike(String value) {
            addCriterion("phone_message_model_id not like", value, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdIn(List<String> values) {
            addCriterion("phone_message_model_id in", values, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdNotIn(List<String> values) {
            addCriterion("phone_message_model_id not in", values, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdBetween(String value1, String value2) {
            addCriterion("phone_message_model_id between", value1, value2, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andPhoneMessageModelIdNotBetween(String value1, String value2) {
            addCriterion("phone_message_model_id not between", value1, value2, "phoneMessageModelId");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("table_name like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("table_name not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("table_name in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNull() {
            addCriterion("message_type is null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNotNull() {
            addCriterion("message_type is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeEqualTo(Byte value) {
            addCriterion("message_type =", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotEqualTo(Byte value) {
            addCriterion("message_type <>", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThan(Byte value) {
            addCriterion("message_type >", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("message_type >=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThan(Byte value) {
            addCriterion("message_type <", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThanOrEqualTo(Byte value) {
            addCriterion("message_type <=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIn(List<Byte> values) {
            addCriterion("message_type in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotIn(List<Byte> values) {
            addCriterion("message_type not in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeBetween(Byte value1, Byte value2) {
            addCriterion("message_type between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("message_type not between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIsNull() {
            addCriterion("response_url is null");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIsNotNull() {
            addCriterion("response_url is not null");
            return (Criteria) this;
        }

        public Criteria andResponseUrlEqualTo(String value) {
            addCriterion("response_url =", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotEqualTo(String value) {
            addCriterion("response_url <>", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlGreaterThan(String value) {
            addCriterion("response_url >", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlGreaterThanOrEqualTo(String value) {
            addCriterion("response_url >=", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLessThan(String value) {
            addCriterion("response_url <", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLessThanOrEqualTo(String value) {
            addCriterion("response_url <=", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlLike(String value) {
            addCriterion("response_url like", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotLike(String value) {
            addCriterion("response_url not like", value, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlIn(List<String> values) {
            addCriterion("response_url in", values, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotIn(List<String> values) {
            addCriterion("response_url not in", values, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlBetween(String value1, String value2) {
            addCriterion("response_url between", value1, value2, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andResponseUrlNotBetween(String value1, String value2) {
            addCriterion("response_url not between", value1, value2, "responseUrl");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeIsNull() {
            addCriterion("user_receive_mode is null");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeIsNotNull() {
            addCriterion("user_receive_mode is not null");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeEqualTo(Byte value) {
            addCriterion("user_receive_mode =", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeNotEqualTo(Byte value) {
            addCriterion("user_receive_mode <>", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeGreaterThan(Byte value) {
            addCriterion("user_receive_mode >", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("user_receive_mode >=", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeLessThan(Byte value) {
            addCriterion("user_receive_mode <", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeLessThanOrEqualTo(Byte value) {
            addCriterion("user_receive_mode <=", value, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeIn(List<Byte> values) {
            addCriterion("user_receive_mode in", values, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeNotIn(List<Byte> values) {
            addCriterion("user_receive_mode not in", values, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeBetween(Byte value1, Byte value2) {
            addCriterion("user_receive_mode between", value1, value2, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveModeNotBetween(Byte value1, Byte value2) {
            addCriterion("user_receive_mode not between", value1, value2, "userReceiveMode");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlIsNull() {
            addCriterion("user_receive_sql is null");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlIsNotNull() {
            addCriterion("user_receive_sql is not null");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlEqualTo(String value) {
            addCriterion("user_receive_sql =", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlNotEqualTo(String value) {
            addCriterion("user_receive_sql <>", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlGreaterThan(String value) {
            addCriterion("user_receive_sql >", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlGreaterThanOrEqualTo(String value) {
            addCriterion("user_receive_sql >=", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlLessThan(String value) {
            addCriterion("user_receive_sql <", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlLessThanOrEqualTo(String value) {
            addCriterion("user_receive_sql <=", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlLike(String value) {
            addCriterion("user_receive_sql like", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlNotLike(String value) {
            addCriterion("user_receive_sql not like", value, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlIn(List<String> values) {
            addCriterion("user_receive_sql in", values, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlNotIn(List<String> values) {
            addCriterion("user_receive_sql not in", values, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlBetween(String value1, String value2) {
            addCriterion("user_receive_sql between", value1, value2, "userReceiveSql");
            return (Criteria) this;
        }

        public Criteria andUserReceiveSqlNotBetween(String value1, String value2) {
            addCriterion("user_receive_sql not between", value1, value2, "userReceiveSql");
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

        public Criteria andModelDescriptionIsNull() {
            addCriterion("model_description is null");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionIsNotNull() {
            addCriterion("model_description is not null");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionEqualTo(String value) {
            addCriterion("model_description =", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionNotEqualTo(String value) {
            addCriterion("model_description <>", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionGreaterThan(String value) {
            addCriterion("model_description >", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("model_description >=", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionLessThan(String value) {
            addCriterion("model_description <", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionLessThanOrEqualTo(String value) {
            addCriterion("model_description <=", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionLike(String value) {
            addCriterion("model_description like", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionNotLike(String value) {
            addCriterion("model_description not like", value, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionIn(List<String> values) {
            addCriterion("model_description in", values, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionNotIn(List<String> values) {
            addCriterion("model_description not in", values, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionBetween(String value1, String value2) {
            addCriterion("model_description between", value1, value2, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andModelDescriptionNotBetween(String value1, String value2) {
            addCriterion("model_description not between", value1, value2, "modelDescription");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNull() {
            addCriterion("client_type is null");
            return (Criteria) this;
        }

        public Criteria andClientTypeIsNotNull() {
            addCriterion("client_type is not null");
            return (Criteria) this;
        }

        public Criteria andClientTypeEqualTo(Integer value) {
            addCriterion("client_type =", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotEqualTo(Integer value) {
            addCriterion("client_type <>", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThan(Integer value) {
            addCriterion("client_type >", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("client_type >=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThan(Integer value) {
            addCriterion("client_type <", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeLessThanOrEqualTo(Integer value) {
            addCriterion("client_type <=", value, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeIn(List<Integer> values) {
            addCriterion("client_type in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotIn(List<Integer> values) {
            addCriterion("client_type not in", values, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeBetween(Integer value1, Integer value2) {
            addCriterion("client_type between", value1, value2, "clientType");
            return (Criteria) this;
        }

        public Criteria andClientTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("client_type not between", value1, value2, "clientType");
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