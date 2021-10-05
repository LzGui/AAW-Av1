package com.newtonpaiva.aaw.av1.api;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.newtonpaiva.aaw.av1.domain.entity.Produto;
import com.newtonpaiva.aaw.av1.domain.enums.ProdutoStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String urlPicture;
	private Float price;
	private ProdutoStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime ModifiedAt;
	
	public ProdutoDto(Produto prod) {
		this.id = prod.getId();
		this.name = prod.getName();
		this.description = prod.getDescription();
		this.urlPicture = prod.getUrlPicture();
		this.price = prod.getPrice();
		this.status = prod.getStatus();
		this.setCreatedAt(prod.getCreatedAt());
		this.setModifiedAt(prod.getModifiedAt());
	}
}
