package com.gom.erp_system.modules.suppliers.model.dto;

import com.gom.erp_system.modules.suppliers.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponseDTO {
    Long id;
    String name;
    String CNPJ;
    String address;
    String phone;
    String email;
    LocalDateTime registerDate;
    boolean status;

    public static SupplierResponseDTO fromEntity(Supplier supplier) {
        return new SupplierResponseDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getCNPJ(),
                supplier.getAddress(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getRegisterDate(),
                supplier.isStatus()
        );
    }
}
