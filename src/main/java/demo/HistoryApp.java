package demo;

import model.History;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

@SuppressWarnings("serial")
public class HistoryApp extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame
	 */
	public HistoryApp(List<History> history) {
				
		setTitle("Transactions history");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][]", "[][grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("");
		contentPane.add(scrollPane, "cell 1 1,grow");
		
		table = new JTable();
		table.setFont(new Font("Verdana", Font.PLAIN, 11));
		scrollPane.setViewportView(table);
		
		// setting our model for History table
		HistoryTableModel model = new HistoryTableModel(history);		
		table.setModel(model);
		
		// setting columns width for History table columns
		TableColumnModel cModel = table.getColumnModel();
		model.setColumnsWidth(cModel);
		
		setVisible(true);
	}
}
