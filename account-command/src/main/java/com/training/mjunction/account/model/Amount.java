package com.training.mjunction.account.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Amount implements Serializable {
	private static final long serialVersionUID = -5684258230592753293L;
	private BigDecimal amount;
}
