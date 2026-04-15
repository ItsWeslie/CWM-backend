package com.cwm.controller.admin;

import com.cwm.api.APIConstants;
import com.cwm.dto.APIResponse;
import com.cwm.dto.PageResponse;
import com.cwm.dto.payment.PaymentRequest;
import com.cwm.dto.payment.PaymentResponse;
import com.cwm.service.adminService.AdminPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIConstants.Admin.PAYMENT)
@PreAuthorize("hasRole('ADMIN')")
public class AdminPaymentController {

    private final AdminPaymentService adminPaymentService;

    @GetMapping
    public ResponseEntity<PageResponse<PaymentResponse>> getAllPayments(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminPaymentService.getAllPayments(pageable);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> createPayment(@Valid @ModelAttribute PaymentRequest paymentRequest) throws IOException {
        return adminPaymentService.createPayment(paymentRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deletePayment(@PathVariable Long id) {
        return adminPaymentService.deletePayment(id);
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse> updatePayment(@PathVariable Long id,
                                                     @Valid @ModelAttribute PaymentRequest paymentRequest) throws IOException {
        return adminPaymentService.updatePayment(id,paymentRequest);
    }


}
