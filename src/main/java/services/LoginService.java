package services;

import doa.LoginDAO;
import doa.LoginDAOImpl;
import models.User;

public class LoginService {
	
	private static final LoginDAO loginDAO = new LoginDAOImpl();
	
	public User customer_login(String username, String password) {
		System.out.println("LOGIN SERVICE");
		return loginDAO.customer_login(username, password);
		
	}
	
	public User employee_login(String username, String password) {
		return loginDAO.employee_login(username, password);
	}
}
