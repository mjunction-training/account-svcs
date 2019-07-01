package com.training.mjunction.account.controller;

import java.time.Instant;
import java.util.List;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.mjunction.account.domain.Account;
import com.training.mjunction.account.events.AccountCreatedEvent;
import com.training.mjunction.account.events.MoneyDepositedEvent;
import com.training.mjunction.account.events.MoneyWithdrawnEvent;
import com.training.mjunction.account.repo.AccountRepository;

@RestController
@ProcessingGroup("Accounts")
@RequestMapping("/accounts")
public class AccountQueryController {

	@Autowired
	private AccountRepository accRepo;

	@EventHandler
	public void on(final AccountCreatedEvent event, @Timestamp final Instant instant) {
		final Account account = new Account(event.getId(), event.getBalance(), event.getAccHolder(),
				event.getAccHolderName(), instant.toString());

		accRepo.insert(account);

	}

	@EventHandler
	public void on(final MoneyDepositedEvent event, @Timestamp final Instant instant) {
		final Account account = accRepo.findByAccountNo(event.getId());
		account.setBalance(account.getBalance().add(event.getAmount()));
		account.setLastUpdated(instant.toString());

		accRepo.save(account);

	}

	@EventHandler
	public void on(final MoneyWithdrawnEvent event, @Timestamp final Instant instant) {
		final Account account = accRepo.findByAccountNo(event.getId());
		account.setBalance(account.getBalance().subtract(event.getAmount()));
		account.setLastUpdated(instant.toString());

		accRepo.save(account);

	}

	@GetMapping("/details")
	public List<Account> getAccDetails() {
		return accRepo.findAll();
	}

	@GetMapping("/details/{id}")
	public Account getAccDetails(@PathVariable final String id) {
		return accRepo.findByAccountNo(id);
	}

}
