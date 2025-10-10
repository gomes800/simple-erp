package com.gom.erp_system.modules.suppliers.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSupplierDTO {
    private String name;
    private String CNPJ;
    private String address;
    private String phone;
    private String email;
}
