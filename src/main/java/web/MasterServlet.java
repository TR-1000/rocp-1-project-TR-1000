package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


import controllers.UserController;
import doa.AccountDAO;
import doa.AccountDAOImpl;
import models.Account;
import models.Admin;
import models.Deposit;
import models.Employee;
import models.Transfer;
import models.User;
import models.Withdrawal;



public class MasterServlet extends HttpServlet {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserController userController = new UserController();
	private static final AccountDAO accountDAO = new AccountDAOImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("doGet");
		
		res.setContentType("application/json");
		
		// this will set the default response to not found;
		// the request was successful
		res.setStatus(404);
		
		final String URI = req.getRequestURI().replace("/rocp-project/", "");

		String[] portions = URI.split("/");

		System.out.println(Arrays.toString(portions));

		try {
			System.out.println("try");
			switch (portions[0]) {
			
			
			///////////////// USERS /////////////////
			case "users":
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					User user = userController.findCustomerByID(id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(user);
					res.getWriter().println(json);
				} else {
					if (req.getMethod().equals("POST")) {
						System.out.println("POST method");
						BufferedReader reader = req.getReader();

						StringBuilder string = new StringBuilder();

						String line = reader.readLine();

						while (line != null) {
							string.append(line);
							line = reader.readLine();
						}

						String body = new String(string);

						System.out.println(body);
						
						User user = om.readValue(body, User.class);
						
						System.out.println(user);

						if (userController.insert(user)) {
							res.setStatus(201);
							res.getWriter().println("User was created");
						}
					
						
					} else {

						Set<User> all = userController.findAllCustomers();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
					}
						
				}
				break;
				
				
				
            ///////////////// UPDATE USERS /////////////////
			case "UpdateUser":
				if (req.getMethod().equals("POST")) {
				System.out.println("Updating user");
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					User foundUser = userController.findCustomerByID(id);
					
					BufferedReader reader = req.getReader();

					StringBuilder string = new StringBuilder();

					String line = reader.readLine();

					while (line != null) {
						string.append(line);
						line = reader.readLine();
					}

					String body = new String(string);

					System.out.println(body);
					
					User user = om.readValue(body, User.class);
					
					System.out.println(user);
					
					if(user != null) {
						foundUser.setFirstName(user.getFirstName());
						foundUser.setLastName(user.getLastName());
						foundUser.setPassword(user.getPassword());
						foundUser.setEmail(user.getEmail());
						foundUser.setPhoneNumber(user.getPhoneNumber());
						foundUser.setId(id);

						System.out.println("before " + foundUser);
						
						userController.update(foundUser);
						
						System.out.println("after " + foundUser);
						
						foundUser = userController.findCustomerByID(id);
					
						System.out.println(body);
						
					}
					res.setStatus(200);
					String json = om.writeValueAsString(foundUser);
					res.getWriter().println(json);
						
				}
			}	
				break;
			
			
			///////////////// EMPLOYEES /////////////////
			case "employees":
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					Set<Employee> emp = userController.findEmployeeByID(id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(emp);
					res.getWriter().println(json);
				} else {
					Set<User> all = userController.findAllEmployees();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				break;	
				
				
				
			///////////////// ADMIN /////////////////
			case "admin":
				Set<Admin> all = userController.findAllAdmins();
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(all));
				break;
				
				
				
			///////////////// ACCOUNTS /////////////////
			case "accounts":
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					Account account = accountDAO.getAccountById(id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(account);
					res.getWriter().println(json);
				} else {
					if (req.getMethod().equals("POST")) {
						System.out.println("POST Method");
						BufferedReader reader = req.getReader();

						StringBuilder string = new StringBuilder();

						String line = reader.readLine();

						while (line != null) {
							string.append(line);
							line = reader.readLine();
						}

						String body = new String(string);

						System.out.println(body);
						
						Account account = om.readValue(body, Account.class);
						
						System.out.println(account);
						
						int customer_id = (int) account.getUserIDNumber();
						
						User foundUser = userController.findCustomerByID(customer_id);
						// If customer is not found in the database
						if(foundUser == null) {
							res.setStatus(404);
							res.getWriter().println("customer ID number " + customer_id + " doesn't exist");
						
						// If customer is found in the database
						} else {
							if (accountDAO.openAccount(account)) {
								res.setStatus(201);
								//res.getWriter().println("Account was created");
								
								String json = om.writeValueAsString(accountDAO.getsAccountsByUserID((int) account.getUserIDNumber()));
								res.getWriter().println(json);
								
								
							}
						
						}
						
					
					} else {
						Set<Account> allAccounts = accountDAO.selectAllAccounts();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(allAccounts));
					}
					
				}
					break;
					
					
					
