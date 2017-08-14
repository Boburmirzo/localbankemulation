package demo;

import net.miginfocom.swing.MigLayout;
import transaction.TransactionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AddAccountApp extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBalance;
	private JCheckBox checkBox;
	
	/**
	 * Create the dialog.
	 */
	public AddAccountApp(TransactionManager manager) {
				
		setTitle("Add new account");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 215);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][100px][grow]", "[][][][]"));
		{
			JLabel labelBalance = new JLabel("Initial balance");
			labelBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(labelBalance, "cell 1 1,alignx left");
		}
		{
			txtBalance = new JTextField();
			txtBalance.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (txtBalance.getText().equals("Initial balance")) {
						txtBalance.setText("");
					}
				}
			});
			txtBalance.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (txtBalance.getText().equals("Initial balance")) {
						txtBalance.setText("");
					}
				}
			});
			txtBalance.setHorizontalAlignment(SwingConstants.CENTER);
			txtBalance.setFont(new Font("Courier New", Font.PLAIN, 12));
			txtBalance.setText("Initial balance");
			contentPanel.add(txtBalance, "cell 2 1,alignx center,aligny center");
			txtBalance.setColumns(20);
		}
		{
			JLabel labelCredit = new JLabel("Credit account");
			labelCredit.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(labelCredit, "cell 1 3,alignx left");
		}
		{
			checkBox = new JCheckBox("");
			contentPanel.add(checkBox, "cell 2 3,alignx center");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtBalance.getText().equals("Initial balance")) {
							JOptionPane.showMessageDialog(null, "Enter valid balance value", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								// try to extract valid value from input
								double balance = Double.valueOf(txtBalance.getText());
								// checking if account is credit or debit
								boolean isCredit = (checkBox.isSelected() ? true : false);
								// now let's invoke appropriate method to add new account
								manager.getAccountDao().addAccount(balance, isCredit);
								// congratulate user
								JOptionPane.showMessageDialog(okButton, "New account sucessfully added!", "New account added", JOptionPane.INFORMATION_MESSAGE);
								// and close our dialog window
								dispose();
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(null, "Enter valid balance value", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
