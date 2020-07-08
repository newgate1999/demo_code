package com.selfgrowth.incomeservice.mapper;

import com.selfgrowth.domain.dto.CategoryDto;
import com.selfgrowth.domain.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {

    Category toEntity(CategoryDto categoryDTO);
    CategoryDto toDto(Category category);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
