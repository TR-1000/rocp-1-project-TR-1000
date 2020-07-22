package doa;

import java.util.*;

import models.Admin;
import models.Employee;
import models.User;

public interface UserDAO {
	
	public boolean insert(User user);
	public boolean update(User user);
	public Set<User> findAllCustomers();
	public Set<User> findAllEmployees();
	public Set<Admin> findAllAdmins();
	public User findCustomerByID(int id);
	public Set<Employee> findEmployeeByID(int id);
	public Set<User> findCustomerByName(String firstName, String lastName);
	public User login(String username, String password);
	
	

}
