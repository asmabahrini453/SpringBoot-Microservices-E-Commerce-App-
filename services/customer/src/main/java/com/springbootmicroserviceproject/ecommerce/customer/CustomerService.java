package com.springbootmicroserviceproject.ecommerce.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;


    public String createCustomer(CustomerRequest request) {
        Customer customer = new Customer(
                null,
                request.firstname(),
                request.lastname(),
                request.email(),
                request.address()
        );
        repository.save(customer);
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer existingCustomer = repository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setFirstname(request.firstname());
        existingCustomer.setLastname(request.lastname());
        existingCustomer.setEmail(request.email());
        existingCustomer.setAddress(request.address());

        repository.save(existingCustomer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return repository.findAll().stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFirstname(),
                        customer.getLastname(),
                        customer.getEmail(),
                        customer.getAddress()
                ))
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(String customerId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

    public boolean existsById(String customerId) {
        return repository.existsById(customerId);
    }

    public void deleteCustomer(String customerId) {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        repository.delete(customer);
    }
}
