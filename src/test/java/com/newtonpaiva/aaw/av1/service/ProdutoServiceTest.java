package com.newtonpaiva.aaw.av1.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.newtonpaiva.aaw.av1.domain.entity.Produto;
import com.newtonpaiva.aaw.av1.domain.enums.ProdutoStatus;
import com.newtonpaiva.aaw.av1.exception.NotFoundException;
import com.newtonpaiva.aaw.av1.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

	@Mock
	private ProdutoRepository produtoRepository;
	
	private ProdutoService unit;
	
	@BeforeEach
	public void setup() {
		unit = new ProdutoService(produtoRepository);
	}

	@Test
	void test_getById_withProduto_shouldReturnObj() {
		var produto = new Produto();
		produto.setId("produto1");
		
		Mockito.when(produtoRepository.findById("produto1")).thenReturn(Optional.of(produto));
		
		var result = unit.get("produto1");
		
		Assertions.assertEquals(result, produto);
		
		Mockito.verify(produtoRepository).findById("produto1");
	}
	
	@Test
	void test_getById_withNoProduto_shouldThrowException() {
		var id = "produto1";
		Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.empty());

		try {
			unit.get(id);
			fail("Expected NotFoundException");
		} catch (NotFoundException ex) {
			Assertions.assertEquals(ex.getMessage(), "Produto com ID " + id + " nao encontrado");
		}		

		Mockito.verify(produtoRepository).findById(id);		
	}
	
	@Test
	void test_delete_withValidId_shouldDeleteOk() {
		var id = "001";
		var produto = new Produto();
		
		Mockito.when(produtoRepository.findById(id))
			.thenReturn(Optional.of(produto));
		
		unit.delete(id);
		
		Mockito.verify(produtoRepository).findById(id);
		Mockito.verify(produtoRepository).deleteById(id);
	}
	
	@Test
	void test_delete_withInvalidId_shouldThrowException() {
		var id = "002";
		
		Mockito.when(produtoRepository.findById(id))
			.thenReturn(Optional.empty());
		
		try {
			unit.get(id);
			fail("Expected NotFoundException");
		} catch (NotFoundException ex) {
			Assertions.assertEquals(ex.getMessage(), "Produto com ID " + id + " nao encontrado");
		}
		
		Mockito.verify(produtoRepository).findById(id);
		Mockito.verifyNoMoreInteractions(produtoRepository);
	}
	
	@Test
	void test_getAll() {
		var list = new ArrayList<Produto>();
		list.add(new Produto().withId("001").withName("Notebook"));
		
		Mockito.when(produtoRepository.findAll()).thenReturn(list);
		
		var result = unit.getAll();
		
		assertEquals(result, list);
		
		Mockito.verify(produtoRepository).findAll();
	}
	
	@Test
	void test_create() {
		var produto = new Produto();
		
		var result = unit.create(produto);
		
		assertNotNull(result.getCreatedAt());
		assertNotNull(result.getModifiedAt());
		
		Mockito.verify(produtoRepository).save(produto);
	}
	
	@Test
	void test_update_withValidId_shouldUpdateOk() {
		var id = "001";
		var exist = new Produto();
		exist.setName("Caneca");
		exist.setDescription("Caneca 300ml");
		exist.setUrlPicture("Foto indisponivel");
		exist.setPrice(25.0f);
		exist.setStatus(ProdutoStatus.CANCELADO);
		exist.setCreatedAt(LocalDateTime.now().minusDays(1));
		exist.setModifiedAt(LocalDateTime.now().minusDays(1));
		
		var updatedProduto = new Produto();
		updatedProduto.setName("Caneca Darth Vader");
		updatedProduto.setDescription("Caneca Star Wars, 400ml");
		updatedProduto.setUrlPicture("Foto disponivel");
		updatedProduto.setPrice(40.00f);
		updatedProduto.setStatus(ProdutoStatus.DISPONIVEL);
		
		Mockito.when(produtoRepository.findById(id))
			.thenReturn(Optional.of(exist));
		
		var result = unit.update(id, updatedProduto);
		
		assertEquals(result.getName(), updatedProduto.getName());
		assertEquals(result.getDescription(), updatedProduto.getDescription());
		assertEquals(result.getUrlPicture(), updatedProduto.getUrlPicture());
		assertEquals(result.getPrice(), updatedProduto.getPrice());
		assertEquals(result.getStatus(), updatedProduto.getStatus());;
		assertTrue(result.getModifiedAt().isAfter(result.getCreatedAt()));
		
		Mockito.verify(produtoRepository).findById(id);
		Mockito.verify(produtoRepository).save(exist);
	}
	@Test
	void test_update_withInvalidId_shouldthrowException() {
		var id = "001";
		var updated = new Produto();
				
		Mockito.when(produtoRepository.findById(id))
			.thenReturn(Optional.empty());
		
		try {
			unit.update(id, updated);
			fail("Expected NotFoundException");			
		} catch (NotFoundException ex) {
			assertEquals(ex.getMessage(), "Produto com ID " + id + " nao encontrado");			
		}
		
		Mockito.verify(produtoRepository).findById(id);
		Mockito.verifyNoMoreInteractions(produtoRepository);		
	}

}
