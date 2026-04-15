package com.cwm.repository;

import com.cwm.model.PaymentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDocumentRepo extends JpaRepository<PaymentDocument, Long> {
}
