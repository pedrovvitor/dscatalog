package com.devsuperior.dscatalog.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;

@Component
public class ProductMapper {

	@Autowired
	CategoryMapper categoryMapper;

	public ProductDTO toDto(Product entity) {
		ProductDTO dto = new ProductDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setImgUrl(entity.getImgUrl());
		dto.setDate(entity.getDate());
		return dto;
	}

	public ProductDTO toDtoWithCategories(Product entity) {
		ProductDTO dto = new ProductDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setImgUrl(entity.getImgUrl());
		dto.setDate(entity.getDate());
		entity.getCategories().forEach(cat -> dto.getCategories().add(categoryMapper.toDto(cat)));
		return dto;
	}

	public Product toEntity(ProductDTO dto) {
		Product entity = new Product();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setDate(dto.getDate());
		return entity;
	}
}
