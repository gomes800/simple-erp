package com.gom.erp_system.modules.products.repository;

import com.gom.erp_system.modules.products.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"supplier"})
    Page<Product> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"supplier"})
    @Query("SELECT p FROM Product p WHERE p.stock <= p.minimumStock")
    Page<Product> findProductsWithLowStock(Pageable pageable);
}
