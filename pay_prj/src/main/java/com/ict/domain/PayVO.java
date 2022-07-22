package com.ict.domain;

import lombok.Data;

@Data
public class PayVO {
	private String itemName;
	private Long amount;
	private String merchant_uid;
}
