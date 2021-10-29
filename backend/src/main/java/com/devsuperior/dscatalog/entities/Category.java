package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tb_category")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public @Data class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String name;

	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;

	@Setter(value = AccessLevel.NONE)
	@Getter(value = AccessLevel.NONE)
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Product> products;

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = Instant.now();
	}
	
	public Set<Product> getProducts () {
		if (products == null) {
			products = new HashSet<>();
		}
		return products;
	}
}
