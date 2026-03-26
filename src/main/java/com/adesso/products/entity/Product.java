package com.adesso.products.entity;


import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productsSeqNumber")
	@SequenceGenerator(name = "productsSeqNumber", sequenceName = "products_seq", allocationSize = 1)
	private Long id;
	@Column
    private String name;
	@Column
    private String description;
	@Column(columnDefinition = "DATE")
    private Date created;
	@Column(columnDefinition = "DATE")
    private Date updated;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProductAvailability availability;
}
