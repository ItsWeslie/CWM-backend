package com.cwm.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "payments",indexes = {
        @Index(name = "idx_payment_month",columnList = "month"),
        @Index(name = "idx_payment_year",columnList = "year")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,name = "payment_id")
    private Long paymentId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(nullable = false)
    private Year year;

    @Column(nullable = false)
    private String paidTo;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String description;

    @OneToOne(mappedBy = "payment",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private PaymentDocument document;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean is_deleted;
}
