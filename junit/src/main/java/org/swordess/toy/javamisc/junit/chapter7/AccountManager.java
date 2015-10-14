package org.swordess.toy.javamisc.junit.chapter7;

public interface AccountManager {

	public Account findAccountForUser(String userId);
	
	public void updateAccount(Account account);
	
}
