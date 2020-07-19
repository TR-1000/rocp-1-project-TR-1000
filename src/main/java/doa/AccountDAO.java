package doa;

import java.util.*;

import models.Account;

public interface AccountDAO {

	public boolean insert(Account account);
//	public Account findByAccountNumber(long accountNumber);
//	public Account findByUserIDNumber(long userIDNumber);
	public Set<Account> selectAllAccounts();
	
	
	
}
