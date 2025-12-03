package io.github.tawdi.smartshop.service;

import io.github.tawdi.smartshop.dto.payment.CreatePaymentRequestDTO;
import io.github.tawdi.smartshop.dto.payment.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO create(CreatePaymentRequestDTO dto, String orderId);

}
