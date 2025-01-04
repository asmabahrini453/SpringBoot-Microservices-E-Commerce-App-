package com.springbootmicroserviceprojet.order.order;

import com.springbootmicroserviceprojet.order.customer.CustomerClient;
import com.springbootmicroserviceprojet.order.exception.BusinessException;
import com.springbootmicroserviceprojet.order.orderline.OrderLineRequest;
import com.springbootmicroserviceprojet.order.orderline.OrderLineService;
import com.springbootmicroserviceprojet.order.product.ProductClient;
import com.springbootmicroserviceprojet.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
    private final ProductClient productClient;
    private final OrderLineService orderLineService;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        //check if we have a customer or not
        //here we used feign client for http protocol
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        //purchase the product using the product microservice
        //here we used rest template for http protocol
        var purchasedProducts = productClient.purchaseProducts(request.products());

        //persist the order object
        var order = this.repository.save(mapper.toOrder(request));

        //persist the order line object
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        //start the payment process


        //send the order confirmation using the notifacation microservice (kafka)


        return order.getId();
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