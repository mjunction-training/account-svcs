package com.training.mjunction.account.base;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id" })
public class BaseCommand<T> {

	@JsonProperty("id")
	@TargetAggregateIdentifier
	private T id;

	@JsonCreator
	public BaseCommand(@JsonProperty("id") final T id) {
		Assert.notNull(id, "Id must be not null");
		this.id = id;
	}

}
