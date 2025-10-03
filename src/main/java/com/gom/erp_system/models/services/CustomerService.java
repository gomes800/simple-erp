package com.gom.erp_system.models.services;

import com.gom.erp_system.models.Customer;
import com.gom.erp_system.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        Customer newCustomer = customer;
        return customerRepository.save(newCustomer);
    }

    public Customer updateCustomer(Long id, Customer update) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));
        if (update.getName() != null) customer.setName(update.getName());
        if (update.getCNPJ() != null) customer.setCNPJ(update.getCNPJ());
        if (update.getType() != null) customer.setType(update.getType());
        if (update.getEmail() != null) customer.setEmail(update.getEmail());
        if (update.getPhone() != null) customer.setPhone(update.getPhone());
        if (update.getAddress() != null) customer.setAddress(update.getAddress());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found."));
        customerRepository.delete(customer);
    }
}
