package com.newtonpaiva.aaw.av1.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.newtonpaiva.aaw.av1.api.ProdutoDto;
import com.newtonpaiva.aaw.av1.domain.enums.ProdutoStatus;

class ProdutoTest {

	@Test
	void test_createFromDto_withValidDto_shouldCreateOk() {
		var dto = new ProdutoDto();
		
		dto.setName("Notebook Gamer");
		dto.setDescription("Notebook Gamer Dell, 8GB RAM, I7 10750H, SSD 1TB, NVIDIA 1660TI");
		dto.setUrlPicture("Foto Disponivel");
		dto.setPrice(8500.0F);
		dto.setStatus(ProdutoStatus.DISPONIVEL);
		
		var result = new Produto(dto);
		
		assertEquals(dto.getName(), result.getName());
		assertEquals(dto.getDescription(), result.getDescription());
		assertEquals(dto.getUrlPicture(), result.getUrlPicture());
		assertEquals(dto.getPrice(), result.getPrice());
		assertEquals(dto.getStatus(), result.getStatus());
	}

}
