package com.training.mjunction.account.query.response;

import java.util.List;

import com.training.mjunction.account.domain.Account;

import lombok.Data;

@Data
public class FindAccountSummariesResponse {
	private List<Account> accounts;
}
