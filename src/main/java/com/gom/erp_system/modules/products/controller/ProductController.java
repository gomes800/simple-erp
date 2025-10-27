package com.gom.erp_system.modules.products.controller;

import com.gom.erp_system.modules.products.model.dto.CreateProductDTO;
import com.gom.erp_system.modules.products.model.dto.ProductResponseDTO;
import com.gom.erp_system.modules.products.model.dto.UpdateProductDTO;
import com.gom.erp_system.modules.products.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody CreateProductDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductDTO dto) {

        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsWithLowStock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(productService.getLowStockProducts(page, size));
    }

    @PatchMapping("/{id}/sale-price")
    public ResponseEntity<ProductResponseDTO> updateSalePrice(
            @PathVariable Long id,
            @RequestBody @NotNull Map<String, BigDecimal> request
    ) {
        BigDecimal newSalePrice = request.get("salePrice");
        ProductResponseDTO updated = productService.updateSalePrice(id, newSalePrice);
        
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity ) {
        return ResponseEntity.ok(productService.updateStock(id, quantity));
    }
}
