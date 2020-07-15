package models;

public class Employee extends Standard {
	
	@Override
	public String getRole() {
		return this.role;
	}



	private String role = "employee";

}
