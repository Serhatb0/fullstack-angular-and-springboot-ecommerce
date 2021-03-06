package com.biricik.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.biricik.ecommerce.dao.CustomerRepository;
import com.biricik.ecommerce.dto.PaymentInfoDto;
import com.biricik.ecommerce.dto.PurchaseDto;
import com.biricik.ecommerce.dto.PurchaseResponse;
import com.biricik.ecommerce.entity.Customer;
import com.biricik.ecommerce.entity.Order;
import com.biricik.ecommerce.entity.OrderItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class CheckoutServiceImpl  implements CheckoutService{
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public CheckoutServiceImpl(CustomerRepository customerRepository,
			@Value("${stripe.key.secret}") String secretKey) {
		this.customerRepository = customerRepository;
		
		// initialize Stripe API with secret key 
		Stripe.apiKey = secretKey;
	}

	@Override
	@Transactional
	public PurchaseResponse placeOrder(PurchaseDto purchaseDto) {
		
		// retrieve the order info from dto
		Order order = purchaseDto.getOrder();
		
		// generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);
		
		// populate order with orderItems
		Set<OrderItem> orderItems = purchaseDto.getOrderItems();
		orderItems.forEach(item -> order.add(item));
		// populate order with billingAddress and shippingAddress
		order.setBillingAddress(purchaseDto.getBillingAddress());
		order.setShippingAddress(purchaseDto.getShippingAddress());
		// populate customer with order 
		Customer customer = purchaseDto.getCustomer();
		
		// check if this is an existing customer
		String theEmail = customer.getEmail();
		
		Customer customerFromDb = customerRepository.findByEmail(theEmail);
		
		if(customerFromDb != null) {
			// we found them .. let's assign them accordingly
			customer = customerFromDb;
		}
		
		customer.add(order);
		// save to the database
		customerRepository.save(customer);
		// return a response
		return new PurchaseResponse(order.getOrderTrackingNumber());
		
	}

	private String generateOrderTrackingNumber() {
		// generate a random UUID number (UUID version-4)
		return UUID.randomUUID().toString();
	}

	@Override
	public PaymentIntent createPaymentIntent(PaymentInfoDto paymentInfoDto) throws StripeException {
		List<String> paymentMethodTypes = new ArrayList<>();
		paymentMethodTypes.add("card");
		
		Map<String, Object> params = new HashMap<>();
		params.put("amount", paymentInfoDto.getAmount());
		params.put("currency", paymentInfoDto.getCurrency());
		params.put("payment_method_types", paymentMethodTypes);
		params.put("description","Birick Ecommerce purchase");
		params.put("receipt_email",paymentInfoDto.getReceiptEmail());
		
		return PaymentIntent.create(params);
	}

}
