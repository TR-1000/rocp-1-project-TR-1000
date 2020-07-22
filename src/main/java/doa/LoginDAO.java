package doa;

import models.User;

public interface LoginDAO {
	
	public User customer_login(String username, String password);
	public User employee_login(String username, String password);

}
