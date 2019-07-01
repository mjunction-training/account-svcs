package com.training.mjunction.account.commands;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.base.BaseCommand;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "amount" })
public class DepositMoneyCommand extends BaseCommand<String> {

	@JsonProperty("amount")
	private final BigDecimal amount;

	@JsonCreator
	public DepositMoneyCommand(@JsonProperty("id") final String id, @JsonProperty("amount") final BigDecimal amount) {
		super(id);
		this.amount = amount;
	}

}