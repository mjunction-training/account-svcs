package com.training.mjunction.account.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.training.mjunction.account.commands.AccountCreateCommand;
import com.training.mjunction.account.commands.DepositMoneyCommand;
import com.training.mjunction.account.commands.WithdrawMoneyCommand;
import com.training.mjunction.account.exception.InsufficientBalanceException;
import com.training.mjunction.account.model.AccountOwner;
import com.training.mjunction.account.model.Amount;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private EventStore eventStore;

	@GetMapping("/events/{id}")
	public List<Object> getEvents(@PathVariable final String id) {
		return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
	}

	@PostMapping
	public CompletableFuture<String> createAccount(@RequestBody final AccountOwner user) {
		return commandGateway
				.send(new AccountCreateCommand(UUID.randomUUID().toString(), user.getName(), user.getName()));
	}

	@PutMapping(path = "/deposit/{accountId}")
	public CompletableFuture<String> deposit(@RequestBody final Amount ammount, @PathVariable final String accountId) {

		return commandGateway.send(new DepositMoneyCommand(accountId, ammount.getAmount()));

	}

	@PutMapping(path = "/withdraw/{accountId}")
	public CompletableFuture<String> withdraw(@RequestBody final Amount ammount, @PathVariable final String accountId) {

		return commandGateway.send(new WithdrawMoneyCommand(accountId, ammount.getAmount()));

	}

//	@DeleteMapping("{id}")
//	public CompletableFuture<String> delete(@PathVariable String id) {
//		return commandGateway.send(new CloseAccountCommand(id));
//	}

	@ExceptionHandler(AggregateNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String insufficientBalance(final InsufficientBalanceException exception) {
		return exception.getMessage();
	}

}
