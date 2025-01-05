package com.springbootmicroserviceprojet.ecommerce.kafka;

import com.springbootmicroserviceprojet.ecommerce.email.EmailService;
import com.springbootmicroserviceprojet.ecommerce.kafka.order.OrderConfirmation;
import com.springbootmicroserviceprojet.ecommerce.kafka.payment.PaymentConfirmation;
import com.springbootmicroserviceprojet.ecommerce.notification.Notification;
import com.springbootmicroserviceprojet.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.springbootmicroserviceprojet.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.springbootmicroserviceprojet.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;
import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    @Transactional
    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming message from order-topic: {}", orderConfirmation);
        try {
            // Save notification
            Notification notification = Notification.builder()
                    .type(ORDER_CONFIRMATION)
                    .notificationDate(LocalDateTime.now())
                    .orderConfirmation(orderConfirmation)
                    .build();

            repository.save(notification);

            // Send email
            var customerName = orderConfirmation.getCustomer().getFirstname() + " "
                    + orderConfirmation.getCustomer().getLastname();

            emailService.sendOrderConfirmationEmail(
                    orderConfirmation.getCustomer().getEmail(),
                    customerName,
                    orderConfirmation.getTotalAmount(),
                    orderConfirmation.getOrderReference(),
                    orderConfirmation.getProducts()
            );
        } catch (Exception e) {
            log.error("Error processing order confirmation: ", e);
            throw e;
        }
    }

    @Transactional
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming message from payment-topic: {}", paymentConfirmation);
        try {
            // Save notification
            Notification notification = Notification.builder()
                    .type(PAYMENT_CONFIRMATION)
                    .notificationDate(LocalDateTime.now())
                    .paymentConfirmation(paymentConfirmation)
                    .build();

            repository.save(notification);

            // Send email
            var customerName = paymentConfirmation.getCustomerFirstname() + " "
                    + paymentConfirmation.getCustomerLastname();

            emailService.sendPaymentSuccessEmail(
                    paymentConfirmation.getCustomerEmail(),
                    customerName,
                    paymentConfirmation.getAmount(),
                    paymentConfirmation.getOrderReference()
            );
        } catch (Exception e) {
            log.error("Error processing payment confirmation: ", e);
            throw e;
        }
    }
}