package com.training.mjunction.account.commands;

import com.training.mjunction.account.base.BaseCommand;

import lombok.Getter;

@Getter
public class AccountCreateCommand extends BaseCommand<String> {

	private final String accountHolder;
	private final String accountHolderName;

	public AccountCreateCommand(final String id, final String holder, final String accountHolderName) {
		super(id);
		accountHolder = holder;
		this.accountHolderName = accountHolderName;
	}

}
