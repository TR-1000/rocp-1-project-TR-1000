package models;

public class Admin extends User {

    private String role = "admin";

    public Admin() {
        super();

    }

    public Admin(long id, String firstName, String lastName, String userName, String password, String email,
				 String phoneNumber) {
        super(id, firstName, lastName, userName, password, email, phoneNumber);

    }
    
    public Admin(String firstName, String lastName, String userName, String password, String email,
			 String phoneNumber) {
    	super(firstName, lastName, userName, password, email, phoneNumber);

    }
    
    public Admin(long id, String firstName, String lastName, String role, String userName, String password, String email,
			String phoneNumber) {
		
			super(firstName, lastName, userName, password, email, phoneNumber);
			
	}

    @Override
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Admin [id=" + this.getId() + ", role=" + this.role + ", firstName=" + this.getFirstName() + ", " +
				"lastName=" + this.getLastName()
                + ", userName=" + getUserName() + ", password=" + getPassword() + ", email=" + getEmail() + ", " +
				"phoneNumber="
                + getPhoneNumber() + ", address=" + "]";
    }

}
