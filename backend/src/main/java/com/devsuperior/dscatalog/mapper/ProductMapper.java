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
		ProductDTO dto = ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.price(entity.getPrice())
				.imgUrl(entity.getImgUrl())
				.date(entity.getDate()).build();
		return dto;
	}

	public ProductDTO toDtoWithCategories(Product entity) {
		ProductDTO dto = ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.price(entity.getPrice())
				.imgUrl(entity.getImgUrl())
				.date(entity.getDate())
				.build();
		dto.getCategories().clear();
		entity.getCategories()
		.forEach(cat -> dto.getCategories()
				.add(categoryMapper.toDto(cat)));
		return dto;
	}

	public Product toEntity(ProductDTO dto) {
		Product product = Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.imgUrl(dto.getImgUrl())
				.date(dto.getDate())
				.build();
		return product;
	}
	public Product toEntityWithCategories(ProductDTO dto) {
		Product product =  Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.imgUrl(dto.getImgUrl())
				.date(dto.getDate())
				.build();
		product.getCategories().clear();
		dto.getCategories().forEach(cat -> product
				.getCategories()
				.add(categoryMapper.toEntity(cat)));
		 return product;
	}

}
