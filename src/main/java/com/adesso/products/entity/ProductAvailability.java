package com.adesso.products.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="productavailability")
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductAvailability {
	
	@Id
	@UuidGenerator
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(columnDefinition="decimal", precision=18, scale=2)
    private BigDecimal price;
    @Column(name= "availability")
    private boolean available;
    @Column
    private boolean deleted;
    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;
    
	public UUID getId() {
		return id;
	}
	
	public BigDecimal getPrice() {
		if (price == null) {
			return new BigDecimal(0);
		}
		return price;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public Product getProduct() {
		return product;
	}
}