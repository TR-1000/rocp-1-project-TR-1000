package models;

import doa.AccountDAO;
import doa.AccountDAOImpl;

public class Bank {
	
	


	public static void main(String[] args) {
		
		Admin admin1 = new Admin();
		Employee emp = new Employee();
		
		System.out.println(admin1.getRole());
		System.out.println(emp.getRole());
		
		AccountDAO dao = new AccountDAOImpl();
		
		Standard user1 = new Standard(
				10,
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
		
		dao.insert(user1.accounts.get(0));
		System.out.println(user1.getRole());
		
		
	}
	

} 