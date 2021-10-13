package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.mapper.CategoryMapper;
import com.devsuperior.dscatalog.mapper.ProductMapper;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	// Configurar comportamente simulado
	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;
	
	@Mock
	private CategoryMapper categoryMapper;

	@Mock
	private CategoryRepository categoryRepository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDTO;

	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		productDTO = Factory.createProductDTO();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(productMapper.toDtoWithCategories(product)).thenReturn(productDTO);
		Mockito.when(productMapper.toDto(product)).thenReturn(productDTO);

		Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(productRepository.getOne(existingId)).thenReturn(product);
		Mockito.when(productRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		

		Mockito.doNothing().when(productRepository).deleteById(existingId);

		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		ProductDTO dto = productMapper.toDtoWithCategories(product);
		ProductDTO result = productService.update(existingId, dto);
		
		Assertions.assertNotNull(result);

		Assertions.assertDoesNotThrow(() -> {
			productService.findById(existingId);
		});

		Mockito.verify(productRepository, Mockito.times(1)).findById(existingId);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = productService.findById(existingId);
		Assertions.assertNotNull(result);

		Assertions.assertDoesNotThrow(() -> {
			productService.findById(existingId);
		});

		Mockito.verify(productRepository, Mockito.times(2)).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.findById(nonExistingId);
		});

		Mockito.verify(productRepository).findById(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDTO> result = productService.findAll(pageable);

		Assertions.assertNotNull(result);
		Mockito.verify(productRepository).findAll(pageable);
	}

	@Test
	public void deleteShouldThrowDataBaseExceptionnWhenIdDoesNotExist() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			productService.delete(dependentId);
		});
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionnWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.delete(nonExistingId);
		});
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			productService.delete(existingId);
		});
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}

}
