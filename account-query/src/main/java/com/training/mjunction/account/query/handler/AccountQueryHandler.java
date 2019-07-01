package com.training.mjunction.account.query.handler;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.mjunction.account.query.response.CountAccountSummariesResponse;
import com.training.mjunction.account.query.response.FindAccountSummariesResponse;
import com.training.mjunction.account.repo.AccountRepository;

@Component
public class AccountQueryHandler {

	@Autowired
	private AccountRepository accRepo;

	@QueryHandler
	public FindAccountSummariesResponse handle(final FindAccountSummariesQuery query) {
		final FindAccountSummariesResponse response = new FindAccountSummariesResponse();
		response.setAccounts(accRepo.findAll());
		return response;
	}

	@QueryHandler
	public CountAccountSummariesResponse handle(final CountAccountSummariesQuery query) {
		final CountAccountSummariesResponse response = new CountAccountSummariesResponse();
		response.setCount(accRepo.findAll().size());
		return response;
	}

}
