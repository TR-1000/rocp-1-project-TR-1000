package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

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
import models.Transfer;
import models.User;
import models.Withdrawal;


public class MasterServlet extends HttpServlet {

	private static final ObjectMapper om = new ObjectMapper();
	private static final UserController userController = new UserController();
	private static final LoginController loginController = new LoginController();
	private static final AccountDAO accountDAO = new AccountDAOImpl();
	//private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


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





// ================================================
// ///////////////// LOGIN LOGOUT /////////////////
// ================================================
			case "customer_login":

				loginController.customer_login(req, res);

				break;

			case "employee_login":

				loginController.employee_login(req, res);

				break;

			case "logout":

				if (session != null && ((Boolean) session.getAttribute("loggedin"))) {

					loginController.logout(req, res);

				} else {

					res.getWriter().println("Can't log out if you were never logged in");
				}

				break;


// ============================================
// ///////////////// REGISTER /////////////////
// ============================================

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

					try {

						User user = om.readValue(body, User.class);
						if (userController.insert(user)) {
							res.setStatus(201);
							res.getWriter().println("Registration Successful!");
							String json = om.writeValueAsString(userController.findCustomerByEmail(user.getEmail()));
							res.getWriter().println(json);

						} else {

							res.getWriter().println("Mistakes Were Made");

						}

					} catch (Exception e) {

						res.getWriter().println("Mistakes Were Made");

					}

			} else {

				res.getWriter().println("Something Went Wrong");

			}

			break;





// =============================================
// ///////////////// CUSTOMERS /////////////////
// =============================================

			case "customer":
				System.out.println("2nd KEY: " + loginController.getKey());
				String[] auth = req.getHeader("Authorization").split(" ");
				String jwtStr = auth[1];
				System.out.println(jwtStr);
				
				res.getWriter().println(Jwts.parserBuilder()
						.require("role", "admin")
						.setSigningKey(loginController.getKey())
						.build()
							.parseClaimsJws(jwtStr));
				
//				res.getWriter().println(Jwts.parserBuilder()
//				.require("role", "admin")
//				.setSigningKey(key).build().parseClaimsJws(jwtStr));
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);

					if (session != null && ((Boolean) session.getAttribute("loggedin"))
							&& (
							session.getAttribute("user_id").equals(id)
							||
							session.getAttribute("role").equals("admin")
							||
							session.getAttribute("role").equals("employees")
							)
						)
					{
						User user = userController.findCustomerByID(id);
						res.setStatus(200);
						String json = om.writeValueAsString(user);
						res.getWriter().println(json);

					} else {

						res.setStatus(401);
						res.getWriter().println("Access Denied!");
					}

				} else {

					if ( session != null ) {
						if (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("admin")) {
							Set<User> all = userController.findAllCustomers();
							res.setStatus(200);
							res.getWriter().println(om.writeValueAsString(all));
							
						} else {
							res.setStatus(401);
							res.getWriter().println(om.writeValueAsString("Access Denied!"));
						}

					}
					
				}	

				break;





// ========================================================
// ///////////////// UPDATE CUSTOMER INFO /////////////////
// ========================================================

			case "customer_update":
				if (req.getMethod().equals("POST")) {

				System.out.println("Updating user");

				if (portions.length == 2) {

					int id = Integer.parseInt(portions[1]);



					if (session != null && ((Boolean) session.getAttribute("loggedin"))
							&& (session.getAttribute("user_id").equals(id)
							|| session.getAttribute("role").equals("admin"))) {

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
						res.getWriter().println("Update Successful!");
						String json = om.writeValueAsString(foundUser);
						res.getWriter().println(json);

					} else {

						res.setStatus(401);
						res.getWriter().println("Access Denied!");

					}
				}

				} else {

					res.getWriter().println("Mistakes Were Made");
				}

				break;





