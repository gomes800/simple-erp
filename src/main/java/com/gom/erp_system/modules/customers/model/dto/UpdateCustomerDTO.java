package com.gom.erp_system.modules.customers.model.dto;

import com.gom.erp_system.modules.customers.model.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDTO {
    private String name;
    private CustomerType type;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
}
