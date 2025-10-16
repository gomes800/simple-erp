package com.gom.erp_system.modules.sales.model;

import com.gom.erp_system.modules.customers.model.Customer;
import com.gom.erp_system.modules.sales.model.dto.CreateOrderDTO;
import com.gom.erp_system.modules.sales.model.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Customer customer;

    private LocalDateTime createDate;
    private BigDecimal totalValue;
    private Integer discount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private String observations;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> products = new ArrayList<>();

    public Order(CreateOrderDTO dto, Customer customer, List<OrderProduct> orderProducts) {
        this.customer = customer;
        this.discount = dto.getDiscount();
        this.observations = dto.getObservations();
        this.products = orderProducts;
        this.createDate = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

}
