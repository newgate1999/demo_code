package com.selfgrowth.incomeservice.service;


import com.selfgrowth.domain.dto.IncomeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface IncomeService {

    IncomeDto save(IncomeDto incomeDto);

    List<IncomeDto> saveAll(List<IncomeDto> incomeDtoList);

    Optional<IncomeDto> findById(Long id);

    Page<IncomeDto> search(MultiValueMap<String, String> queryParams, Pageable pageable);

    IncomeDto delete(Long id);
}
