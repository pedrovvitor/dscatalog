package com.devsuperior.dscatalog.businessrule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Component
public class ProductBusinessRule {

	@Autowired
	private CategoryRepository categoryRepository;

	public Product copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());

		entity.getCategories().clear();
		dto.getCategories().forEach(cat -> entity.getCategories().add(categoryRepository.getOne(cat.getId())));
		return entity;
	}
}
