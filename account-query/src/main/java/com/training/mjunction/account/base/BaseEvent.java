package com.training.mjunction.account.base;

import org.springframework.util.Assert;

import lombok.Data;

@Data
public class BaseEvent<T> {

	private T id;

	public BaseEvent(final T id) {
		Assert.notNull(id, "Id must be not null");
		this.id = id;
	}

}
