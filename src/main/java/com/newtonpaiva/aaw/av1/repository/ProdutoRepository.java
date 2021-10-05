package com.newtonpaiva.aaw.av1.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.newtonpaiva.aaw.av1.domain.entity.Produto;

public interface ProdutoRepository extends MongoRepository<Produto, String>{

}
