package demo;

import model.Account;
import model.Transaction;
import net.miginfocom.swing.MigLayout;
import transaction.TransactionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

@SuppressWarnings("serial")
public class TransactionApp extends JFrame {

	private JPanel contentPane;
	private JTable tableT;
	private JTable tableA;
	private JButton btnProcess;
	private JButton btnGenerate;
	private JButton btnShowInfo;
	private JButton btnHistory;

	private TransactionManager manager;
	private List<Transaction> transactions;
	private List<Account> accounts;
	private JButton btnLoad;
	private JButton btnAddAccount;
	private JButton btnDeleteAccount;

	/**
	 * Create the frame.
	 */
	public TransactionApp(TransactionManager manager) {

		setManager(manager);

		setTitle("Bank Transactions Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				manager.getAccountDao().close(manager.getAccountDao().getConnect());
			}
		});
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("",
				"[50px:50px:50px,center][100px:550px:1720px,grow,center][50px:50px:50px,center][100px:100px:100px,center][50px:50px:50px,center]",
				"[center][grow 1][][center][center][center][grow 1][center][grow 1][center][center][center][grow 1][]"));

		JLabel lblTransactionsInfo = new JLabel("Transactions Info");
		lblTransactionsInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblTransactionsInfo, "cell 1 0");

		// --------------------
		// buttons block
		// --------------------

		// Button Load
		btnLoad = new JButton("Load");
		btnLoad.setToolTipText("Load transactions from csv file");
		btnLoad.setMinimumSize(new Dimension(100, 23));
		btnLoad.setMaximumSize(new Dimension(100, 23));
		contentPane.add(btnLoad, "cell 3 2");

		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.load();
				refreshTransactionsView();
				btnProcess.setEnabled(true);
			}
		});

		// Button Generate
		btnGenerate = new JButton("Generate");
		btnGenerate.setToolTipText("Randomly generate transaction");
		btnGenerate.setMaximumSize(new Dimension(100, 23));
		btnGenerate.setMinimumSize(new Dimension(100, 23));
		contentPane.add(btnGenerate, "cell 3 3");

		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.generate();
				refreshTransactionsView();
				btnProcess.setEnabled(true);
			}
		});

		// Button Process
		btnProcess = new JButton("Process");
		btnProcess.setToolTipText("Process transactions");
		btnProcess.setEnabled(false);
		btnProcess.setMinimumSize(new Dimension(100, 23));
		btnProcess.setMaximumSize(new Dimension(100, 23));
		contentPane.add(btnProcess, "cell 3 4");

		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manager.process();
				refreshTransactionsView();
				refreshAccountsView();
				btnProcess.setEnabled(false);
			}
		});

		// Button History
		btnHistory = new JButton("History");
		btnHistory.setToolTipText("View transactions history");
		btnHistory.setMinimumSize(new Dimension(100, 23));
		btnHistory.setMaximumSize(new Dimension(100, 23));
		contentPane.add(btnHistory, "cell 3 5");

		btnHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HistoryApp(manager.getAccountDao().getHistory());
			}
		});
		// --------------------
		// end of buttons block
		// --------------------

		JLabel lblAccountsInfo = new JLabel("Accounts Info");
		lblAccountsInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(lblAccountsInfo, "cell 1 7");

		JScrollPane scrollPaneT = new JScrollPane();
		contentPane.add(scrollPaneT, "cell 1 1 1 6,grow");

		tableT = new JTable();
		tableT.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPaneT.setViewportView(tableT);

		JScrollPane scrollPaneA = new JScrollPane();
		contentPane.add(scrollPaneA, "cell 1 8 1 5,grow");

		tableA = new JTable();
		scrollPaneA.setViewportView(tableA);

		// Show Info button
		btnShowInfo = new JButton("Show info");
		btnShowInfo.setToolTipText("Show accounts' info");
		btnShowInfo.setMinimumSize(new Dimension(100, 23));
		btnShowInfo.setMaximumSize(new Dimension(100, 23));
		contentPane.add(btnShowInfo, "cell 3 9");
		
		btnShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshAccountsView();
			}
		});
		
		// Button Add
		btnAddAccount = new JButton("Add");		
		btnAddAccount.setMinimumSize(new Dimension(100, 23));
		btnAddAccount.setMaximumSize(new Dimension(100, 23));
		btnAddAccount.setToolTipText("Add new account");
		contentPane.add(btnAddAccount, "cell 3 10");

		btnAddAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddAccountApp(manager);
				refreshAccountsView();
			}
		});
		
		// button Delete
		btnDeleteAccount = new JButton("Delete");		
		btnDeleteAccount.setMinimumSize(new Dimension(100, 23));
		btnDeleteAccount.setMaximumSize(new Dimension(100, 23));
		btnDeleteAccount.setToolTipText("Delete existing account");
		contentPane.add(btnDeleteAccount, "cell 3 11");	
		
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DeleteAccountApp(manager);
				refreshAccountsView();
			}
		});	
	} // end of constructor

	private void refreshTransactionsView() {
		setTransactions(manager.getTransactions());
		TransactionTableModel model = new TransactionTableModel(transactions);
		tableT.setModel(model);
		TableColumnModel columnModel = tableT.getColumnModel();
		model.setColumnsWidth(columnModel);
	}

	private void refreshAccountsView() {
		setAccounts(manager.getAccountDao().getAllAccounts());
		AccountTableModel modelA = new AccountTableModel(accounts);
		tableA.setModel(modelA);
		TableColumnModel columnModel = tableA.getColumnModel();
		modelA.setColumnsWidth(columnModel);
	}

	public TransactionManager getManager() {
		return manager;
	}

	public void setManager(TransactionManager manager) {
		this.manager = manager;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

}
