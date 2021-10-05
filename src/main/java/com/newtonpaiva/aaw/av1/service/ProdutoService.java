package com.newtonpaiva.aaw.av1.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.newtonpaiva.aaw.av1.domain.entity.Produto;
import com.newtonpaiva.aaw.av1.exception.NotFoundException;
import com.newtonpaiva.aaw.av1.repository.ProdutoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdutoService {
	private final ProdutoRepository produtoRepository;
	
	public Produto create(Produto prod) {
		var now = LocalDateTime.now();
		
		prod.setCreatedAt(now);
		prod.setModifiedAt(now);
		
		produtoRepository.save(prod);
		
		return prod;
	}
	
	public Produto get(String id) {
		var produto = produtoRepository.findById(id);
		
		if (produto.isEmpty()) {
			throw new NotFoundException("Produto com ID " + id + " nao encontrado");
		}
		
		return produto.get();
	}
	
	public List<Produto> getAll(){
		return produtoRepository.findAll();
	}
	
	public void delete(String id) {
		get(id);
		produtoRepository.deleteById(id);
	}
	
	public Produto update(String id, Produto prod) {
		var exist = get(id);
		
		exist.setName(prod.getName());
		exist.setDescription(prod.getDescription());
		exist.setUrlPicture(prod.getUrlPicture());
		exist.setPrice(prod.getPrice());
		exist.setStatus(prod.getStatus());
		exist.setModifiedAt(LocalDateTime.now());
		
		produtoRepository.save(exist);
		
		return exist;
		
	}
}