			///////////////// UPDATE ACCOUNTS /////////////////
			case "account_status_update" :
				if (req.getMethod().equals("POST")) {
					if (portions.length == 2) {
						System.out.println("Updating account status");
						int id = Integer.parseInt(portions[1]);
						Account foundAccount = accountDAO.getAccountById(id);
						
						BufferedReader reader = req.getReader();

						StringBuilder string = new StringBuilder();

						String line = reader.readLine();

						while (line != null) {
							string.append(line);
							line = reader.readLine();
						}

						String body = new String(string);

						System.out.println(body);
						
						Account account = om.readValue(body, Account.class);
						
						System.out.println(account);
						
						if(account != null) {
							foundAccount.setStatus(account.getStatus());
							foundAccount.setAccountNumber(id);

							System.out.println("before " + foundAccount);
							
							accountDAO.updateAccountStatus(foundAccount);
							
							System.out.println("after " + foundAccount);
							
							accountDAO.updateAccountStatus(foundAccount);
							
							foundAccount = accountDAO.getAccountById(id);
						
							System.out.println(body);
							
						}
						res.setStatus(200);
						String json = om.writeValueAsString(foundAccount);
						res.getWriter().println(json);
							
					}
				}	
					break;
					
			case("deposit"):
				if (req.getMethod().equals("POST")) {
					
					BufferedReader reader = req.getReader();

					StringBuilder string = new StringBuilder();

					String line = reader.readLine();

					while (line != null) {
						string.append(line);
						line = reader.readLine();
					}

					String body = new String(string);

					System.out.println(body);
					
					Deposit depositObject = om.readValue(body, Deposit.class);
					
					System.out.println(depositObject);
					
					if(depositObject != null) {
						accountDAO.deposit(depositObject.getAccount_number(), depositObject.getAmount());
						System.out.println(body);
						
					}
					res.setStatus(200);
					String json = om.writeValueAsString(accountDAO.getAccountById(depositObject.getAccount_number()));
					res.getWriter().println(json);
				}
				break;
				
			case("withdraw"):
				if (req.getMethod().equals("POST")) {
					
					BufferedReader reader = req.getReader();

					StringBuilder string = new StringBuilder();

					String line = reader.readLine();

					while (line != null) {
						string.append(line);
						line = reader.readLine();
					}

					String body = new String(string);

					System.out.println(body);
					
					Withdrawal withdrawalObject = om.readValue(body, Withdrawal.class);
					
					System.out.println(withdrawalObject);
					
					if(withdrawalObject != null) {
						accountDAO.withdraw(withdrawalObject.getAccount_number(), withdrawalObject.getAmount());
						System.out.println(body);
						
					}
					res.setStatus(200);
					String json = om.writeValueAsString(accountDAO.getAccountById(withdrawalObject.getAccount_number()));
					res.getWriter().println(json);
				}
				break;
				
				
			case("transfer"):
				if (req.getMethod().equals("POST")) {
					
					BufferedReader reader = req.getReader();

					StringBuilder string = new StringBuilder();

					String line = reader.readLine();

					while (line != null) {
						string.append(line);
						line = reader.readLine();
					}

					String body = new String(string);

					System.out.println(body);
					
					Transfer transferObject = om.readValue(body, Transfer.class);
					
					System.out.println(transferObject);
					
					int to = transferObject.getTo_account_number();
					int from = transferObject.getFrom_account_number();
					double amount = transferObject.getAmount();
					
					if(transferObject != null) {
						System.out.println("transfer in progress");
						accountDAO.transfer(to, from, amount);
						System.out.println(body);
						
					}
					res.setStatus(200);
					String json = om.writeValueAsString(accountDAO.getTransferAccounts(to, from));
					res.getWriter().println(json);
				}
				
				
				
				
				
				
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().println("The id you provided is not an integer");
			res.setStatus(400);
		}

	}

		
		



	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
		
//		res.setContentType("application/json");
//		// this will set the default response to not found;
//		// the request was successful
//		res.setStatus(404);
//				
//		final String URI = req.getRequestURI().replace("/rocp-project/", "");
//
//		String[] portions = URI.split("/");
//
//		System.out.println(Arrays.toString(portions));
//		
//		try {
//			switch (portions[0]) {
			
//				
//			case "employees":
//				if (portions.length == 2) {
//					int user_id = Integer.parseInt(portions[1]);
//					Set<Employee> emp = userController.findEmployeeByID(user_id);
//					res.setStatus(200);
//					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
//					String json = om.writeValueAsString(emp);
//					res.getWriter().println(json);
//				} else {
//					
//					if (req.getMethod().equals("POST")) {
//						
//						BufferedReader reader = req.getReader();
//
//						StringBuilder string = new StringBuilder();
//
//						String line = reader.readLine();
//
//						while (line != null) {
//							string.append(line);
//							line = reader.readLine();
//						}
//
//						String body = new String(string);
//
//						System.out.println(body);
//						
//						User user = om.readValue(body, User.class);
//						
//						System.out.println(user);
//
//						
//					} else {
//
//						Set<User> all = userController.findAllEmployees();
//						res.setStatus(200);
//						res.getWriter().println(om.writeValueAsString(all));
//					}
//						
//				}
//			break;	
//				
//			case "admin":
//				Set<Admin> all = userController.findAllAdmins();
//				res.setStatus(200);
//				res.getWriter().println(om.writeValueAsString(all));
//				
//			}
//			
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			res.getWriter().println("The id you provided is not an integer");
//			res.setStatus(400);
//		}
				
	}
	
}
		
		
		
		
