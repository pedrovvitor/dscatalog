package com.devsuperior.dscatalog.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	private ProductMapper productMapper;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		return productRepository.findAll(pageable).map(cat -> productMapper.toDto(cat));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return productMapper.toDtoWithCategories(productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found! Id = " + id)));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		return productMapper.toDto(productRepository.save(productMapper.toEntity(dto)));
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = productRepository.getOne(id);
			dto.setId(product.getId());
			return productMapper.toDto(productRepository.save(productMapper.toEntityWithCategories(dto)));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Product not found! Id = " + id);
		}
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
