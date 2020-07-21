package doa;

import java.util.*;

import models.Account;
import models.User;

public interface AccountDAO {

	public boolean openAccount(Account account);
//	public Account findByAccountNumber(long accountNumber);
//	public Account findByUserIDNumber(long userIDNumber);
	public Set<Account> selectAllAccounts();
	public Set<Account> getsAccountsByUserID(int id);
	public Account getAccountById(int id);
	public boolean transfer(int toAccountNumber, int fromAccountNumber, double amount);
	public boolean deposit(int accountNumber, double amount);
	public boolean withdraw(int accountNumber, double amount);
	public boolean updateAccountStatus(Account account);
	public Object getTransferAccounts(int to, int from);
	
	
}
