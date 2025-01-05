package com.springbootmicroserviceproject.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    // Create a new customer
    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        // Ensure the response returns the generated customer ID
        String customerId = service.createCustomer(request);
        return ResponseEntity.ok(customerId);  // Return the customer ID after creation
    }

    // Update an existing customer
    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        // Ensure the update uses the request data to update the customer
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        // Returns a list of customers with their details
        return ResponseEntity.ok(service.findAllCustomers());
    }

    // Check if customer exists by id
    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            @PathVariable("customer-id") String customerId
    ) {
        // Returns whether a customer exists by ID
        return ResponseEntity.ok(service.existsById(customerId));
    }

    // Get a customer by id
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") String customerId
    ) {
        // Returns customer details by ID
        return ResponseEntity.ok(service.findById(customerId));
    }

    // Delete a customer by id
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(
            @PathVariable("customer-id") String customerId
    ) {
        // Delete the customer by ID
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
