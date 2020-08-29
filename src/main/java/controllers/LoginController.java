package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import models.Login;
import models.User;
import services.LoginService;

public class LoginController {

	private final LoginService loginService = new LoginService();
	private static final ObjectMapper om = new ObjectMapper();
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	public Key getKey() {
		return key;
	}


	// ===================================================
	// ///////////////// CUSTOMER LOGIN /////////////////
	// ===================================================

	public void customer_login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		if(req.getMethod().equals("POST")) {

			BufferedReader reader = req.getReader();

			StringBuilder s = new StringBuilder();

			String line = reader.readLine();

			while(line != null) {
				s.append(line);
				line=reader.readLine();
			}

			String body = new String(s);

			System.out.println(body);

			Login log = om.readValue(body, Login.class);

			User foundUser = loginService.customer_login(log.getUsername(), log.getPassword());


			if (foundUser != null) {
				String role = foundUser.getRole();
				int user_id = (int) foundUser.getId();
				System.out.println(user_id);

				HttpSession session = req.getSession();
				session.setAttribute("user", foundUser);
				session.setAttribute("role", role);
				session.setAttribute("user_id", user_id);
				session.setAttribute("loggedin", true);
				res.setStatus(200);
				String json = om.writeValueAsString(foundUser);
				res.getWriter().println("Login Successful");
				res.getWriter().println(json);

			} else {
				HttpSession session = req.getSession(false);
				if(session != null) {
					session.invalidate();
				}
				res.setStatus(401);
				res.getWriter().println("Invalid Username or Password");

			}

		} else {
			res.getWriter().println("Something went wrong");
		}

	}





	// ===================================================
	// ///////////////// EMPLOYEE LOGIN /////////////////
	// ===================================================

	public void employee_login(HttpServletRequest req, HttpServletResponse res) throws IOException {
		if(req.getMethod().equals("POST")) {
			
			BufferedReader reader = req.getReader();

			StringBuilder s = new StringBuilder();

			String line = reader.readLine();

			while(line != null) {
				s.append(line);
				line=reader.readLine();
			}

			String body = new String(s);

			System.out.println(body);

			Login log = om.readValue(body, Login.class);

			User foundUser = loginService.employee_login(log.getUsername(), log.getPassword());


			if (foundUser != null) {
				String jwtStr = Jwts.builder()
						.setSubject(foundUser.getUserName())
						.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(120)))
						.claim("id", foundUser.getId())
						.claim("role", foundUser.getRole())
						.signWith(getKey())
						.compact();
				
				System.out.println("1st KEY: " + getKey());
				
				
				
				//res.setStatus(200);
				//res.getWriter().println(jwtStr);
				System.out.println(jwtStr);
				
				String role = foundUser.getRole();
				int user_id = (int) foundUser.getId();
				System.out.println(user_id);

				HttpSession session = req.getSession();
				session.setAttribute("user", foundUser);
				session.setAttribute("role", role);
				session.setAttribute("user_id", user_id);
				session.setAttribute("loggedin", true);
				res.setStatus(200);
				String json = om.writeValueAsString(foundUser);
				res.getWriter().println(jwtStr);
				//res.getWriter().println("Login Successful");
				res.getWriter().println(json);

			} else {
				HttpSession session = req.getSession(false);
				if(session != null) {
					session.invalidate();
				}
				res.setStatus(401);
				res.getWriter().println("Invalid Username or Password");

			}

		} else {
			res.getWriter().println("Something went wrong");
		}

	}


	// ===========================================
	// ///////////////// LOGOUT /////////////////
	// ===========================================
	
	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session = req.getSession(false);

		if(session != null) {
			User currentUser = (User) session.getAttribute("user");
			System.out.println(currentUser);
			try {
				res.getWriter().println("Goodbye, " + currentUser.getFirstName());
				res.setStatus(200);
			} catch (Exception e) {
				System.out.println(e);
				res.getWriter().println("Goodbye!");
			}
			
			session.invalidate();
			res.setStatus(200);

		} else {
			res.setStatus(400);
			res.getWriter().println("You must be logged in to log out.");
		}

	}

	
	
	
	
	
	
	





	
}
