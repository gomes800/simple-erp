package com.gom.erp_system.modules.suppliers.repository;

import com.gom.erp_system.modules.suppliers.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Page<Supplier> findAll(Pageable pageable);
}
