package models;

public class Employee extends User {

	private String role = "employee";

	public Employee() {
		super();
		
	}

	public Employee(long id, String firstName, String lastName, String userName, String password, String email,
				String phoneNumber) {
		super(id, firstName, lastName, userName, password, email, phoneNumber);
			
	}
	
	public Employee(String firstName, String lastName, String userName, String password, String email,
			String phoneNumber) {
		super(firstName, lastName, userName, password, email, phoneNumber);
			
	}
	
	public Employee(long id, String firstName, String lastName, String role, String userName, String password, String email,
			String phoneNumber) {
		
			super(firstName, lastName, userName, password, email, phoneNumber);
			
	}

	@Override
	public String getRole() {
		return this.role;
	}

	@Override
	public String toString() {
		return "Employee [id=" + this.getId() + ", role=" + this.role + ", firstName=" + this.getFirstName()
				+ ", lastName=" + this.getLastName() + ", userName=" + getUserName() + ", password=" + getPassword()
				+ ", email=" + getEmail() + ", phoneNumber=" + getPhoneNumber() + "]";
	}

}
