package models;

public class SavingsAccount extends Account {
	private String type =  "savings";
	private String status;
	
	
	public SavingsAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public SavingsAccount(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		return this.type;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getStatus() {
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}
}
