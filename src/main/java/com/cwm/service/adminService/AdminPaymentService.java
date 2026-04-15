package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.payment.PaymentRequest;
import com.cwm.dto.payment.PaymentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminPaymentService {
    ResponseEntity<PageResponse<PaymentResponse>> getAllPayments(Pageable pageable);

    ResponseEntity<APIResponse> createPayment(PaymentRequest paymentRequest) throws IOException;

    ResponseEntity<APIResponse> deletePayment(Long id);

    ResponseEntity<APIResponse> updatePayment(Long id, PaymentRequest paymentRequest) throws IOException;
}
