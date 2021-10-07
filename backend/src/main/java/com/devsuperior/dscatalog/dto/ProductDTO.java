package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	private Instant date;
	private Set<CategoryDTO> categories;
	
	public Set<CategoryDTO> getCategories() {
		if(categories == null) {
			categories = new HashSet<>();
		}
		return categories;
	}
}
