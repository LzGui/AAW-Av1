package com.newtonpaiva.aaw.av1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.newtonpaiva.aaw.av1.api.ProdutoDto;
import com.newtonpaiva.aaw.av1.domain.entity.Produto;
import com.newtonpaiva.aaw.av1.service.ProdutoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ProdutoController {
	private final ProdutoService produtoService;
	
	@GetMapping("produtos/{id}")
	public ProdutoDto getByID(@PathVariable String id) {
		var produto = produtoService.get(id);
		
		return new ProdutoDto(produto);
	}
	
	@GetMapping("/produtos")
	public List<ProdutoDto> getAll() {
		var produtos = produtoService.getAll();
		var produtoDtos = new ArrayList<ProdutoDto>();
		
		for (var produto : produtos) {
			produtoDtos.add(new ProdutoDto(produto));
		}
		return produtoDtos;
	}
	
	@PostMapping("/produtos")
	public ProdutoDto create(@RequestBody ProdutoDto produtoDto) {
		var produto = new Produto(produtoDto);
		produto = produtoService.create(produto);
		
		return new ProdutoDto(produto);
	}
	
	@PutMapping("/produtos/{id}")
	public ProdutoDto create(@PathVariable String id, @RequestBody ProdutoDto produtoDto) {
		var produto = new Produto(produtoDto);
		produto = produtoService.update(id, produto);
		
		return new ProdutoDto(produto);
	}
	
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		produtoService.delete(id);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
}
