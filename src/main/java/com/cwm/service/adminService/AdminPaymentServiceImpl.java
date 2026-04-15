package com.cwm.service.adminService;

import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.payment.PaymentRequest;
import com.cwm.dto.payment.PaymentResponse;
import com.cwm.exception.PaymentNotFoundException;
import com.cwm.mapper.PaymentMapper;
import com.cwm.model.Payment;
import com.cwm.repository.PaymentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPaymentServiceImpl implements AdminPaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PageResponse<PaymentResponse>> getAllPayments(Pageable pageable) {

        Page<Payment> payments = paymentRepo.findAll(pageable);

        Page<PaymentResponse> paymentResponses = payments.map(payment -> paymentMapper
                .toPaymentResponse(payment,payment.getDocument()));

        PageResponse<PaymentResponse> response = PageResponse.<PaymentResponse>builder()
                .content(paymentResponses.getContent())
                .page(paymentResponses.getNumber())
                .size(paymentResponses.getSize())
                .totalElements(paymentResponses.getTotalElements())
                .totalPages(paymentResponses.getTotalPages())
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> createPayment(PaymentRequest paymentRequest) throws IOException {
            log.info("createPayment method called");
            Payment newPayment = paymentMapper.apply(paymentRequest);
            log.info("after payment mapper called");
            paymentRepo.save(newPayment);
            return ResponseEntity.ok(new APIResponse("Payment created successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> deletePayment(Long id) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(()->new PaymentNotFoundException("Payment not found with id: "+id));
        paymentRepo.delete(payment);
        return ResponseEntity.ok(new APIResponse("Payment deleted successfully"));
    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse> updatePayment(Long id, PaymentRequest paymentRequest) throws IOException {
        Payment existingPayment = paymentRepo.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id " + id));
        Payment updatedPayment = paymentMapper.existingPaymentToUpdatedPayment(existingPayment,paymentRequest);
        paymentRepo.save(updatedPayment);
        return ResponseEntity.ok(new APIResponse("Payment updated successfully"));
    }
}
