package com.devsuperior.dscatalog.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mapper.ProductMapper;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	ProductMapper productMapper;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(PageRequest pageRequest) {
		return productRepository.findAll(pageRequest).map(cat -> productMapper.toDto(cat));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return productMapper.toDtoWithCategories(productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found! Id = " + id)));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
	//	entity.setName(dto.getName());
		entity = productRepository.save(entity);
		return productMapper.toDto(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product obj;
		try {
			obj = productRepository.getOne(id);
			obj.setName(dto.getName());
			
			obj = productRepository.save((obj));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Product not found! Id = " + id);
		}
		return productMapper.toDto(obj);
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Product not found! Id = " + id);
		}
		/*
		 * Garante integridade referencial do banco
		 */
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
