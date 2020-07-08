package com.selfgrowth.incomeservice.repository;

import com.selfgrowth.domain.entity.Income;
import com.selfgrowth.incomeservice.repository.custom.IncomeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long>, IncomeRepositoryCustom {

    Optional<Income> findById(Long Id);
}
