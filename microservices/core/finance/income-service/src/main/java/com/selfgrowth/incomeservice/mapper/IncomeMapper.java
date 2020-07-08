package com.selfgrowth.incomeservice.mapper;

import com.selfgrowth.domain.dto.IncomeDto;
import com.selfgrowth.domain.entity.Income;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface IncomeMapper extends EntityMapper<IncomeDto, Income>{
//    @Mapping(source = "category.id", target = "categoryId")
    Income toEntity(IncomeDto incomeDto);

//    @Mapping(source = "categoryId", target = "category.id")
    IncomeDto toDto(Income income);

    default Income fromId(Long id) {
        if (id == null) {
            return null;
        }
        Income income = new Income();
        income.setId(id);
        return income;
    }
}
