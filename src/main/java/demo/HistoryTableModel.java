package demo;


import model.History;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.List;

@SuppressWarnings("serial")
public class HistoryTableModel extends AbstractTableModel {
	
	private final int ID = 0;
	private final int SOURCE = 1;
	private final int DEST = 2;
	private final int AMOUNT = 3;
	private final int DESC = 4;
	private final int STATUS = 5;
	private final int DATE_TIME = 6;
	
	private List<History> history;
	
	private String[] columnNames = {"ID", "Source", "Destiantion", "Amount", "Description", "Status" , "Date & Time"};
	
	/**
	 * Constructor
	 */
	public HistoryTableModel(List<History> history) {
		this.history = history;
	}
			
	@Override
	public int getRowCount() {
		return history.size();
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
		
		History h = history.get(rowIndex);
		
		switch(columnIndex) {
		case ID:
			return h.getId();
		case SOURCE:
			return h.getT().getSource();
		case DEST:
			return h.getT().getDestination();
		case AMOUNT:
			return h.getT().getAmount();
		case DESC:
			return h.getT().getDesc();
		case STATUS:
			return h.getT().getStatus();
		case DATE_TIME:
			return h.getTimestamp();
		default:
			return h.getTimestamp();
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
		
		int idMin = 35;
		int idMax = 100;
		int idPref = 50;
		
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
		int statusMax = 1200;
		int statusPref = 300;
		
		int dateMin = 100;
		int dateMax = 200;
		int datePref = 100;
		
		for (int col = 0; col < cModel.getColumnCount(); col++) {
			TableColumn column = cModel.getColumn(col);
			
			switch(col) {
				case ID:
					column.setMinWidth(idMin);
					column.setMaxWidth(idMax);
					column.setPreferredWidth(idPref);
					break;
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
				case DATE_TIME:
					column.setMinWidth(dateMin);
					column.setMaxWidth(dateMax);
					column.setPreferredWidth(datePref);
					break;
			}			
		}
	}
}
