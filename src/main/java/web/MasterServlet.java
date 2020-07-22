package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.LoginController;
import controllers.UserController;
import doa.AccountDAO;
import doa.AccountDAOImpl;
import models.Account;
import models.Admin;
import models.Deposit;
import models.Employee;
import models.Login;
import models.Transfer;
import models.User;
import models.Withdrawal;


import java.util.Date;
import java.util.concurrent.TimeUnit;


public class MasterServlet extends HttpServlet {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserController userController = new UserController();
	private static final LoginController loginController = new LoginController();
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
			HttpSession session = req.getSession(false);
			switch (portions[0]) {
			
			
			
			
			///////////////// LOGIN LOGOUT /////////////////
			case "customer_login":
			
				loginController.customer_login(req, res);
				
				break;
				
			case "logout":
				
				if (session != null && ((Boolean) session.getAttribute("loggedin"))) {
					
					loginController.logout(req, res);
					
				} else {
					
					res.getWriter().println("Can't log out if you were never logged in");
				}
				
				break;
				
				
				
			///////////////// REGISTER /////////////////	
			case "register":
				
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
				
					User user = om.readValue(body, User.class);
				
					System.out.println(user);

					if (userController.insert(user)) {
						res.setStatus(201);
						res.getWriter().println("User was created");
						
					} else {
						
						res.getWriter().println("Mistakes were made");
						
					}
			
				
			} else {
				
				res.getWriter().println("Something went wrong");

//				Set<User> all = userController.findAllCustomers();
//				res.setStatus(200);
//				res.getWriter().println(om.writeValueAsString(all));
			}
			
			break;
				
				
				
				
			///////////////// USERS /////////////////
			case "customer":
				// NEEDS TO BE GET REQUEST
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					//HttpSession session = req.getSession(false);
					if (session != null && ((Boolean) session.getAttribute("loggedin")) && session.getAttribute("user_id").equals(id) || session.getAttribute("role").equals("admin")) {
						System.out.println("user role: " + session.getAttribute("role"));
						System.out.println("user id: " + session.getAttribute("user_id"));
						System.out.println("user id: " + session.getAttribute("loggedin"));
						
						User user = userController.findCustomerByID(id);
						res.setStatus(200);
						// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
						String json = om.writeValueAsString(user);
						res.getWriter().println(json);
							
					} else {
						
						res.setStatus(401);
						res.getWriter().println("Denied!");
					}
					
				} else {
					
					Set<User> all = userController.findAllCustomers();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
					
				}
				
				break;
				
				
				
				
            ///////////////// UPDATE USERS /////////////////
			case "customer_update":
				if (req.getMethod().equals("POST")) {
					
				System.out.println("Updating user");
				
				if (portions.length == 2) {
					
					int id = Integer.parseInt(portions[1]);
					
					if (session != null && ((Boolean) session.getAttribute("loggedin")) && session.getAttribute("user_id").equals(id) || session.getAttribute("role").equals("admin")) {
						
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
							
					} else {
						
						res.setStatus(401);
						res.getWriter().println("Denied!");
						
					}
				}
					
				} else {
					
					res.getWriter().println("Mistakes were made");
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
			case "account":
				
				if (portions.length == 2) {
					
					int id = Integer.parseInt(portions[1]);
					
					if (session != null && ((Boolean) session.getAttribute("loggedin")) && session.getAttribute("user_id").equals(id) || session.getAttribute("role").equals("admin")) {
						
						Account account = accountDAO.getAccountById(id);
						
						res.setStatus(200);
						
						String json = om.writeValueAsString(account);
						
						res.getWriter().println(json);
					
					} else {
						
						res.setStatus(401);
						res.getWriter().println("Denied");
					}
					
					
					
				} else {
			
					
				}
				
				break;
				
				
				
				
				
			///////////////// NEW ACCOUNT /////////////////
			case "account_new":
				
				if (session != null && ((Boolean) session.getAttribute("loggedin")) || session.getAttribute("role").equals("admin")) {
					
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
						
						Account new_account = om.readValue(body, Account.class);
						
						int customer_id = (int) session.getAttribute("user_id");
						
						new_account.setUserIDNumber(customer_id);
						
						if (accountDAO.openAccount(new_account)) {
							System.out.println("asdasdasdads");
							res.setStatus(201);
							//res.getWriter().println("Account was created");
							
							String json = om.writeValueAsString(userController.findCustomerByID(customer_id));
							res.getWriter().println(json);
							
						} else {
							
							res.getWriter().println("Mistakes were made");
							
						}
						
//						int customer_id = (int) new_account.getUserIDNumber();
//						System.out.println("asdasdasdads");
//						
//						if(session.getAttribute("user_id").equals(customer_id)) {
//							
//							System.out.println("asdasdasdads");
//							User foundUser = userController.findCustomerByID(customer_id);
//							// If customer is not found in the database
//							if(foundUser == null) {
//								res.setStatus(404);
//								res.getWriter().println("customer ID number " + customer_id + " doesn't exist");
//							
//							// If customer is found in the database
//							} else {
//								System.out.println("asdasdasdads");
//								if (accountDAO.openAccount(((long)customer_id), new_account.getType())) {
//									System.out.println("asdasdasdads");
//									res.setStatus(201);
//									//res.getWriter().println("Account was created");
//									
//									String json = om.writeValueAsString(accountDAO.getsAccountsByUserID((int) new_account.getUserIDNumber()));
//									res.getWriter().println(json);
//									
//								} else {
//									
//									res.getWriter().println("Mistakes were made");
//									
//								}
//							
//							}
//							
//						} else {
//							res.getWriter().println("Mistakes were made");
//						}
						
					} else {
						
						res.getWriter().println("Something went wrong");
//						Set<Account> allAccounts = accountDAO.selectAllAccounts();
//						System.out.println("fetching all accounts");
//						res.setStatus(200);
//						res.getWriter().println(om.writeValueAsString(allAccounts));
						
					}
				}
				
				
				
				
					
					
			///////////////// UPDATE ACCOUNTS /////////////////
			case "account_status_update" :
				
				if (session.getAttribute("role").equals("admin")) {
					
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
				} else {
					res.setStatus(401);
					res.getWriter().println("Access DENIED!");
				}
					break;
					
					
					
					
			///////////////// DEPOSIT /////////////////
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
						
						if (session != null && ((Boolean) session.getAttribute("loggedin")) || session.getAttribute("role").equals("admin")) {
							
							int customer_id = (int) session.getAttribute("user_id");
							
							Account foundAccount = accountDAO.getAccountById(depositObject.getAccount_number());
							
							if(foundAccount != null) {
								
								int foundAccountCustomer = (int) foundAccount.getUserIDNumber();
								
								if (customer_id == foundAccountCustomer) {
									
									accountDAO.deposit(depositObject.getAccount_number(), depositObject.getAmount());
															
									String json = om.writeValueAsString(userController.findCustomerByID(customer_id));
									
									res.getWriter().println(json);
									
								} else {
									
									res.setStatus(401);
									
									res.getWriter().println("Incorrect Account Number");
									
									String json = om.writeValueAsString(userController.findCustomerByID(customer_id));
									
									res.getWriter().println(json);
								}
								
							} else {
								
								res.setStatus(404);
								
								res.getWriter().println("Account Doesn't Exist");
								
								String json = om.writeValueAsString(userController.findCustomerByID(customer_id));
								
								res.getWriter().println(json);
								
							}
						}
							
					}
				
				}
				break;
				
				
				
			///////////////// WITHDRAW /////////////////
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
					
					int accountNumber = withdrawalObject.getAccount_number();
					
					double balance = accountDAO.getAccountById(accountNumber).getBalance();
					
					double amount = withdrawalObject.getAmount();
					
					if(withdrawalObject != null) {
						if(amount < balance ) {
							accountDAO.withdraw(accountNumber, amount);
							System.out.println(body);
							res.setStatus(200);
							String json = om.writeValueAsString(accountDAO.getAccountById(withdrawalObject.getAccount_number()));
							res.getWriter().println(json);
						} else {
							res.getWriter().println("Insufficient funds!");
							res.setStatus(404);
						}
						
					}
					
				}
			
				break;
				
				
				
				
				
			///////////////// TRANSFER /////////////////
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
					double balance = accountDAO.getAccountById(from).getBalance();
					
					if(transferObject != null) {
						if(amount < balance ) {
							System.out.println("transfer in progress");
							accountDAO.transfer(to, from, amount);
							System.out.println(body);
							res.setStatus(200);
							String json = om.writeValueAsString(accountDAO.getTransferAccounts(to, from));
							res.getWriter().println(json);
						} else {
							res.getWriter().println("Insufficient funds!");
						}
						
					}
					
				}
					
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().println("The id you provided is not an integer");
			res.setStatus(400);
		}

	}

		
		



	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Lets us do POST requests in goGet
		doGet(req, res);
				
	}
	
