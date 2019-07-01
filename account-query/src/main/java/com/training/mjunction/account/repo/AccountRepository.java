package com.training.mjunction.account.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.training.mjunction.account.domain.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	Account findByAccountNo(String accNo);
}
