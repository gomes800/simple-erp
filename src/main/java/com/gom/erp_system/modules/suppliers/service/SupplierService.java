package com.gom.erp_system.modules.suppliers.service;

import com.gom.erp_system.modules.suppliers.model.Supplier;
import com.gom.erp_system.modules.suppliers.model.dto.CreateSupplierDTO;
import com.gom.erp_system.modules.suppliers.model.dto.SupplierResponseDTO;
import com.gom.erp_system.modules.suppliers.model.dto.UpdateSupplierDTO;
import com.gom.erp_system.modules.suppliers.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public Page<SupplierResponseDTO> getAllSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return supplierRepository.findAll(pageable)
                .map(SupplierResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public SupplierResponseDTO getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));
        return SupplierResponseDTO.fromEntity(supplier);
    }

    public SupplierResponseDTO createSupplier(CreateSupplierDTO dto) {
        Supplier newSupplier = new Supplier();
        newSupplier.setName(dto.getName());
        newSupplier.setCNPJ(dto.getCNPJ());
        newSupplier.setAddress(dto.getAddress());
        newSupplier.setPhone(dto.getPhone());
        newSupplier.setEmail(dto.getEmail());
        newSupplier.setRegisterDate(LocalDateTime.now());
        newSupplier.setStatus(true);

        supplierRepository.save(newSupplier);
        return SupplierResponseDTO.fromEntity(newSupplier);
    }

    public SupplierResponseDTO updateSupplier(Long id, UpdateSupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));

        if (dto.getName() != null) supplier.setName(dto.getName());
        if (dto.getCNPJ() != null) supplier.setCNPJ(dto.getCNPJ());
        if (dto.getAddress() != null) supplier.setAddress(dto.getAddress());
        if (dto.getPhone() != null) supplier.setPhone(dto.getPhone());
        if (dto.getEmail() != null) supplier.setEmail(dto.getEmail());
        if (dto.getStatus() != null) supplier.setStatus(dto.getStatus());

        supplierRepository.save(supplier);
        return SupplierResponseDTO.fromEntity(supplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));
        supplierRepository.delete(supplier);
    }

    public SupplierResponseDTO deactivateSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found."));
        supplier.setStatus(false);
        supplierRepository.save(supplier);
        return SupplierResponseDTO.fromEntity(supplier);
    }

}
