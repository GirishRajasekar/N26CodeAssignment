package com.n26.code.challenge.bean;

import java.util.Date;

public class TransactionVO {
	
	private Double amount = 0.0;
	private Date timestamp ;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
