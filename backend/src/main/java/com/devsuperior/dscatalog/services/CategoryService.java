package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.mapper.CategoryMapper;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	CategoryMapper categoryMapper;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable).map(cat -> categoryMapper.toDto(cat));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return categoryMapper.toDto(categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Category not found for id: " + id))); 
	}

}
