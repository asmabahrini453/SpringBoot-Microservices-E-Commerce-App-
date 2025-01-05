package com.springbootmicroserviceprojet.order.order;

import com.springbootmicroserviceprojet.order.customer.CustomerClient;
import com.springbootmicroserviceprojet.order.exception.BusinessException;
import com.springbootmicroserviceprojet.order.kafka.OrderConfirmation;
import com.springbootmicroserviceprojet.order.kafka.OrderProducer;
import com.springbootmicroserviceprojet.order.orderline.OrderLineRequest;
import com.springbootmicroserviceprojet.order.orderline.OrderLineService;
import com.springbootmicroserviceprojet.order.payment.PaymentClient;
import com.springbootmicroserviceprojet.order.payment.PaymentRequest;
import com.springbootmicroserviceprojet.order.product.ProductClient;
import com.springbootmicroserviceprojet.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.StaleObjectStateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        try {
            // Ensure that the customer is found before proceeding
            var customer = customerClient.findCustomerById(request.customerId())
                    .orElseThrow(() -> new BusinessException("Cannot create order: No customer exists with the provided ID"));

            // Ensure products are successfully purchased
            var purchasedProducts = productClient.purchaseProducts(request.products());

            // Save the main order and retrieve the saved order object
            var order = repository.save(mapper.toOrder(request));

            // Check if there are products in the order
            if (request.products() != null && !request.products().isEmpty()) {
                // Iterate through the purchased products and save the order lines
                for (PurchaseRequest purchaseRequest : request.products()) {
                    orderLineService.saveOrderLine(new OrderLineRequest(
                            null,  // `null` should work if the `id` is auto-generated
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    ));
                }
            }

            // Handle the payment request for the order
            var paymentRequest = new PaymentRequest(
                    request.amount(),
                    request.paymentMethod(),
                    order.getId(),
                    order.getReference(),
                    customer
            );
            paymentClient.requestOrderPayment(paymentRequest);

            // Send an order confirmation
            orderProducer.sendOrderConfirmation(new OrderConfirmation(
                    request.reference(),
                    request.amount(),
                    request.paymentMethod(),
                    customer,
                    purchasedProducts
            ));

            return order.getId();  // Return the saved order's ID
        } catch (StaleObjectStateException e) {
            throw new BusinessException("The order was modified by another transaction. Please try again.");
        }
    }


    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}