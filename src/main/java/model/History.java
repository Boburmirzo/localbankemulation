package model;

import java.sql.Timestamp;

public class History {
			
	private Transaction t;
	private int id;
	private Timestamp timestamp;	// Transaction status - successful or fail(with comments)
	
	
	@Override
	public String toString() {
		return "History record [id=" + id + ", source=" + t.getSource() + ", destination=" + t.getDestination() + ", amount=" + t.getAmount() + ", desc="
				+ t.getDesc() + ", error=" + t.getStatus() + "date & time=" + timestamp + "]";
	}

	public final Timestamp getTimestamp() {
		return timestamp;
	}

	public final void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}	
	
	public final Transaction getT() {
		return t;
	}

	public final void setT(Transaction t) {
		this.t = t;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}	
}
