package com.devsuperior.dscatalog.mapper;

import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;

@Component
public class CategoryMapper {

	public CategoryDTO toDto(Category entity) {
		return  CategoryDTO.builder().id(entity.getId()).name(entity.getName()).build();
	}
	
	public Category toEntity(CategoryDTO dto) {
		return Category.builder().id(dto.getId()).name(dto.getName()).build();
	}

}
