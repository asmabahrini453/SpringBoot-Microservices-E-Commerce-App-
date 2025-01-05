package com.springbootmicroserviceprojet.order.orderline;

import com.springbootmicroserviceprojet.order.order.Order;
import com.springbootmicroserviceprojet.order.order.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;
    private final OrderRepository orderRepository; // Add this

    @Transactional
    public Integer saveOrderLine(OrderLineRequest request) {
        // First get the actual Order entity from the database
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Create the OrderLine
        OrderLine orderLine = mapper.toOrderLine(request);
        orderLine.setOrder(order); // Set the managed Order entity

        return repository.save(orderLine).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}