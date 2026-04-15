package com.cwm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_id",
            referencedColumnName = "payment_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_id_payment_id")
    )
    @JsonBackReference
    private Payment payment;

    @Column(nullable = false)
    private String documentName;
    @Column(nullable = false)
    private String documentType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false,columnDefinition = "MEDIUMBLOB")
    private byte[] documentData;
}
