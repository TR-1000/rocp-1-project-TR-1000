package doa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.User;
import util.ConnectionUtil;

public class LoginDAOImpl implements LoginDAO {
	
	private static final AccountDAO accountDAO = new AccountDAOImpl();
	
	public User customer_login(String username, String password) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM customers WHERE user_name = '"+ username +"' AND user_password = '" + password + "';";
			System.out.println(sql);
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next()) {
				User user = new User();
				user.setId(result.getInt("id"));
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setUserName(result.getString("user_name"));
				user.setPassword(result.getString("user_password"));
				user.setEmail(result.getString("email"));
				user.setPhoneNumber(result.getString("phone"));
				user.setAccounts(accountDAO.getAccountsByUserID(result.getInt("id")));
				
				return user;
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		} 
		return null;
	}
}
