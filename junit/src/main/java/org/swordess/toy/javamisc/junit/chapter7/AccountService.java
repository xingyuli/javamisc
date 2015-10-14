package org.swordess.toy.javamisc.junit.chapter7;

public class AccountService {

	private AccountManager accountManager;

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	public void transfer(String senderId, String beneficiaryId, long amount) {
		Account sender = accountManager.findAccountForUser(senderId);
		Account beneficiary = accountManager.findAccountForUser(beneficiaryId);
		sender.debit(amount);
		beneficiary.credit(amount);
		accountManager.updateAccount(sender);
		accountManager.updateAccount(beneficiary);
	}

}
