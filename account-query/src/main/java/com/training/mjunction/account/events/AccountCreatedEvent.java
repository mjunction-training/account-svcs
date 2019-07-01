package com.training.mjunction.account.events;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.base.BaseEvent;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "accHolder", "accHolderName", "balance" })
public class AccountCreatedEvent extends BaseEvent<String> {

	@JsonProperty("accHolder")
	private final String accHolder;
	@JsonProperty("accHolderName")
	private final String accHolderName;
	@JsonProperty("balance")
	private final BigDecimal balance;

	@JsonCreator
	public AccountCreatedEvent(@JsonProperty("id") final String id, @JsonProperty("accHolder") final String accHolder,
			@JsonProperty("accHolderName") final String accHolderName,
			@JsonProperty("balance") final BigDecimal balance) {
		super(id);
		this.accHolder = accHolder;
		this.balance = balance;
		this.accHolderName = accHolderName;

	}

}
