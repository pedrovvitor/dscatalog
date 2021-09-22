package com.devsuperior.dscatalog.mapper;

import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;

@Component
public class CategoryMapper {

	public CategoryDTO toDto(Category entity) {
		return new CategoryDTO(entity.getId(), entity.getName());
	}
	
	public Category toEntity(CategoryDTO dto) {
		return new Category(dto.getId(), dto.getName());
	}

}
