package com.gom.erp_system.modules.sales.model.dto;

import com.gom.erp_system.modules.sales.model.Order;
import com.gom.erp_system.modules.sales.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime createDate;
    private BigDecimal totalValue;
    private Integer discount;
    private OrderStatus status;
    private String observations;
    private List<OrderProductResponseDTO> products;

    public static OrderResponseDTO fromEntity(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName())
                .createDate(order.getCreateDate())
                .totalValue(order.getTotalValue())
                .discount(order.getDiscount())
                .status(order.getStatus())
                .observations(order.getObservations())
                .products(
                        order.getProducts().stream()
                                .map(OrderProductResponseDTO::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
