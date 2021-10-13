package com.devsuperior.dscatalog.tests;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mapper.ProductMapper;

public class Factory {

	@Autowired
	ProductMapper productMapper;

	public static Product createProduct() {
		
		Product product = Product.builder()
				.id(1L)
				.name("Phone")
				.description("A good phone")
				.price(800.0)
				.imgUrl("https://img.com/img.png")
				.date(Instant.parse("2020-07-14T10:00:00Z")).build();
		product
		.getCategories()
		.add(Category
				.builder()
				.id(2L)
				.name("Eletronics")
				.build());
		
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		
		ProductDTO dto = ProductDTO.builder()
				.id(1L)
				.name("Phone")
				.description("A good phone")
				.price(800.0)
				.imgUrl("https://img.com/img.png")
				.date(Instant.parse("2020-07-14T10:00:00Z")).build();
		dto
		.getCategories()
		.add(CategoryDTO
				.builder()
				.id(2L)
				.name("Eletronics")
				.build());
		
		return dto;
	}
	
	public static Category createCategory () {
		return Category.builder().id(1L).name("Eletronics").build();
	}

}
