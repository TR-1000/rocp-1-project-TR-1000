package doa;

import java.sql.*;
import java.util.Set;

import models.Account;
import util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	public boolean insert(Account account) {
		System.out.println("In Inserting Account");
		try(Connection conn = ConnectionUtil.getConnection()){
			int index=0;
			String sql = "INSERT INTO accounts(customer_id) " + "VALUES(?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(++index, account.getUserIDNumber());
			
			System.out.println("Adding account to database");
			
			if(statement.execute()) {
				return true;
			}
	
		}catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}
	
	

	@Override
	public Set<Account> selectAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}


}
