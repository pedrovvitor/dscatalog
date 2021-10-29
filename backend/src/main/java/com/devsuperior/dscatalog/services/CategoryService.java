package com.devsuperior.dscatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.mapper.CategoryMapper;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryMapper categoryMapper;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable).map(cat -> categoryMapper.toDto(cat));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		return categoryMapper.toDto(categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found! Id = " + id)));
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		return categoryMapper.toDto(categoryRepository.save(Category.builder().name(dto.getName()).build()));
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
			Category obj = categoryRepository.findById(id).orElseThrow(() ->
			new ResourceNotFoundException("Category not found! Id = " + id));
			obj.setName(dto.getName());
			obj = categoryRepository.save((obj));
			return categoryMapper.toDto(obj);
	}

	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Category not found! Id = " + id);
		}
		/*
		 * Garante integridade referencial do banco
		 */
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
