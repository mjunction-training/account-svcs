package com.training.mjunction.account.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.base.BaseCommand;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "accountHolder", "accountHolderName" })
public class AccountCreateCommand extends BaseCommand<String> {

	@JsonProperty("accountHolder")
	private final String accountHolder;
	@JsonProperty("accountHolderName")
	private final String accountHolderName;

	@JsonCreator
	public AccountCreateCommand(@JsonProperty("id") final String id, @JsonProperty("accountHolder") final String holder,
			@JsonProperty("accountHolderName") final String accountHolderName) {
		super(id);
		accountHolder = holder;
		this.accountHolderName = accountHolderName;
	}

}
