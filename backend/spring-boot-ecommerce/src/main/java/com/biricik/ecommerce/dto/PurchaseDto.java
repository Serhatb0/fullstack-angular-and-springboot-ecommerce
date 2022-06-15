package com.biricik.ecommerce.dto;

import java.util.Set;

import com.biricik.ecommerce.entity.Address;
import com.biricik.ecommerce.entity.Customer;
import com.biricik.ecommerce.entity.Order;
import com.biricik.ecommerce.entity.OrderItem;

import lombok.Data;

@Data
public class PurchaseDto {

	private Customer customer;
	private Address shippingAddress;
	private Address billingAddress;
	private Order order;
	private Set<OrderItem> orderItems;
}
	