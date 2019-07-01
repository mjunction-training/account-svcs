package com.training.mjunction.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account implements Serializable {
	private static final long serialVersionUID = -683252464592318120L;

	@Id
	private String accountNo;
	private BigDecimal balance;
	private String accHolder;
	private String accHolderName;
	private String lastUpdated;

}
