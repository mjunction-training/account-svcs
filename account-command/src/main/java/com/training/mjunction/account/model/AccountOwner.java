package com.training.mjunction.account.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name" })
public class AccountOwner implements Serializable {
	private static final long serialVersionUID = -5357787298063565273L;
	@JsonProperty("name")
	private String name;
}
