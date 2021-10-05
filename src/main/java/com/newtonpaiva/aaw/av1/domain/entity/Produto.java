package com.newtonpaiva.aaw.av1.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.newtonpaiva.aaw.av1.api.ProdutoDto;
import com.newtonpaiva.aaw.av1.domain.enums.ProdutoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String urlPicture;
	private Float price;
	private ProdutoStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime ModifiedAt;

	public Produto(ProdutoDto prodDto) {
		this.id = prodDto.getId();
		this.name = prodDto.getName();
		this.description = prodDto.getDescription();
		this.urlPicture = prodDto.getUrlPicture();
		this.price = prodDto.getPrice();
		this.status = prodDto.getStatus();
	}
}
