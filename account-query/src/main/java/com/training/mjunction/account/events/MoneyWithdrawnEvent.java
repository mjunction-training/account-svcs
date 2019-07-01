package com.training.mjunction.account.events;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.base.BaseEvent;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "amount" })
public class MoneyWithdrawnEvent extends BaseEvent<String> {

	@JsonProperty("amount")
	private final BigDecimal amount;

	public MoneyWithdrawnEvent(@JsonProperty("id") final String id, @JsonProperty("amount") final BigDecimal amount) {
		super(id);
		this.amount = amount;
	}

}