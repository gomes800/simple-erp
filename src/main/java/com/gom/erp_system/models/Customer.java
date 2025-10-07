package com.gom.erp_system.models;

import com.gom.erp_system.models.dto.CreateCustomerDTO;
import com.gom.erp_system.models.enums.CustomerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType type;

    private String documentNumber;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime registerDate;
    private boolean status;

    public Customer(CreateCustomerDTO dto) {
        this.name = dto.getName();
        this.type = dto.getType();
        this.documentNumber = dto.getDocumentNumber();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.address = dto.getAddress();
    }
}
