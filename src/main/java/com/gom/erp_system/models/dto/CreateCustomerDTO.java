package com.gom.erp_system.models.dto;

import com.gom.erp_system.models.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDTO {
    private String name;
    private CustomerType type;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
}
