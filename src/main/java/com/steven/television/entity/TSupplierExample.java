package com.steven.television.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TSupplierExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public TSupplierExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
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

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("supplier_id like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("supplier_id not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrIsNull() {
            addCriterion("supplier_addr is null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrIsNotNull() {
            addCriterion("supplier_addr is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrEqualTo(String value) {
            addCriterion("supplier_addr =", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrNotEqualTo(String value) {
            addCriterion("supplier_addr <>", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrGreaterThan(String value) {
            addCriterion("supplier_addr >", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_addr >=", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrLessThan(String value) {
            addCriterion("supplier_addr <", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrLessThanOrEqualTo(String value) {
            addCriterion("supplier_addr <=", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrLike(String value) {
            addCriterion("supplier_addr like", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrNotLike(String value) {
            addCriterion("supplier_addr not like", value, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrIn(List<String> values) {
            addCriterion("supplier_addr in", values, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrNotIn(List<String> values) {
            addCriterion("supplier_addr not in", values, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrBetween(String value1, String value2) {
            addCriterion("supplier_addr between", value1, value2, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierAddrNotBetween(String value1, String value2) {
            addCriterion("supplier_addr not between", value1, value2, "supplierAddr");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneIsNull() {
            addCriterion("supplier_phone is null");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneIsNotNull() {
            addCriterion("supplier_phone is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneEqualTo(String value) {
            addCriterion("supplier_phone =", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneNotEqualTo(String value) {
            addCriterion("supplier_phone <>", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneGreaterThan(String value) {
            addCriterion("supplier_phone >", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_phone >=", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneLessThan(String value) {
            addCriterion("supplier_phone <", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneLessThanOrEqualTo(String value) {
            addCriterion("supplier_phone <=", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneLike(String value) {
            addCriterion("supplier_phone like", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneNotLike(String value) {
            addCriterion("supplier_phone not like", value, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneIn(List<String> values) {
            addCriterion("supplier_phone in", values, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneNotIn(List<String> values) {
            addCriterion("supplier_phone not in", values, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneBetween(String value1, String value2) {
            addCriterion("supplier_phone between", value1, value2, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierPhoneNotBetween(String value1, String value2) {
            addCriterion("supplier_phone not between", value1, value2, "supplierPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierDescIsNull() {
            addCriterion("supplier_desc is null");
            return (Criteria) this;
        }

        public Criteria andSupplierDescIsNotNull() {
            addCriterion("supplier_desc is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierDescEqualTo(String value) {
            addCriterion("supplier_desc =", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescNotEqualTo(String value) {
            addCriterion("supplier_desc <>", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescGreaterThan(String value) {
            addCriterion("supplier_desc >", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_desc >=", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescLessThan(String value) {
            addCriterion("supplier_desc <", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescLessThanOrEqualTo(String value) {
            addCriterion("supplier_desc <=", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescLike(String value) {
            addCriterion("supplier_desc like", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescNotLike(String value) {
            addCriterion("supplier_desc not like", value, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescIn(List<String> values) {
            addCriterion("supplier_desc in", values, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescNotIn(List<String> values) {
            addCriterion("supplier_desc not in", values, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescBetween(String value1, String value2) {
            addCriterion("supplier_desc between", value1, value2, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andSupplierDescNotBetween(String value1, String value2) {
            addCriterion("supplier_desc not between", value1, value2, "supplierDesc");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(Integer value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(Integer value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(Integer value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(Integer value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(Integer value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<Integer> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<Integer> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(Integer value1, Integer value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_supplier
     *
     * @mbg.generated do_not_delete_during_merge Mon Nov 09 22:22:16 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_supplier
     *
     * @mbg.generated Mon Nov 09 22:22:16 CST 2020
     */
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