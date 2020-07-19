package models;

public class Account {

	private long accountNumber;
	private long userIDNumber;
	private double balance;
	//private AccountStatus status;
	//private AccountType type;
	
	
	
	//Constructors
	
	public Account() {
		
	}
	
	public Account(long accountNumber){
		this.accountNumber = accountNumber;
		System.out.println("Account created! Account number is " + this.accountNumber);
	}
	
	
	//Getters and Setters
	
	public long getUserIDNumber() {
		System.out.println("User ID Number: " + userIDNumber);
		return userIDNumber;
	}
	
	public void setUserIDNumber(long userIDNumber) {
		this.userIDNumber = userIDNumber;
	}
	
	public long getAccountNumber() {
		System.out.println("Account number " + accountNumber + "\n");
		return accountNumber;
	}
	
//	public void setAccountNumber(long accountNumber) {
//		this.accountNumber = accountNumber;
//	}
	
	public double getBalance() {
		System.out.println("Account number " + accountNumber +  ": " +  balance);
		return balance;
	}
	
	public void deposit(double amount) {
		if(amount > 0) {
			this.balance += amount;
			System.out.println(amount + " has been deposited into your account.\nYour total balance is " + balance + ".\n");
		}
		else System.out.println("Please enter a valid amount.\n");
		
	}
	
	public void withdrawal(double amount) {
		if(amount <= this.balance) {
			this.balance -= amount;
			System.out.println(amount + " withdrawn from your account.\nYou have " + balance + " remaining.\n");
		}
		else System.out.println("Insufficient funds! Enter an amount less than or equal to " + balance);
		
	}
	
	public void transfer(Account toAccount, double amount) {
		System.out.println("Current balance: " + balance);
		System.out.println("Attempting transfer of "+ amount);
		if(amount <= balance) {
			this.withdrawal(amount);
			toAccount.deposit(amount);
		}
		else System.out.println("Insufficient funds");
		
	}	
	
	

	
}
