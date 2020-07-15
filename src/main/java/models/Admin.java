package models;

public class Admin extends Employee {
	
	private String role = "admin";

	@Override
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		;
	}
	
}
