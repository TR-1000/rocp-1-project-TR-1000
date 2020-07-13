package models;

public class Bank {

	public static void main(String[] args) {
		
		User user1 = new User(
				1,
				"john",
				"deere",
				"j_deere",
				"password",
				"j_deere@johndeere.com",
				"210-222-2222",
				"215 Deere rd. Grand Detour, IL 98765");
		user1.createAccount(25);
		user1.createAccount(34);
		user1.accounts.get(0).getUserIDNumber();
		
		
	}
	

} 