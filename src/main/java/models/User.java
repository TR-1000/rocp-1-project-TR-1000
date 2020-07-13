package models;

import java.util.ArrayList;

public class User {
	
	private long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String phoneNumber;
	private String address;
	public ArrayList<Account> accounts = new ArrayList<Account>();
	
	
	
	public User() {
		
	}

	public User(long id, String firstName, String lastName, String userName, String password, String email,
			String phoneNumber, String address) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		
		System.out.println("New user created\nUser ID: " + id + "User Name: " + userName + "\n");
	}

	public Account createAccount(long accountNumber) {
		Account account = new Account(accountNumber);
		account.setUserIDNumber(this.id);
		accounts.add(account);
		return account;
	}
	
	public ArrayList<Account> getAccounts() {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}	
	
       
	
	
	

}
