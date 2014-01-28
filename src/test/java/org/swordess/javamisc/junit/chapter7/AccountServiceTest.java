package org.swordess.javamisc.junit.chapter7;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.swordess.javamisc.junit.chapter7.Account;
import org.swordess.javamisc.junit.chapter7.AccountService;

public class AccountServiceTest {

	@Test
	public void testTransferOk() {
		// 1. preparation
		Account senderAccount = new Account("1", 200);
		Account benificiaryAccount = new Account("2", 100);
		MockAccountManager manager = new MockAccountManager();
		manager.addAccount("1", senderAccount);
		manager.addAccount("2", benificiaryAccount);
		AccountService service = new AccountService();
		service.setAccountManager(manager);
		
		// 2. execute
		service.transfer("1", "2", 50);
		
		// 3. verify
		assertEquals(150, senderAccount.getBalance());
		assertEquals(150, benificiaryAccount.getBalance());
	}
	
}
