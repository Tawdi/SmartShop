package io.github.tawdi.smartshop.controller;

import io.github.tawdi.smartshop.domain.entity.Payment;
import io.github.tawdi.smartshop.dto.ApiResponseDTO;
import io.github.tawdi.smartshop.dto.payment.CreatePaymentRequestDTO;
import io.github.tawdi.smartshop.dto.payment.PaymentResponseDTO;
import io.github.tawdi.smartshop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/order/{orderId}")
    public ResponseEntity<ApiResponseDTO<PaymentResponseDTO>> addPayment(  @PathVariable String orderId, @RequestBody CreatePaymentRequestDTO dto) {

        PaymentResponseDTO payment = paymentService.create(dto, orderId);

        return new  ResponseEntity<>(ApiResponseDTO.success("Le paiement a été réalisé avec succès.",payment),HttpStatus.CREATED);
    }
}
