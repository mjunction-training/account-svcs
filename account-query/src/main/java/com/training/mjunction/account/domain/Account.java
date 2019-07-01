package com.training.mjunction.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "accountNo", "balance", "accHolder", "accHolder", "lastUpdated" })
public class Account implements Serializable {
	private static final long serialVersionUID = -683252464592318120L;

	@Id
	@JsonProperty("accountNo")
	private String accountNo;
	@JsonProperty("balance")
	private BigDecimal balance;
	@JsonProperty("accHolder")
	private String accHolder;
	@JsonProperty("accHolderName")
	private String accHolderName;
	@JsonProperty("lastUpdated")
	private String lastUpdated;

}
