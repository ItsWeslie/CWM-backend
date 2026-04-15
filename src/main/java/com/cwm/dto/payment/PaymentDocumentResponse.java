package com.cwm.dto.payment;

import jakarta.persistence.Basic;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDocumentResponse {

    private String documentName;
    private String documentType;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] documentData;
}
