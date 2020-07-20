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
import models.Admin;
import models.Employee;
import models.User;

public class MasterServlet extends HttpServlet {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserController userController = new UserController();
	
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
			switch (portions[0]) {
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
						//Avenger a = om.readValue(body, Avenger.class);
						
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
				
			case "employees":
				if (portions.length == 2) {
					int id = Integer.parseInt(portions[1]);
					Set<Employee> emp = userController.findEmployeeByID(id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(emp);
					res.getWriter().println(json);
				} else {
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
						//Avenger a = om.readValue(body, Avenger.class);
						
						System.out.println(user);

//						if (userController.addUser(user)) {
//							System.out.println("in addAvenger if statement");
//							res.setStatus(201);
//							res.getWriter().println("Avenger was created");
//						}
					
						
					} else {

						Set<User> all = userController.findAllEmployees();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
					}
					
				
					
				}
			break;	
				
			case "admin":
				Set<Admin> all = userController.findAllAdmins();
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(all));
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().println("The id you provided is not an integer");
			res.setStatus(400);
		}

	}

		
		



	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
res.setContentType("application/json");
		
		// this will set the default response to not found;
		// the request was successful
		res.setStatus(404);
		
		final String URI = req.getRequestURI().replace("/rocp-project/", "");

		String[] portions = URI.split("/");

		System.out.println(Arrays.toString(portions));
		
		System.out.println("Updating user");

		try {
			switch (portions[0]) {
			case "UpdateUser":
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
			break;	
				
			case "employees":
				if (portions.length == 2) {
					int user_id = Integer.parseInt(portions[1]);
					Set<Employee> emp = userController.findEmployeeByID(user_id);
					res.setStatus(200);
					// The ObjectMapper (om) here will take the object (a) and convert it to a JSON object String.
					String json = om.writeValueAsString(emp);
					res.getWriter().println(json);
				} else {
					
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

						
					} else {

						Set<User> all = userController.findAllEmployees();
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(all));
					}
						
				}
			break;	
				
			case "admin":
				Set<Admin> all = userController.findAllAdmins();
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(all));
				
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			res.getWriter().println("The id you provided is not an integer");
			res.setStatus(400);
		}
	}
}
		
		
		
		
