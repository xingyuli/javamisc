package org.swordess.javamisc.junit.chapter7;

import java.util.HashMap;
import java.util.Map;

import org.swordess.javamisc.junit.chapter7.Account;
import org.swordess.javamisc.junit.chapter7.AccountManager;

public class MockAccountManager implements AccountManager {

	private Map<String, Account> accounts = new HashMap<>();
	
	public void addAccount(String userId, Account account) {
		accounts.put(userId, account);
	}
	
	@Override
	public Account findAccountForUser(String userId) {
		return accounts.get(userId);
	}

	@Override
	public void updateAccount(Account account) {
		// do nothing
	}
	
}
