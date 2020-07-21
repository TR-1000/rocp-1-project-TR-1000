import doa.AccountDAO;
import doa.AccountDAOImpl;
import doa.UserDAO;
import doa.UserDAOImpl;
import models.Account;
import models.Admin;
import models.CheckingAccount;
import models.Employee;
import models.SavingsAccount;
import models.User;

public class BankApplication {
	
	
	public static void main(String[] args) {
		
		Admin admin1 = new Admin(
			
				"ajohn",
				"adeere",
				"aj_deere",
				"apassword",
				"aj_deere@johndeere.com",
				"a210-222-2222"
			);
		
		Employee emp = new Employee(
			
				"ejohn",
				"edeere",
				"ej_deere",
				"epassword",
				"ej_deere@johndeere.com",
				"e210-222-2222"
			);
//		
//		System.out.println(admin1.getRole());
//		System.out.println(emp.getRole());
//		
		AccountDAO accountDAO = new AccountDAOImpl();
		UserDAO userDAO = new UserDAOImpl();
		
		User user1 = new User(
				1,
				"john",
				"deere",
				"jj_deere",
				"password",
				"j_deere@johndeeree.com",
				"210-222-2221"
			);
		
		
		long user1id = user1.getId();
		System.out.println(user1id);
//		String empRole = emp.getRole();
//		String adminRole = admin1.getRole();
		
//		System.out.println(empRole.equals("employee"));
//		System.out.println(adminRole.equals("employee"));
//		System.out.println(user1Role.equals("standard"));
		
//		userDAO.insert(user1);
//		userDAO.insert(emp);
//		userDAO.insert(admin1);
//		
//		
//		
//		
//		
		user1.createCheckingAccount();
//		admin1.createAccount(34);
		user1.createSavingsAccount();
//		emp.createAccount(90);
		
		
//		Account ca1 = user1.accounts.get(0);
//		Account sa1 = user1.accounts.get(1);
//		System.out.println(ca1.getType());
//		System.out.println(sa1.getType());
		
//		accountDAO.insert(ca1);
//		accountDAO.insert(sa1);
//	
//		
//		user1.accounts.get(0).deposit(1000.00);
//		user1.accounts.get(1).deposit(20000.00);
//		admin1.accounts.get(0).deposit(9000.00);
//		emp.accounts.get(0).deposit(1000000);
//		
//		emp.accounts.get(0).transfer(user1.accounts.get(0), 1000);
//		emp.accounts.get(0).transfer(user1.accounts.get(1), 1000);
//		emp.accounts.get(0).transfer(admin1.accounts.get(0), 1000);
//		
//		
//		
//		
//		
//		//dao.insert(user1.accounts.get(0));
//		
//		System.out.println(user1.toString());
//		System.out.println(admin1.toString());
//		System.out.println(emp.toString());
		
//		System.out.println(userDAO.findAll());
		
		
	}
	

} 