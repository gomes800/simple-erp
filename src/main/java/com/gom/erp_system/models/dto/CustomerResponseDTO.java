package com.gom.erp_system.models.dto;

import com.gom.erp_system.models.Customer;
import com.gom.erp_system.models.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private CustomerType type;
    private String documentNumber;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime registerDate;
    private boolean status;

    public static CustomerResponseDTO fromEntity(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .type(customer.getType())
                .documentNumber(customer.getDocumentNumber())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .registerDate(customer.getRegisterDate())
                .status(customer.isStatus())
                .build();
    }
}
