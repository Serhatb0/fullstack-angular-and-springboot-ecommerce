package com.biricik.ecommerce.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biricik.ecommerce.dto.PaymentInfoDto;
import com.biricik.ecommerce.dto.PurchaseDto;
import com.biricik.ecommerce.dto.PurchaseResponse;
import com.biricik.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;


@RestController
@RequestMapping("/api/checkout")
public class CheckoutServiceController {
	
	private CheckoutService checkoutService;

	public CheckoutServiceController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}
	
	@PostMapping("/purchase")
	public PurchaseResponse placeOrder(@RequestBody PurchaseDto purchaseDto) {
		PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchaseDto);
		return purchaseResponse;
	}
	
	@PostMapping("/payment-intent")
	public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoDto paymentInfoDto) throws StripeException{
		
		PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfoDto);
		
		String paymentStr = paymentIntent.toJson();
		
		return new ResponseEntity<>(paymentStr,HttpStatus.OK);
	}
}
