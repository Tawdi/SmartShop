package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.dto.payment.CreatePaymentRequestDTO;
import io.github.tawdi.smartshop.dto.payment.PaymentResponseDTO;
import io.github.tawdi.smartshop.dto.payment.UpdatePaymentStatusRequestDTO;

public interface PaymentService {

    PaymentResponseDTO create(CreatePaymentRequestDTO dto, String orderId);

    PaymentResponseDTO updateStatus(Long paymentId, UpdatePaymentStatusRequestDTO dto);

}
