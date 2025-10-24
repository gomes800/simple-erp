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
import com.gom.erp_system.modules.sales.model.dto.UpdateOrderDTO;
import com.gom.erp_system.modules.sales.model.enums.OrderStatus;
import com.gom.erp_system.modules.sales.model.exception.InvalidOrderStateException;
import com.gom.erp_system.modules.sales.repository.OrderRepository;
import com.gom.erp_system.utils.EntityFinder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        Order order = EntityFinder.findOrThrow(orderRepository, id, "Order");

        return OrderResponseDTO.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);
        return  orderRepository.findAll(pageable)
                .map(OrderResponseDTO::fromEntity);
    }

    private BigDecimal calculateTotalValue(List<OrderProductDTO> products) {
        if (products == null || products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return products.stream()
                .map(p -> {
                    Product product = productRepository.findById(p.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + p.getProductId()));
                    return product.getSalePrice().multiply(BigDecimal.valueOf(p.getQuantity()));

                })
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
        Customer customer = EntityFinder.findOrThrow(customerRepository, dto.getCustomerId(), "Customer");

        List<OrderProduct> orderProducts = dto.getProducts().stream()
                .map(p -> {
                    Product product = EntityFinder.findOrThrow(productRepository, p.getProductId(), "Product");
                    return new OrderProduct(product, p.getQuantity());
                })
                .collect(Collectors.toUnmodifiableList());

        Order order = new Order(dto, customer,orderProducts);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalValue(calculateTotalValue(dto.getProducts()));

        orderRepository.save(order);

        return OrderResponseDTO.fromEntity(order);
    }

    public OrderResponseDTO updateOrder(Long id, UpdateOrderDTO dto) {
        Order order = EntityFinder.findOrThrow(orderRepository, id, "Order");

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStateException("Can't edit this order because it's not pending.");
        }

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found."));
            order.setCustomer(customer);
        }

        if (dto.getDiscount() != null) order.setDiscount(dto.getDiscount());
        if (dto.getObservations() != null) order.setObservations(dto.getObservations());

        orderRepository.save(order);
        return OrderResponseDTO.fromEntity(order);
    }

    public void deleteOrder(Long id) {
        Order order = EntityFinder.findOrThrow(orderRepository, id, "Order");
        orderRepository.delete(order);
    }
}