//	if (req.getMethod().equals("POST")) {
//	
//	BufferedReader reader = req.getReader();
//
//	StringBuilder string = new StringBuilder();
//
//	String line = reader.readLine();
//
//	while (line != null) {
//		string.append(line);
//		line = reader.readLine();
//	}
//
//	String body = new String(string);
//	
//	Login log = om.readValue(body, Login.class);
//	
//	User user = userController.login(log.getUsername() , log.getPassword());
//	
//	
//	
//	
//	if (user != null) {
//		jwtStr = Jwts.builder().setSubject(
//				user.getUserName())
//					.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(120)))
//					.claim("id", user.getId())
//					.claim("role", user.getRole())
//					.signWith(key)
//					.compact();
//		res.setStatus(200);
//		res.getWriter().println(jwtStr);
//		
//		res.getWriter().println(Jwts.parserBuilder()
//					.require("role", "admin")
//					.setSigningKey(key).build().parseClaimsJws(jwtStr));
//				
//	} else {
//		res.setStatus(401);
//		res.getWriter().println("Incorrect username or password");
//	}
//
//	
//} else {
//
//	res.getWriter().println("please log in");
//}
	
	
//	HttpSession session = req.getSession(false);
//	if (session != null && ((Boolean) session.getAttribute("loggedin"))) {
//		System.out.println("user role: " + session.getAttribute("role"));
//		System.out.println("user id: " + session.getAttribute("user_id"));
//		System.out.println("user id: " + session.getAttribute("loggedin"));
//			
//	
//	} else {
//		res.getWriter().println("You must be logged in to do that!");
//	}

	
}
		
		
		
		
