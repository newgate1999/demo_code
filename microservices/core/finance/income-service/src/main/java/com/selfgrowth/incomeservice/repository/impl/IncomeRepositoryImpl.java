package com.selfgrowth.incomeservice.repository.impl;

import com.selfgrowth.domain.entity.Income;
import com.selfgrowth.incomeservice.repository.custom.IncomeRepositoryCustom;
import com.selfgrowth.incomeservice.util.Constant;
import com.selfgrowth.incomeservice.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeRepositoryImpl implements IncomeRepositoryCustom {

    private final Logger log = LoggerFactory.getLogger(IncomeRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Income> search(MultiValueMap<String, String> queryParams, Pageable pageable) {
        String sql = "select A from Income A";
        Map<String, Object> values = new HashMap<>();

        sql += createWhereQuery(queryParams, values);
        sql += createOrderQuery(queryParams);
        Query query = entityManager.createQuery(sql, Income.class);
        values.forEach(query::setParameter);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public Long countIncome(MultiValueMap<String, String> queryparams) {
        String sql = "select count(A) from Income A ";
        Map<String, Object> values = new HashMap<>();
        sql += createWhereQuery(queryparams, values);
        Query query = entityManager.createQuery(sql, Long.class);
        values.forEach(query::setParameter);
        return (Long) query.getSingleResult();
    }

    private String createWhereQuery(MultiValueMap<String, String> queryParams, Map<String, Object> values) {
        String sql = " where A.status <> 0 ";
        if (queryParams.containsKey("name") && !StrUtil.isBlank(queryParams.get("name").get(0))) {
            sql += "and lower(A.name) like lower(:name) ";
            values.put("name", "%" + queryParams.get("name").get(0) + "%");
        }
        if (queryParams.containsKey("value")) {
            sql += "and A.value = :value ";
            values.put("value", Double.valueOf(queryParams.get("value").get(0)));
        }
        if (queryParams.containsKey("categoryId")) {
            sql += "and A.categoryId = :categoryId ";
            values.put("categoryId", Long.valueOf(queryParams.get("categoryId").get(0)));
        }
        return sql;
    }

    private String createOrderQuery(MultiValueMap<String, String> queryParams) {
        String sql = " order by ";

        if (queryParams.containsKey("sort")) {
            String sort = queryParams.get("sort").get(0);
            if (Constant.SORT_BY.HIGH_TO_LOW.equals(sort)) {
                sql += " A.value desc NULLS LAST ";
            } else if (Constant.SORT_BY.LOW_TO_HIGH.equals(sort)) {
                sql += " A.value asc NULLS LAST ";
            } else if (Constant.SORT_BY.NEW_TO_OLD.equals(sort)) {
                sql += " A.createdDate desc ";
            } else if (Constant.SORT_BY.OLD_TO_NEW.equals(sort)) {
                sql += " A.createdDate asc ";
            } else {
                sql += " A.createdDate desc ";
            }
        }
        return sql;
    }
}
