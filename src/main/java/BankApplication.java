import doa.AccountDAO;
import doa.AccountDAOImpl;
import doa.UserDAO;
import doa.UserDAOImpl;
import models.Admin;
import models.Employee;
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
				
				"john",
				"deere",
				"jj_deere",
				"password",
				"j_deere@johndeeree.com",
				"210-222-2221"
			);
		
		
		String user1Role = user1.getRole();
//		String empRole = emp.getRole();
//		String adminRole = admin1.getRole();
		
//		System.out.println(empRole.equals("employee"));
//		System.out.println(adminRole.equals("employee"));
//		System.out.println(user1Role.equals("standard"));
		
//		userDAO.insert(user1);
		userDAO.insert(emp);
		userDAO.insert(admin1);
//		
//		
//		
//		
//		
//		user1.createAccount(25);
//		admin1.createAccount(34);
//		user1.createAccount(78);
//		emp.createAccount(90);
//		
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