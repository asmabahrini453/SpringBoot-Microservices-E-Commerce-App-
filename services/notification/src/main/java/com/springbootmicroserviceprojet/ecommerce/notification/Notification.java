package com.springbootmicroserviceprojet.ecommerce.notification;

import com.springbootmicroserviceprojet.ecommerce.kafka.order.OrderConfirmation;
import com.springbootmicroserviceprojet.ecommerce.kafka.payment.PaymentConfirmation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;

    @Embedded
    private OrderConfirmation orderConfirmation;

    @Embedded
    private PaymentConfirmation paymentConfirmation;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
