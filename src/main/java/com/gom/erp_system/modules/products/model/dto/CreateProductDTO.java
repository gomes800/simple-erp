package com.gom.erp_system.modules.products.model.dto;

import com.gom.erp_system.modules.products.model.enums.Categories;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private Integer minimumStock;
    private Categories category;
    private String supplier;
}
