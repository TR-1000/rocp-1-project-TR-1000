package doa;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;



import models.Account;
import models.User;
import util.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {

	public boolean addAccount(Account account) {
		System.out.println("In Inserting Account");
		try(Connection conn = ConnectionUtil.getConnection()){
			int index=0;
			String sql = "INSERT INTO accounts(customer_id,type) " + "VALUES(?,?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(++index, account.getUserIDNumber());
			statement.setString(++index, account.getType());
			
			System.out.println("Adding account to database");
			
			statement.execute();
			return true;
			
	
		}catch (SQLException e) {
			System.out.println(e);
		}
		return false;
	}
	
	
	
	

	@Override
	public Set<Account> selectAllAccounts() {
		System.out.println("Finding all accounts");
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts;";
			
			Statement statement = conn.createStatement();
			
			Set<Account> accountSet = new HashSet<>();
			
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				accountSet.add(new Account(
						result.getLong("account_number"),
						result.getLong("customer_id"),
						result.getDouble("balance"),
						result.getString("type"),
						result.getString("status")
						)
					);	
			}
			
			return accountSet;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}



	@Override
	public Account getAccountById(int id) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_number = " + id + ";";
			
			Statement statement = conn.createStatement();
			
			ResultSet result = statement.executeQuery(sql);
			
			if (result.next()) {
				Account account = new Account();
				account.setAccountNumber(result.getLong("account_number"));
				account.setUserIDNumber(result.getInt("customer_id"));
				account.setBalance(result.getDouble("balance"));
				account.setType(result.getString("type"));
				account.setStatus(result.getString("status"));
				
				return account;
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		} 
		return null;
	}






	@Override
	public Set<Account> getsAccountsByUserID(int id) {
		System.out.println("Finding all accounts");
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM accounts WHERE customer_id = " + id + ";";
			
			Statement statement = conn.createStatement();
			
			Set<Account> accountSet = new HashSet<>();
			
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				accountSet.add(new Account(
						result.getLong("customer_id"),
						result.getDouble("balance"),
						result.getString("type"))
					);	
			}
			
			return accountSet;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}





	@Override
	public boolean transfer(Account source, Account target, double amount) {
		// TODO Auto-generated method stub
		return false;
	}





	@Override
	public boolean deposit(Account account, double amount) {
		// TODO Auto-generated method stub
		return false;
	}





	@Override
	public boolean withdrawl(Account account, double amount) {
		// TODO Auto-generated method stub
		return false;
	}


}
