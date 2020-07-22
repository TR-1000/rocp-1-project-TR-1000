package services;

import java.util.List;
import java.util.Set;

import doa.UserDAO;
import doa.UserDAOImpl;
import models.Admin;
import models.Employee;
import models.User;

public class UserService {

	private final UserDAO userDAO = new UserDAOImpl();
	
	public Set<User> findAllCustomers() {
		return userDAO.findAllCustomers();
	}
	
	public User findCustomerByID(int id) {
		return userDAO.findCustomerByID(id);
	}
	
	public Set<Employee> findEmployeeByID(int id) {
		return userDAO.findEmployeeByID(id);
	}
	
	public Set<User> findAllEmployees() {
		return userDAO.findAllEmployees();
	}
	
	public Set<Admin> findAllAdmins() {
		return userDAO.findAllAdmins();
	}

	public boolean insert(User user) {
		return userDAO.insert(user);
		
	}
	
	public boolean update(User user) {
		return userDAO.update(user);
	}
	
//	public User login(String username, String password) {
//		return userDAO.login(username, password);
//	}


	
	
	
	
}
