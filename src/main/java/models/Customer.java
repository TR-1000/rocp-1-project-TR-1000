package models;

import java.util.HashSet;
import java.util.Set;

public class Customer extends User {

	private Set<Account> accounts = new HashSet<>();

    public Customer() {
        super();

    }

    public Customer(long id, String firstName, String lastName, String userName, String password, String email,
				 String phoneNumber) {
        super(id, firstName, lastName, userName, password, email, phoneNumber);

    }
    
    public Customer(String firstName, String lastName, String userName, String password, String email,
			 String phoneNumber) {
    	super(firstName, lastName, userName, password, email, phoneNumber);

    }
    
    public Customer(long id, String firstName, String lastName, Role role, String userName, String password, String email,
			String phoneNumber) {
		
			super(firstName, lastName, userName, password, email, phoneNumber);
			
	}
    
    
	public CheckingAccount createCheckingAccount() {
		CheckingAccount account = new CheckingAccount();
		account.setUserIDNumber(this.getId());
		accounts.add(account);
		System.out.println(accounts);
		return account;
	}
	
	public SavingsAccount createSavingsAccount() {
		SavingsAccount account = new SavingsAccount();
		account.setUserIDNumber(this.getId());
		accounts.add(account);
		System.out.println(accounts);
		return account;
	}
	
	
	public void setAccounts(Set<Account> set) {
		accounts = set;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}

 

}
