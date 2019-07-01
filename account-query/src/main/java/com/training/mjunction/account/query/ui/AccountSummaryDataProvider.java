package com.training.mjunction.account.query.ui;

import org.axonframework.queryhandling.QueryGateway;

import com.training.mjunction.account.domain.Account;
import com.training.mjunction.account.query.handler.CountAccountSummariesQuery;
import com.training.mjunction.account.query.handler.FindAccountSummariesQuery;
import com.training.mjunction.account.query.response.CountAccountSummariesResponse;
import com.training.mjunction.account.query.response.FindAccountSummariesResponse;
import com.vaadin.data.provider.CallbackDataProvider;

public class AccountSummaryDataProvider extends CallbackDataProvider<Account, Void> {
	private static final long serialVersionUID = 1737230807250572798L;

	public AccountSummaryDataProvider(final QueryGateway queryGateway) {
		super(q -> {
			FindAccountSummariesQuery query = new FindAccountSummariesQuery();
			FindAccountSummariesResponse response = queryGateway.query(query, FindAccountSummariesResponse.class)
					.join();
			return response.getAccounts().stream();
		}, q -> {
			CountAccountSummariesQuery query = new CountAccountSummariesQuery();
			CountAccountSummariesResponse response = queryGateway.query(query, CountAccountSummariesResponse.class)
					.join();
			return response.getCount();
		});
	}

}
