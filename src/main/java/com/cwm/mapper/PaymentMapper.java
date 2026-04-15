package com.cwm.mapper;

import com.cwm.dto.payment.PaymentDocumentResponse;
import com.cwm.dto.payment.PaymentRequest;
import com.cwm.dto.payment.PaymentResponse;
import com.cwm.model.Payment;
import com.cwm.model.PaymentDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Year;
import java.util.List;

@Component
@Slf4j
public class PaymentMapper {

    private static final List<String> ALLOWED_TYPES = List.of(
            "application/pdf",
            "image/jpeg",
            "image/png"
    );

    private boolean validateFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
                throw new IllegalArgumentException(
                        "Invalid file type: " + contentType +
                                ". Allowed types: PDF, JPEG, PNG"
                );
            }
            return true;
        }
        return false;
    }

    public Payment apply(PaymentRequest paymentRequest) throws IOException {

        Payment payment = Payment.builder()
                .date(paymentRequest.date())
                .month(paymentRequest.date().getMonth())
                .year(Year.of(paymentRequest.date().getYear()))
                .paidTo(paymentRequest.paidTo())
                .amount(paymentRequest.amount())
                .description(paymentRequest.description())
                .build();

        MultipartFile file = paymentRequest.paymentDocument();

        if(validateFile(file)) {
            PaymentDocument document = PaymentDocument.builder()
                    .documentName(file.getOriginalFilename())
                    .documentType(file.getContentType())
                    .documentData(file.getBytes())
                    .payment(payment)
                    .build();
            payment.setDocument(document);
        }

        return payment;
    }

    public PaymentResponse toPaymentResponse(Payment payment, PaymentDocument paymentDocument) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .date(payment.getDate())
                .month(payment.getMonth())
                .year(payment.getYear())
                .paidTo(payment.getPaidTo())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .document(
                        PaymentDocumentResponse.builder()
                        .documentName(paymentDocument.getDocumentName())
                        .documentType(paymentDocument.getDocumentType())
                        .documentData(paymentDocument.getDocumentData())
                        .build())
                .build();
    }

    public Payment existingPaymentToUpdatedPayment(
            Payment existingPayment,
            PaymentRequest paymentRequest) throws IOException {

        existingPayment.setDate(paymentRequest.date());
        existingPayment.setMonth(paymentRequest.date().getMonth());
        existingPayment.setYear(Year.of(paymentRequest.date().getYear()));
        existingPayment.setPaidTo(paymentRequest.paidTo());
        existingPayment.setAmount(paymentRequest.amount());
        existingPayment.setDescription(paymentRequest.description());

        MultipartFile file = paymentRequest.paymentDocument();

        if (validateFile(file)) {

            PaymentDocument document = existingPayment.getDocument();

            if (document == null) {
                document = new PaymentDocument();
                document.setPayment(existingPayment);
            }
            document.setDocumentName(file.getOriginalFilename());
            document.setDocumentType(file.getContentType());
            document.setDocumentData(file.getBytes());

            existingPayment.setDocument(document);
        }

        return existingPayment;
    }
}
