package com.gom.erp_system.modules.products.model;

import com.gom.erp_system.modules.products.model.dto.CreateProductDTO;
import com.gom.erp_system.modules.products.model.enums.Categories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private Integer minimumStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categories category;

    private LocalDateTime registerDate;
    private String supplier;

    public Product(CreateProductDTO dto) {
        this.code = dto.getCode();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.costPrice = dto.getCostPrice();
        this.salePrice = dto.getSalePrice();
        this.stock = dto.getStock();
        this.minimumStock = dto.getMinimumStock();
        this.category = dto.getCategory();
        this.supplier = dto.getSupplier();
    }
}
