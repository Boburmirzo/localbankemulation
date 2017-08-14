package demo;


import model.Account;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;

@SuppressWarnings("serial")
public class AccountTableModel extends AbstractTableModel {
	
	private final int ID = 0;
	private final int BALANCE = 1;
	private final int IS_CREDIT = 2;
	
	private List<Account> accounts;
	
	private String[] columnNames = {"Account ID", "Balance", "Debit/Credit"};
	
	/**
	 * Constructor
	 */
	public AccountTableModel(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	@Override
	public int getRowCount() {
		return accounts.size();
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
		
		Account account = accounts.get(rowIndex);
		
		switch(columnIndex) {
		case ID:
			return account.getId();
		case BALANCE:
			return account.getBalance();
		case IS_CREDIT:
			return account.isCredit();		
		default:
			return account.isCredit();
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
		
		int idMin = 100;
		int idMax = 1000;
		int idPref = 100;
		
		int balMin = 100;
		int balMax = 1000;
		int balPref = 150;
		
		int creditMin = 100;
		int creditMax = 1000;
		int creditPref = 100;
		
		for (int col = 0; col < cModel.getColumnCount(); col++) {
			TableColumn column = cModel.getColumn(col);
			
			switch(col) {
				case ID:
					column.setMinWidth(idMin);
					column.setMaxWidth(idMax);
					column.setPreferredWidth(idPref);
					break;
				case BALANCE:
					column.setMinWidth(balMin);
					column.setMaxWidth(balMax);
					column.setPreferredWidth(balPref);
					break;
				case IS_CREDIT:
					column.setMinWidth(creditMin);
					column.setMaxWidth(creditMax);
					column.setPreferredWidth(creditPref);
					break;
			}			
		}
	}
}
