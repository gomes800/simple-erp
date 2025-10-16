package com.gom.erp_system.modules.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    private Long productId;
    private Integer quantity;
}
