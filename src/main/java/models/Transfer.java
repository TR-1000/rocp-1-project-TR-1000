package models;

public class Transfer implements Transaction {
	
	private String type = "transfer";
	int from_account_number;
	int to_account_number;
	double amount;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFrom_account_number() {
		return from_account_number;
	}
	public void setFrom_account_number(
			int from_account_number) {
		this.from_account_number = from_account_number;
	}
	public int getTo_account_number() {
		return to_account_number;
	}
	public void setTo_account_number(int to_account_number) {
		this.to_account_number = to_account_number;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	

}
