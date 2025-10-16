package com.gom.erp_system.modules.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {

    private Long customerId;
    private Integer discount;
    private String observations;
    private List<OrderProductDTO> products;
}
