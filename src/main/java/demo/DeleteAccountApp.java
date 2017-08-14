package demo;

import net.miginfocom.swing.MigLayout;
import transaction.TransactionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class DeleteAccountApp extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	
	/**
	 * Create the dialog.
	 */
	public DeleteAccountApp(TransactionManager manager) {
				
		setTitle("Delete account");
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 215);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow,center]", "[][][]"));
		{
			{
				JLabel labelBalance = new JLabel("Account ID");
				labelBalance.setHorizontalAlignment(SwingConstants.CENTER);
				labelBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
				contentPanel.add(labelBalance, "cell 0 1,alignx center");
			}
		}
		txtId = new JTextField();
		txtId.setSize(new Dimension(40, 0));
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (txtId.getText().equals("Enter account ID")) {
					txtId.setText("");
				}
			}
		});
		txtId.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtId.getText().equals("Enter account ID")) {
					txtId.setText("");
				}
			}
		});
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setFont(new Font("Courier New", Font.PLAIN, 12));
		txtId.setText("Enter account ID");
		contentPanel.add(txtId, "cell 0 2,alignx center,aligny center");
		txtId.setColumns(20);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtId.getText().equals("Enter account ID")) {
							JOptionPane.showMessageDialog(null, "Enter valid account ID", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								// ask for confirmation
								JOptionPane.showConfirmDialog(okButton, "Are you sure?", "Deleting account...", JOptionPane.YES_NO_OPTION);
								// if yes - try to extract valid value from input
								int id = Integer.valueOf(txtId.getText());
								// now let's invoke appropriate method to add new account
								manager.getAccountDao().deleteAccount(id);								
								// and close our dialog window
								dispose();
							} catch (NumberFormatException ex) {
								JOptionPane.showMessageDialog(null, "Enter valid account ID", "Error", JOptionPane.ERROR_MESSAGE);
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
