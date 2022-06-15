package com.biricik.ecommerce.service;

import com.biricik.ecommerce.dto.PaymentInfoDto;
import com.biricik.ecommerce.dto.PurchaseDto;
import com.biricik.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
	PurchaseResponse placeOrder(PurchaseDto purchaseDto);
	
	PaymentIntent createPaymentIntent(PaymentInfoDto paymentInfoDto)throws StripeException;

}
