package com.gom.erp_system.modules.sales.model.dto;

import com.gom.erp_system.modules.sales.model.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalItemValue;

    public static OrderProductResponseDTO fromEntity(OrderProduct op) {
        return OrderProductResponseDTO.builder()
                .productId(op.getProduct().getId())
                .productName(op.getProduct().getName())
                .quantity(op.getQuantity())
                .unitPrice(op.getUnitPrice())
                .totalItemValue(op.getUnitPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .build();
    }
}