// =============================================
// ///////////////// EMPLOYEES /////////////////
// =============================================

			case "employee":
				if (portions.length == 2) {
					
					int id = Integer.parseInt(portions[1]);
					Set<Employee> emp = userController.findEmployeeByID(id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(emp);
					res.getWriter().println(json);

				} else {

					if ( session != null ) {
						if (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("employee")) {
							Set<User> all = userController.findAllCustomers();
							res.setStatus(200);
							res.getWriter().println(om.writeValueAsString(all));
						}

						res.setStatus(401);
						res.getWriter().println(om.writeValueAsString("Access Denied!"));

					} else {
						res.setStatus(401);
						res.getWriter().println(om.writeValueAsString("Access Denied!"));
					}

				}

				break;






// =========================================
// ///////////////// ADMIN /////////////////
// =========================================

			case "admin":
				
				if ( session != null ) {
					if (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("employee")) {
						Set<Admin> all = userController.findAllAdmins();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
						break;
					} else {

						res.setStatus(401);
						res.getWriter().println(om.writeValueAsString("Access Denied!"));
					}
				}	
				
				break;

				



// ============================================
// ///////////////// ACCOUNTS /////////////////
// ============================================

			case "account":

				if (portions.length == 2) {

					int id = Integer.parseInt(portions[1]);
					System.out.println(session.getAttribute("role"));
					if ( session != null && ((Boolean) session.getAttribute("loggedin")) && ((session.getAttribute("user_id").equals(id) || (session.getAttribute("role").equals("admin") || (session.getAttribute("role").equals("employee"))  ))) ) {

						Account account = accountDAO.getAccountById(id);

						res.setStatus(200);

						String json = om.writeValueAsString(account);

						res.getWriter().println(json);

					} else {

						res.setStatus(401);
						res.getWriter().println("Access Denied!");
					}



				} else {

					if ( session != null ) {
						if (session.getAttribute("role").equals("admin") || session.getAttribute("role").equals("employee")) {
							Set<Account> allAccounts = accountDAO.selectAllAccounts();
							res.setStatus(200);
							res.getWriter().println(om.writeValueAsString(allAccounts));

						} else {

							res.setStatus(401);
							res.getWriter().println(om.writeValueAsString("Access Denied!"));
						}

				} else {

					res.setStatus(401);
					res.getWriter().println(om.writeValueAsString("Access Denied!"));
				}

			}
				break;






// ===============================================
// ///////////////// NEW ACCOUNT /////////////////
// ===============================================

			case "account_new":

				if (session != null && ((Boolean) session.getAttribute("loggedin"))) {

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

							res.setStatus(201);
							String json = om.writeValueAsString(userController.findCustomerByID(customer_id));
							res.getWriter().println(json);

						} else {

							res.getWriter().println("Mistakes Were Made");

						}

					} else {

						res.getWriter().println("Something went wrong");

					}
				} else {

					res.getWriter().println("Please Log In");
				}

				break;







// ===================================================
// ///////////////// UPDATE ACCOUNTS /////////////////
// ===================================================

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





// ============================================
// ///////////////// DEPOSIT /////////////////
// ============================================

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

						if (session != null && ((Boolean) session.getAttribute("loggedin"))) {

							int customer_id = (int) session.getAttribute("user_id");

							Account foundAccount = accountDAO.getAccountById(depositObject.getAccount_number());

							if(foundAccount != null) {

								int foundAccountCustomer = (int) foundAccount.getUserIDNumber();

								if (customer_id == foundAccountCustomer) {

									accountDAO.deposit(depositObject.getAccount_number(), depositObject.getAmount());

									String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

									res.setStatus(200);

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





// ============================================
// ///////////////// WITHDRAW /////////////////
// ============================================

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

						if (session != null && ((Boolean) session.getAttribute("loggedin"))) {

							int customer_id = (int) session.getAttribute("user_id");

							Account foundAccount = accountDAO.getAccountById(withdrawalObject.getAccount_number());

							if(foundAccount != null) {

								int foundAccountCustomer = (int) foundAccount.getUserIDNumber();

								if (customer_id == foundAccountCustomer) {

									double balance = foundAccount.getBalance();

									double amount = withdrawalObject.getAmount();

									if(amount <= balance ) {

										accountDAO.withdraw(withdrawalObject.getAccount_number(), withdrawalObject.getAmount());

										String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

										res.setStatus(200);

										res.getWriter().println(json);

									} else {

										res.setStatus(400);

										res.getWriter().println("Insufficient funds!");

										String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

										res.getWriter().println(json);

									}

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






// ============================================
// ///////////////// TRANSFER /////////////////
// ============================================

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

					if(transferObject != null) {

						if (session != null && ((Boolean) session.getAttribute("loggedin"))) {

							int customer_id = (int) session.getAttribute("user_id");

							int to = transferObject.getTo_account_number();
							Account foundToAccount = accountDAO.getAccountById(to);

							int from = transferObject.getFrom_account_number();
							Account foundFromAccount = accountDAO.getAccountById(from);


							if(foundFromAccount != null && foundToAccount != null) {

								int foundAccountCustomer = (int) foundFromAccount.getUserIDNumber();

								if (customer_id == foundAccountCustomer) {

									double amount = transferObject.getAmount();

									double balance = foundFromAccount.getBalance();

									if(amount <= balance ) {

										accountDAO.transfer(to, from, amount);

										String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

										res.setStatus(200);

										res.getWriter().println(json);

									} else {

										res.setStatus(400);

										res.getWriter().println("Insufficient funds!");

										String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

										res.getWriter().println(json);

									}

								} else {

									res.setStatus(401);

									res.getWriter().println("Access Denied!");

									String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

									res.getWriter().println(json);
								}

							} else {

								res.setStatus(404);

								res.getWriter().println("One Of These Accounts Doesn't Exist");

								String json = om.writeValueAsString(userController.findCustomerByID(customer_id));

								res.getWriter().println(json);

							}

						}

					}

				}

				break;
			}


		} catch (NumberFormatException e) {
			res.getWriter().println("Mistakes Were Made");
			e.printStackTrace();
			res.setStatus(400);
		}

	}








	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Lets us do POST requests in goGet
		doGet(req, res);

	}


// jsonwebtoken

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

}
