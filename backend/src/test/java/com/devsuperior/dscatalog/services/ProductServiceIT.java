package com.devsuperior.dscatalog.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalProducts;
	

	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void findAllShouldReturnPageWhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = productService.findAll(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(result.getNumber(), 0);
		Assertions.assertEquals(result.getSize(), 10);
		Assertions.assertEquals(countTotalProducts, result.getTotalElements());
	}
	
	
	@Test
	public void findAllShouldReturnPageWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<ProductDTO> result = productService.findAll(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	
	@Test
	public void findAllShouldReturnSortedPageWhenPageSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		
		
		Page<ProductDTO> result = productService.findAll(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
		Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
		Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		productService.delete(existingId);
		Assertions.assertEquals(countTotalProducts-1, productRepository.count());
		
	}
	
	@Test
	public void deleteShouldthrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.delete(nonExistingId);
		});
		
	}
}
