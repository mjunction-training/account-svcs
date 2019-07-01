package com.training.mjunction.account.events;

import java.math.BigDecimal;

import com.training.mjunction.account.base.BaseEvent;

import lombok.Getter;

@Getter
public class MoneyDepositedEvent extends BaseEvent<String> {
	private final BigDecimal amount;

	public MoneyDepositedEvent(final String id, final BigDecimal amount) {
		super(id);
		this.amount = amount;
	}

}