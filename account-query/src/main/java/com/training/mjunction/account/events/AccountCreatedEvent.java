package com.training.mjunction.account.events;

import java.math.BigDecimal;

import com.training.mjunction.account.base.BaseEvent;

import lombok.Getter;

@Getter
public class AccountCreatedEvent extends BaseEvent<String> {

	private final String accHolder;
	private final String accHolderName;
	private final BigDecimal balance;

	public AccountCreatedEvent(final String id, final String accHolder, final String accHolderName,
			final BigDecimal balance) {
		super(id);
		this.accHolder = accHolder;
		this.balance = balance;
		this.accHolderName = accHolderName;

	}

}
