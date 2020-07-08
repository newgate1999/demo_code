package com.selfgrowth.incomeservice.repository.custom;

import com.selfgrowth.domain.entity.Income;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface IncomeRepositoryCustom {
    List<Income> search(MultiValueMap<String, String> queryParams, Pageable pageable);

    Long countIncome(MultiValueMap<String, String> queryparams);
}
