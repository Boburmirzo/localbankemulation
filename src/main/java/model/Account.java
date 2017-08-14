package model;

public class Account {
	
	private int id;
	private double balance;
	private boolean isCredit;
	
	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final double getBalance() {
		return balance;
	}

	public final void setBalance(double balance) {
		this.balance = balance;
	}

	public final boolean isCredit() {
		return isCredit;
	}

	public final void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", isCredit=" + isCredit + "]";
	}	
}
