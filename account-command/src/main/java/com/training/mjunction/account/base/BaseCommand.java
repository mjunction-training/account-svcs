package com.training.mjunction.account.base;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.springframework.util.Assert;

import lombok.Data;

@Data
public class BaseCommand<T> {

	@TargetAggregateIdentifier
	private T id;

	public BaseCommand(final T id) {
		Assert.notNull(id, "Id must be not null");
		this.id = id;
	}

}
