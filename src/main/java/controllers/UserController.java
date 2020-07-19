package controllers;


import java.util.Set;

import models.Admin;
import models.Employee;
import models.User;
import services.UserService;

public class UserController {
	
	private final UserService userService = new UserService();
	
	public Set<User> findAllCustomers() {
		return userService.findAllCustomers();
	}
	
	public User findCustomerByID(int id) {
		return userService.findCustomerByID(id);
	}
	
	public Set<Employee> findEmployeeByID(int id) {
		return userService.findEmployeeByID(id);
	}
	
	public Set<User> findAllEmployees(){
		return userService.findAllEmployees();
	}
	
	public Set<Admin> findAllAdmins() {
		return userService.findAllAdmins();
	}

	public boolean insert(User user) {
		return userService.insert(user);
	}
	
	public boolean update(User user) {
		return userService.update(user);
	}
	
	

}
