package model;

public class Transaction {
	
	private int source;		// Source account	
	private int dest;		// Destination account	
	private double amount;	// Amount of transaction	
	private String desc;	// Credit or debit account flag	
	private String status;	// Transaction status - successful or fail(with comments)
	
	public final int getSource() {
		return source;
	}

	public final void setSource(int source) {
		this.source = source;
	}

	public final int getDestination() {
		return dest;
	}

	public final void setDestination(int destination) {
		this.dest = destination;
	}

	public final double getAmount() {
		return amount;
	}

	public final void setAmount(double amount) {
		this.amount = amount;
	}

	public final String getDesc() {
		return desc;
	}

	public final void setDesc(String desc) {
		this.desc = desc;
	}
	
	public final String getStatus() {
		return status;
	}

	public final void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Transaction [source=" + source + ", destination=" + dest + ", amount=" + amount + ", desc="
				+ desc + ", error=" + status + "]";
	}	
}
