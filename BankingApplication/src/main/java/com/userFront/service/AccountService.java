package com.userFront.service;

import com.userFront.domain.PrimaryAccount;
import com.userFront.domain.SavingsAccount;

public interface AccountService {

	PrimaryAccount createPrimaryAccount();
	SavingsAccount createSavingsAccount();
	
	void deposit(String accountType, double amount, String userName);
	void withdraw(String accountType, double amount, String userName);
}
