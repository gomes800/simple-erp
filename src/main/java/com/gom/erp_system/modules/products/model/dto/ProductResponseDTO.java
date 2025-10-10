package com.gom.erp_system.modules.products.model.dto;

import com.gom.erp_system.modules.products.model.Product;
import com.gom.erp_system.modules.products.model.enums.Categories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private Integer minimumStock;
    private Categories category;
    private LocalDateTime registerDate;
    private String supplier;

    public static ProductResponseDTO fromEntity(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .salePrice(product.getSalePrice())
                .stock(product.getStock())
                .minimumStock(product.getMinimumStock())
                .category(product.getCategory())
                .registerDate(product.getRegisterDate())
                .supplier(product.getSupplier())
                .build();
    }
}
