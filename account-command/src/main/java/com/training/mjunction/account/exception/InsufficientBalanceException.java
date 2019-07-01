package com.training.mjunction.account.exception;

public class InsufficientBalanceException extends RuntimeException {
	private static final long serialVersionUID = -7905944771946485097L;

	public InsufficientBalanceException(final String message) {
		super(message);
	}

}
