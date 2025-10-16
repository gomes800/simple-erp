package com.gom.erp_system.modules.sales.service;

import com.gom.erp_system.modules.customers.model.Customer;
import com.gom.erp_system.modules.customers.repository.CustomerRepository;
import com.gom.erp_system.modules.products.model.Product;
import com.gom.erp_system.modules.products.repository.ProductRepository;
import com.gom.erp_system.modules.sales.model.Order;
import com.gom.erp_system.modules.sales.model.OrderProduct;
import com.gom.erp_system.modules.sales.model.dto.CreateOrderDTO;
import com.gom.erp_system.modules.sales.model.dto.OrderProductDTO;
import com.gom.erp_system.modules.sales.model.dto.OrderResponseDTO;
import com.gom.erp_system.modules.sales.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    private BigDecimal calculateTotalValue(List<OrderProductDTO> products) {
        if (products == null || products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return products.stream()
                .map(p -> p.getUnitPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalValue(List<OrderProductDTO> products, Integer discount) {
        BigDecimal total = calculateTotalValue(products);

        if (discount != null && discount > 0) {
            BigDecimal discountAmount = total
                    .multiply(BigDecimal.valueOf(discount))
                    .divide(BigDecimal.valueOf(100));
            return total.subtract(discountAmount);
        }

        return total;
    }

    public OrderResponseDTO createOrder(CreateOrderDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));

        List<OrderProduct> orderProducts = dto.getProducts().stream()
                .map(p -> {
                    Product product = productRepository.findById(p.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found."));
                    return new OrderProduct(product, p.getQuantity());
                })
                .collect(Collectors.toUnmodifiableList());

        Order order = new Order(dto, customer,orderProducts);
        order.setTotalValue(calculateTotalValue(dto.getProducts()));

        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }
}
