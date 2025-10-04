package com.gom.erp_system.services;

import com.gom.erp_system.models.Customer;
import com.gom.erp_system.models.dto.CreateCustomerDTO;
import com.gom.erp_system.models.dto.CustomerResponseDTO;
import com.gom.erp_system.models.dto.UpdateCustomerDTO;
import com.gom.erp_system.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CustomerService {

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public Page<CustomerResponseDTO> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return customerRepository.findAll(pageable)
                .map(CustomerResponseDTO::fromEntity);

    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));

        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO createCustomer(CreateCustomerDTO dto) {
        Customer newCustomer = new Customer(dto);
        newCustomer.setRegisterDate(LocalDateTime.now());
        newCustomer.setStatus(true);

        customerRepository.save(newCustomer);

        return CustomerResponseDTO.fromEntity(newCustomer);
    }

    public CustomerResponseDTO updateCustomer(Long id, UpdateCustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));
        if (dto.getName() != null) customer.setName(dto.getName());
        if (dto.getDocumentNumber() != null) customer.setDocumentNumber(dto.getDocumentNumber());
        if (dto.getType() != null) customer.setType(dto.getType());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());

        customerRepository.save(customer);

        return CustomerResponseDTO.fromEntity(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));
        customerRepository.delete(customer);
    }
}
