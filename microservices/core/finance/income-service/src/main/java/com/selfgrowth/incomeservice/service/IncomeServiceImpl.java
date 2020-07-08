package com.selfgrowth.incomeservice.service;

import com.selfgrowth.domain.dto.IncomeDto;
import com.selfgrowth.domain.entity.Income;
import com.selfgrowth.incomeservice.mapper.IncomeMapper;
import com.selfgrowth.incomeservice.repository.IncomeRepository;
import com.selfgrowth.incomeservice.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final Logger log = LoggerFactory.getLogger(IncomeServiceImpl.class);

    private final IncomeRepository incomeRepository;
    private final IncomeMapper incomeMapper;

    public IncomeServiceImpl(IncomeRepository incomeRepository, IncomeMapper incomeMapper) {
        this.incomeRepository = incomeRepository;
        this.incomeMapper = incomeMapper;
    }

    @Override
    public IncomeDto save(IncomeDto incomeDto) {
        log.debug("Request to save Income: {}", incomeDto);
        incomeDto.setStatus(Constant.ENTITY_STATUS.ACTIVE);

        Income income = incomeMapper.toEntity(incomeDto);
        income = incomeRepository.save(income);

        IncomeDto result = incomeMapper.toDto(income);
        return result;
    }

    @Override
    public List<IncomeDto> saveAll(List<IncomeDto> incomeDtoList) {
        log.debug("Request to save Income: {}", incomeDtoList);
//        incomeDto.setStatus(Constant.ENTITY_STATUS.ACTIVE);
        for (IncomeDto income : incomeDtoList) {
            income.setStatus(Constant.ENTITY_STATUS.ACTIVE);
        }

        List<Income> incomeList = incomeMapper.toEntity(incomeDtoList);
        incomeList = incomeRepository.saveAll(incomeList);

        List<IncomeDto> result = incomeMapper.toDto(incomeList);
        return result;
    }

    @Override
    public Optional<IncomeDto> findById(Long id) {
        log.debug("Request to find income by id:", id);
        return incomeRepository.findById(id).map(incomeMapper::toDto);
    }

    @Override
    public Page<IncomeDto> search(MultiValueMap<String, String> queryParams, Pageable pageable) {
        log.debug("Request to search Income");
        List<Income> incomes = incomeRepository.search(queryParams, pageable);
        Page<Income> pages = new PageImpl<>(incomes, pageable, incomeRepository.countIncome(queryParams));
        return pages.map(incomeMapper::toDto);
    }

    @Override
    public IncomeDto delete(Long id) {
        log.debug("Request to delete income: ", id);
        Optional<Income> incomeOptional = incomeRepository.findById(id);
        if (incomeOptional.isPresent()) {
            Income income = incomeOptional.get();
            if (!Constant.ENTITY_STATUS.DELETE.equals(income.getStatus())) {
                income.setStatus(Constant.ENTITY_STATUS.DELETE);
                incomeRepository.save(income);
                return incomeMapper.toDto(income);
            }
        }
        return null;
    }
}
