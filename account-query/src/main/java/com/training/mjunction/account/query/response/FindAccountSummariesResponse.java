package com.training.mjunction.account.query.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.domain.Account;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "accounts" })
public class FindAccountSummariesResponse {
	@JsonProperty("accounts")
	private List<Account> accounts;
}
