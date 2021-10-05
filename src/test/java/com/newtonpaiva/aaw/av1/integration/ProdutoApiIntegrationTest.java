package com.newtonpaiva.aaw.av1.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonpaiva.aaw.av1.controller.ProdutoController;
import com.newtonpaiva.aaw.av1.domain.entity.Produto;
import com.newtonpaiva.aaw.av1.domain.enums.ProdutoStatus;
import com.newtonpaiva.aaw.av1.exception.NotFoundException;
import com.newtonpaiva.aaw.av1.service.ProdutoService;

@WebMvcTest(ProdutoController.class)
class ProdutoApiIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProdutoService produtoService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	void test_getById_withInvalidId_shouldReturn404() throws Exception {
		var id = "001";
		
		Mockito.when(produtoService.get(id))
			.thenThrow(new NotFoundException("Not found"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/" + id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNotFound()); 
		
		Mockito.verify(produtoService).get(id);
	}

	@Test
	void test_getById_withValidId_shouldReturn200() throws Exception {
		var id = "001";
		var produto = new Produto();
		
		produto.setId(id);
		produto.setName("Cafeteira");
		produto.setDescription("Cafeteira de capsula");
		produto.setUrlPicture("Foto aqui");
		produto.setPrice(215.0f);
		produto.setStatus(ProdutoStatus.INDISPONIVEL);
		
		Mockito.when(produtoService.get(id))
			.thenReturn(produto);
		
		var expectedJson = objectMapper.writeValueAsString(produto);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/" +id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.content().json(expectedJson))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
		
		Mockito.verify(produtoService).get(id);
	}
	
	@Test
	void test_delete_withValidId_shouldReturn204() throws Exception {
		var id = "001";
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/" + id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(produtoService).delete(id);
	}
	
	@Test
	void test_delete_withInvalidId_shouldReturn404() throws Exception {
		var id = "001";
		
		Mockito.doThrow(new NotFoundException("Not Found"))
			.when(produtoService)
			.delete(id);;
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/" + id))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(produtoService).delete(id);
	}

}
