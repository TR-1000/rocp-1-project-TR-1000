package models;

public class CheckingAccount extends Account {
	private String type = "checking";
	private String status;

	public CheckingAccount() {
		super();
		
	}

	public CheckingAccount(long accountNumber) {
		super(accountNumber);
		// TODO Auto-generated constructor stub
	}

	public CheckingAccount(String type) {
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
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
