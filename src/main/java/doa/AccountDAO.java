package doa;

import java.util.*;

import models.Account;

public interface AccountDAO {

	public boolean addAccount(Account account);
//	public Account findByAccountNumber(long accountNumber);
//	public Account findByUserIDNumber(long userIDNumber);
	public Set<Account> selectAllAccounts();
	public Set<Account> getsAccountsByUserID(int id);
	public Account getAccountById(int id);
	public boolean transfer(Account source, Account target, double amount);
	public boolean deposit(Account account, double amount);
	public boolean withdrawl(Account account, double amount);
	
	
	
}
