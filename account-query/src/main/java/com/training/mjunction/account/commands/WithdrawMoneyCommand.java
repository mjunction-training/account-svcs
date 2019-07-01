package com.training.mjunction.account.commands;

import java.math.BigDecimal;

import com.training.mjunction.account.base.BaseCommand;

import lombok.Getter;

@Getter
public class WithdrawMoneyCommand extends BaseCommand<String> {
	private final BigDecimal amount;

	public WithdrawMoneyCommand(final String id, final BigDecimal amount) {
		super(id);
		this.amount = amount;
	}
}