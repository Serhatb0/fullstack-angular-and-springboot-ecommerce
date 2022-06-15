package com.biricik.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentInfoDto {

	private int amount;
	private String currency;
	private String receiptEmail;
}
