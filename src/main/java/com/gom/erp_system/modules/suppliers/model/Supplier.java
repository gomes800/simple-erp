package com.gom.erp_system.modules.suppliers.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gom.erp_system.modules.products.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String CNPJ;
    private String address;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products;

    private LocalDateTime registerDate;
    private boolean status;

}
