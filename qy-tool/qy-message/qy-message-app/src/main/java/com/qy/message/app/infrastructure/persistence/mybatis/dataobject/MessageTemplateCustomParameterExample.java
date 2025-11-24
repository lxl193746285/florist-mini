package com.qy.message.app.infrastructure.persistence.mybatis.dataobject;

import java.util.ArrayList;
import java.util.List;

public class MessageTemplateCustomParameterExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MessageTemplateCustomParameterExample() {
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

        public Criteria andTemplatelIdIsNull() {
            addCriterion("templatel_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdIsNotNull() {
            addCriterion("templatel_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdEqualTo(Integer value) {
            addCriterion("templatel_id =", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdNotEqualTo(Integer value) {
            addCriterion("templatel_id <>", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdGreaterThan(Integer value) {
            addCriterion("templatel_id >", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("templatel_id >=", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdLessThan(Integer value) {
            addCriterion("templatel_id <", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdLessThanOrEqualTo(Integer value) {
            addCriterion("templatel_id <=", value, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdIn(List<Integer> values) {
            addCriterion("templatel_id in", values, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdNotIn(List<Integer> values) {
            addCriterion("templatel_id not in", values, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdBetween(Integer value1, Integer value2) {
            addCriterion("templatel_id between", value1, value2, "templatelId");
            return (Criteria) this;
        }

        public Criteria andTemplatelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("templatel_id not between", value1, value2, "templatelId");
            return (Criteria) this;
        }

        public Criteria andKeyIdIsNull() {
            addCriterion("key_id is null");
            return (Criteria) this;
        }

        public Criteria andKeyIdIsNotNull() {
            addCriterion("key_id is not null");
            return (Criteria) this;
        }

        public Criteria andKeyIdEqualTo(String value) {
            addCriterion("key_id =", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotEqualTo(String value) {
            addCriterion("key_id <>", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdGreaterThan(String value) {
            addCriterion("key_id >", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdGreaterThanOrEqualTo(String value) {
            addCriterion("key_id >=", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdLessThan(String value) {
            addCriterion("key_id <", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdLessThanOrEqualTo(String value) {
            addCriterion("key_id <=", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdLike(String value) {
            addCriterion("key_id like", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotLike(String value) {
            addCriterion("key_id not like", value, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdIn(List<String> values) {
            addCriterion("key_id in", values, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotIn(List<String> values) {
            addCriterion("key_id not in", values, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdBetween(String value1, String value2) {
            addCriterion("key_id between", value1, value2, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyIdNotBetween(String value1, String value2) {
            addCriterion("key_id not between", value1, value2, "keyId");
            return (Criteria) this;
        }

        public Criteria andKeyModeIsNull() {
            addCriterion("key_mode is null");
            return (Criteria) this;
        }

        public Criteria andKeyModeIsNotNull() {
            addCriterion("key_mode is not null");
            return (Criteria) this;
        }

        public Criteria andKeyModeEqualTo(Byte value) {
            addCriterion("key_mode =", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeNotEqualTo(Byte value) {
            addCriterion("key_mode <>", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeGreaterThan(Byte value) {
            addCriterion("key_mode >", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeGreaterThanOrEqualTo(Byte value) {
            addCriterion("key_mode >=", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeLessThan(Byte value) {
            addCriterion("key_mode <", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeLessThanOrEqualTo(Byte value) {
            addCriterion("key_mode <=", value, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeIn(List<Byte> values) {
            addCriterion("key_mode in", values, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeNotIn(List<Byte> values) {
            addCriterion("key_mode not in", values, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeBetween(Byte value1, Byte value2) {
            addCriterion("key_mode between", value1, value2, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyModeNotBetween(Byte value1, Byte value2) {
            addCriterion("key_mode not between", value1, value2, "keyMode");
            return (Criteria) this;
        }

        public Criteria andKeyValueIsNull() {
            addCriterion("key_value is null");
            return (Criteria) this;
        }

        public Criteria andKeyValueIsNotNull() {
            addCriterion("key_value is not null");
            return (Criteria) this;
        }

        public Criteria andKeyValueEqualTo(String value) {
            addCriterion("key_value =", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueNotEqualTo(String value) {
            addCriterion("key_value <>", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueGreaterThan(String value) {
            addCriterion("key_value >", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueGreaterThanOrEqualTo(String value) {
            addCriterion("key_value >=", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueLessThan(String value) {
            addCriterion("key_value <", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueLessThanOrEqualTo(String value) {
            addCriterion("key_value <=", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueLike(String value) {
            addCriterion("key_value like", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueNotLike(String value) {
            addCriterion("key_value not like", value, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueIn(List<String> values) {
            addCriterion("key_value in", values, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueNotIn(List<String> values) {
            addCriterion("key_value not in", values, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueBetween(String value1, String value2) {
            addCriterion("key_value between", value1, value2, "keyValue");
            return (Criteria) this;
        }

        public Criteria andKeyValueNotBetween(String value1, String value2) {
            addCriterion("key_value not between", value1, value2, "keyValue");
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