package org.swordess.javamisc.junit.chapter7;

public class Account {

	private String accountId;
	private long balance;

	public Account(String accountId, long balance) {
		this.accountId = accountId;
		this.balance = balance;
	}

	public void debit(long amount) {
		balance -= amount;
	}
	
	public void credit(long amount) {
		balance += amount;
	}
	
	public long getBalance() {
		return balance;
	}

}
