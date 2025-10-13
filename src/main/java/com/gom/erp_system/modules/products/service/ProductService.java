package com.gom.erp_system.modules.products.service;

import com.gom.erp_system.modules.products.model.Product;
import com.gom.erp_system.modules.products.model.dto.CreateProductDTO;
import com.gom.erp_system.modules.products.model.dto.ProductResponseDTO;
import com.gom.erp_system.modules.products.model.dto.UpdateProductDTO;
import com.gom.erp_system.modules.products.repository.ProductRepository;
import com.gom.erp_system.modules.suppliers.model.Supplier;
import com.gom.erp_system.modules.suppliers.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .map(ProductResponseDTO::fromEntity);

    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));

        return ProductResponseDTO.fromEntity(product);
    }

    public ProductResponseDTO createProduct(CreateProductDTO dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));

        Product newProduct = new Product(dto);
        newProduct.setRegisterDate(LocalDateTime.now());
        newProduct.setSupplier(supplier);

        productRepository.save(newProduct);

        return ProductResponseDTO.fromEntity(newProduct);
    }

    public ProductResponseDTO updateProduct(Long id, UpdateProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));

        if (dto.getCode() != null) product.setCode(dto.getCode());
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getCostPrice() != null) product.setCostPrice(dto.getCostPrice());
        if (dto.getSalePrice() != null) product.setSalePrice(dto.getSalePrice());
        if (dto.getStock() != null) product.setStock(dto.getStock());
        if (dto.getMinimumStock() != null) product.setMinimumStock(dto.getMinimumStock());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));
            product.setSupplier(supplier);
        }

        productRepository.save(product);

        return ProductResponseDTO.fromEntity(product);
    }

    public void deleteProdutc(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getLowStockProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findProductsWithLowStock(pageable)
                .map(ProductResponseDTO::fromEntity);
    }

    public ProductResponseDTO updateSalePrice(Long id, BigDecimal newSalePrice) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));

        if (newSalePrice == null || newSalePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sale price must be greater than zero.");
        }
        product.setSalePrice(newSalePrice);
        productRepository.save(product);

        return ProductResponseDTO.fromEntity(product);
    }

}
