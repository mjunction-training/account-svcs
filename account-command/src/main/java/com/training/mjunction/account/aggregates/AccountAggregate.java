package com.training.mjunction.account.aggregates;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.training.mjunction.account.commands.AccountCreateCommand;
import com.training.mjunction.account.commands.DepositMoneyCommand;
import com.training.mjunction.account.commands.WithdrawMoneyCommand;
import com.training.mjunction.account.events.AccountCreatedEvent;
import com.training.mjunction.account.events.MoneyDepositedEvent;
import com.training.mjunction.account.events.MoneyWithdrawnEvent;
import com.training.mjunction.account.exception.InsufficientBalanceException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Aggregate
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "balance", "accountHolder", "accHolderName" })
public class AccountAggregate {

	@JsonProperty("id")
	@AggregateIdentifier
	private String id;
	@JsonProperty("balance")
	private BigDecimal balance;
	@JsonProperty("accountHolder")
	private String accountHolder;
	@JsonProperty("accHolderName")
	private String accHolderName;

	@CommandHandler
	public AccountAggregate(final AccountCreateCommand command) {

		Assert.hasLength(command.getAccountHolder(), "Account holder must have a name");
		Assert.hasLength(command.getId(), "Account id must have length greater than Zero");

		log.info(() -> "CommandHandler:: creating account" + command.getId());

		apply(new AccountCreatedEvent(command.getId(), command.getAccountHolder(), command.getAccountHolderName(),
				new BigDecimal("0")));
	}

	@EventSourcingHandler
	public void handle(final AccountCreatedEvent event) {

		log.info(() -> "EventSourcingHandler:: creating account" + event.getId());

		id = event.getId();

		accountHolder = event.getAccHolder();
		accHolderName = event.getAccHolderName();

		balance = event.getBalance();
	}

	@CommandHandler
	public void on(final DepositMoneyCommand command) {

		log.info(() -> "CommandHandler:: deposit  received " + command.getId());

		Assert.isTrue(command.getAmount().compareTo(BigDecimal.ZERO) > 0, "Amount should be a positive number");

		apply(new MoneyDepositedEvent(command.getId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void handle(final MoneyDepositedEvent event) {

		log.info(() -> "EventSourcingHandler:: balance: " + balance.toString());

		balance = balance.add(event.getAmount());

	}

	@CommandHandler
	public void on(final WithdrawMoneyCommand command) {

		log.info(() -> "CommandHandler:: withdraw  received " + command.getId());

		Assert.isTrue(command.getAmount().compareTo(BigDecimal.ZERO) > 0, "Amount should be a positive number");

		if (command.getAmount().compareTo(balance) > 0) {
			throw new InsufficientBalanceException("Insufficient balance. Trying to withdraw:" + command.getAmount()
					+ ", but current balance is: " + balance);
		}

		apply(new MoneyWithdrawnEvent(command.getId(), command.getAmount()));
	}

	@EventSourcingHandler
	public void handle(final MoneyWithdrawnEvent event) {

		log.info(() -> "EventSourcingHandler:: withdraw  received " + event.getId());

		balance = balance.subtract(event.getAmount());
	}

}
