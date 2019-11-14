package com.userFront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userFront.domain.PrimaryAccount;
import com.userFront.domain.PrimaryTransaction;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.SavingsTransaction;
import com.userFront.domain.User;
import com.userFront.service.AccountService;
import com.userFront.service.TransactionService;
import com.userFront.service.UserService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	
	
	@GetMapping("/primaryAccount/{userName}")
	public ResponseEntity<PrimaryAccount> getPrimaryAccountDetails(@PathVariable("userName") String name) {
		List<PrimaryTransaction> primaryTransactionList = transactionService
				.findPrimaryTransactionList(name);

		User user = userService.findByUsername(name);
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		 
		primaryAccount.setPrimaryTransactionList(primaryTransactionList);
	
		if(primaryAccount!=null) {
		return new ResponseEntity<PrimaryAccount> (primaryAccount, HttpStatus.OK);
		}
		
		 return new ResponseEntity<PrimaryAccount> (primaryAccount, HttpStatus.NO_CONTENT);
		}
		

	@GetMapping("/savingsAccount/{userName}")
	public ResponseEntity<SavingsAccount> getSavingsAccountDetails(@PathVariable("userName") String name) {
	  List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(name);

    User user = userService.findByUsername(name);
   SavingsAccount savingsAccount = user.getSavingsAccount();
     
    savingsAccount.setSavingsTransactionList(savingsTransactionList);
  
    if(savingsAccount!=null) {
    return new ResponseEntity<SavingsAccount>(savingsAccount, HttpStatus.OK);
    }
     return new ResponseEntity<SavingsAccount>(savingsAccount, HttpStatus.NO_CONTENT);
	}


	@PostMapping(value = "/deposit/{accountType}/{amount}/{userName}")
	public  ResponseEntity<Void> deposit(@PathVariable("accountType") String accountType,@PathVariable("amount") String amount,@PathVariable("userName") String userName) {
		accountService.deposit(accountType, Double.parseDouble(amount),userName);

		return new ResponseEntity<Void>(HttpStatus.OK) ;
	}

	

	@PostMapping(value = "/withdraw/{accountType}/{amount}/{userName}")
	public  ResponseEntity<Void> withdraw(@PathVariable("accountType") String accountType,@PathVariable("amount") String amount,@PathVariable("userName") String userName) {
		accountService.withdraw(accountType, Double.parseDouble(amount), userName);

		return new ResponseEntity<Void>(HttpStatus.OK) ;
	}
}
