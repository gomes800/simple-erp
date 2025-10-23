package com.gom.erp_system.modules.sales.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderDTO {
    private Long customerId;
    private Integer discount;
    private String observations;
}
