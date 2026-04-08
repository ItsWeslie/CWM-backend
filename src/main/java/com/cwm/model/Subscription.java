package com.cwm.model;

import com.cwm.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "subscriptions",
        indexes = {
                @Index(name = "idx_subscription_month",columnList = "month"),
                @Index(name = "idx_subscription_year",columnList = "year")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(nullable = false)
    private Year year;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            referencedColumnName = "member_id",
            name = "member_id",
            foreignKey = @ForeignKey(name = "fk_subscription_member_id")
    )
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
