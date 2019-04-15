package tk.yimiao.yimiaocloud.microservice.mall.base.pojo;

import java.util.ArrayList;
import java.util.List;

public class TbOauthExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Long offset;

    public TbOauthExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andOauthNameIsNull() {
            addCriterion("oauth_name is null");
            return (Criteria) this;
        }

        public Criteria andOauthNameIsNotNull() {
            addCriterion("oauth_name is not null");
            return (Criteria) this;
        }

        public Criteria andOauthNameEqualTo(String value) {
            addCriterion("oauth_name =", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameNotEqualTo(String value) {
            addCriterion("oauth_name <>", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameGreaterThan(String value) {
            addCriterion("oauth_name >", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameGreaterThanOrEqualTo(String value) {
            addCriterion("oauth_name >=", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameLessThan(String value) {
            addCriterion("oauth_name <", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameLessThanOrEqualTo(String value) {
            addCriterion("oauth_name <=", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameLike(String value) {
            addCriterion("oauth_name like", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameNotLike(String value) {
            addCriterion("oauth_name not like", value, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameIn(List<String> values) {
            addCriterion("oauth_name in", values, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameNotIn(List<String> values) {
            addCriterion("oauth_name not in", values, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameBetween(String value1, String value2) {
            addCriterion("oauth_name between", value1, value2, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthNameNotBetween(String value1, String value2) {
            addCriterion("oauth_name not between", value1, value2, "oauthName");
            return (Criteria) this;
        }

        public Criteria andOauthIdIsNull() {
            addCriterion("oauth_id is null");
            return (Criteria) this;
        }

        public Criteria andOauthIdIsNotNull() {
            addCriterion("oauth_id is not null");
            return (Criteria) this;
        }

        public Criteria andOauthIdEqualTo(String value) {
            addCriterion("oauth_id =", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdNotEqualTo(String value) {
            addCriterion("oauth_id <>", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdGreaterThan(String value) {
            addCriterion("oauth_id >", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdGreaterThanOrEqualTo(String value) {
            addCriterion("oauth_id >=", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdLessThan(String value) {
            addCriterion("oauth_id <", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdLessThanOrEqualTo(String value) {
            addCriterion("oauth_id <=", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdLike(String value) {
            addCriterion("oauth_id like", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdNotLike(String value) {
            addCriterion("oauth_id not like", value, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdIn(List<String> values) {
            addCriterion("oauth_id in", values, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdNotIn(List<String> values) {
            addCriterion("oauth_id not in", values, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdBetween(String value1, String value2) {
            addCriterion("oauth_id between", value1, value2, "oauthId");
            return (Criteria) this;
        }

        public Criteria andOauthIdNotBetween(String value1, String value2) {
            addCriterion("oauth_id not between", value1, value2, "oauthId");
            return (Criteria) this;
        }
    }

    /**
     *
     */
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
    }
}