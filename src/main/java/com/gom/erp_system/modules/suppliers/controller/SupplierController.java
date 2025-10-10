package com.gom.erp_system.modules.suppliers.controller;

import com.gom.erp_system.modules.suppliers.model.dto.CreateSupplierDTO;
import com.gom.erp_system.modules.suppliers.model.dto.SupplierResponseDTO;
import com.gom.erp_system.modules.suppliers.model.dto.UpdateSupplierDTO;
import com.gom.erp_system.modules.suppliers.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponseDTO>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(supplierService.getAllSuppliers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@RequestBody CreateSupplierDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplierService.createSupplier(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(
            @PathVariable Long id,
            @RequestBody UpdateSupplierDTO dto) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<SupplierResponseDTO> deactivateSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(supplierService.deactivateSupplier(id));
    }
}
