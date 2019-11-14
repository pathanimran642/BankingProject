package com.userFront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userFront.domain.PrimaryAccount;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.User;
import com.userFront.service.TransactionService;
import com.userFront.service.UserService;

@RestController
@RequestMapping("/transfer")
public class TransferController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private UserService userService;

	@PostMapping(value="/betweenAccounts/{userName}/{transferFrom}/{transferTo}/{amount}/")
	public ResponseEntity<Void> betweenAccounts(@PathVariable("transferFrom") String transferFrom, @PathVariable("transferTo") String transferTo, @PathVariable("amount") String amount,@PathVariable("userName") String name ) throws Exception {
	  User user = userService.findByUsername(name);
	  PrimaryAccount primaryAccount = user.getPrimaryAccount();
    SavingsAccount savingsAccount = user.getSavingsAccount();
    transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}


	
	@PostMapping(value = "/toSomeoneElse/{userName}/{accountType}/{amount}/")
	public ResponseEntity<Void> toSomeoneElse(@PathVariable("userName") String userName,@PathVariable("accountType") String accountType,@PathVariable("amount") String amount) {
	  User user = userService.findByUsername(userName);
    PrimaryAccount primaryAccount = user.getPrimaryAccount();
    SavingsAccount savingsAccount = user.getSavingsAccount();
    transactionService.toSomeoneElseTransfer(accountType, amount,primaryAccount,savingsAccount);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
