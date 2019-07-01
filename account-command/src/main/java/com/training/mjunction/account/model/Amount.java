package com.training.mjunction.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "amount" })
public class Amount implements Serializable {
	private static final long serialVersionUID = -5684258230592753293L;
	@JsonProperty("amount")
	private BigDecimal amount;
}
