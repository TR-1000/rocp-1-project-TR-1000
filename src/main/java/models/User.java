package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {
	private enum Role {STANDARD, EMPLOYEE, ADMIN}
	
	private long id;
	private String role = "standard";
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String phoneNumber;
	private Set<Account> accounts = new HashSet<>();
	
	
	
	public User() {}

	public User(long id, String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		
		System.out.println("New user created\nUser ID: " + id + "User Name: " + userName + "\n");
	}
	
	public User(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		
		System.out.println("New user created\nUser ID: " + id + "User Name: " + userName + "\n");
	}
	
	public User(long id, String firstName, String lastName, String role, String userName, String password, String email,
			String phoneNumber) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		
		System.out.println("New user created\nUser ID: " + id + "User Name: " + userName + "\n");
	}

	public CheckingAccount createCheckingAccount() {
		CheckingAccount account = new CheckingAccount();
		account.setUserIDNumber(this.id);
		accounts.add(account);
		System.out.println(accounts);
		return account;
	}
	
	public SavingsAccount createSavingsAccount() {
		SavingsAccount account = new SavingsAccount();
		account.setUserIDNumber(this.id);
		accounts.add(account);
		System.out.println(accounts);
		return account;
	}
	
	public String getRole() {
		return this.role;
	}


	public void setAccounts(Set<Account> set) {
		this.accounts = set;
	}
	
	public Set<Account> getAccounts() {
		return accounts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setRole(String role) {
		this.role = role;
		
	}
	



	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + password + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", accounts=" + accounts + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	

	
	
	
	
	
	
	
	
       
	
	
	

}
