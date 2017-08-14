package demo;


import model.Transaction;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;

@SuppressWarnings("serial")
public class TransactionTableModel extends AbstractTableModel {
	
	private final int SOURCE = 0;
	private final int DEST = 1;
	private final int AMOUNT = 2;
	private final int DESC = 3;
	private final int STATUS = 4;
	
	private List<Transaction> transactions;
	
	private String[] columnNames = {"Source", "Destiantion", "Amount", "Description", "Status"};
	
	/**
	 * Constructor
	 */
	public TransactionTableModel(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	@Override
	public int getRowCount() {
		return transactions.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Transaction transaction = transactions.get(rowIndex);
		
		switch(columnIndex) {
		case SOURCE:
			return transaction.getSource();
		case DEST:
			return transaction.getDestination();
		case AMOUNT:
			return transaction.getAmount();
		case DESC:
			return transaction.getDesc();
		case STATUS:
			return transaction.getStatus();
		default:
			return transaction.getStatus();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {		
		return getValueAt(0, columnIndex).getClass();		
	}
	
	/**
	 * Setting custom columns' width values 
	 */
	public void setColumnsWidth(TableColumnModel cModel) {
		
		int srcMin = 70;
		int srcMax = 100;
		int srcPref = 70;
		
		int destMin = 70;
		int destMax = 100;
		int destPref = 70;
		
		int amountMin = 80;
		int amountMax = 100;
		int amountPref = 80;
		
		int descMin = 100;
		int descMax = 1000;
		int descPref = 200;
		
		int statusMin = 100;
		int statusMax = 900;
		int statusPref = 300;
		
		for (int col = 0; col < cModel.getColumnCount(); col++) {
			TableColumn column = cModel.getColumn(col);
			
			switch(col) {
				case SOURCE:
					column.setMinWidth(srcMin);
					column.setMaxWidth(srcMax);
					column.setPreferredWidth(srcPref);
					break;
				case DEST:
					column.setMinWidth(destMin);
					column.setMaxWidth(destMax);
					column.setPreferredWidth(destPref);
					break;
				case AMOUNT:
					column.setMinWidth(amountMin);
					column.setMaxWidth(amountMax);
					column.setPreferredWidth(amountPref);
					break;
				case DESC:
					column.setMinWidth(descMin);
					column.setMaxWidth(descMax);
					column.setPreferredWidth(descPref);
					break;
				case STATUS:
					column.setMinWidth(statusMin);
					column.setMaxWidth(statusMax);
					column.setPreferredWidth(statusPref);
					break;
			}			
		}
	}
}
