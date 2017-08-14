package dao;


import model.Account;
import model.History;
import model.Transaction;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountDAO {
	private Connection conn;
	private String fileName = "database.properties";

	/**
	 * Constructor
	 */
	public AccountDAO() {
		// Reading data from properties file
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(fileName));

			String url = props.getProperty("dburl");
			System.out.println(url);
			String user = props.getProperty("user");
			String password = props.getProperty("password");

			conn = DriverManager.getConnection(url, user, password);

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Could not initialize connection with database. Properties file does not exist or corrupted.",
					"File not found", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not initialize connection with database. SQL Excetion occured.",
					"SQL exception", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Could not initialize connection with database. Failed to read database config properties.",
					"Input/Output exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Processing transaction
	 */
	public void transfer(int account1, int account2, double amount) {
		updateBalance(account1, amount);
		updateBalance(account2, -amount);
	}

	/**
	 * Updating balance of an Account
	 */
	void updateBalance(int account, double amount) {
		Statement statement = null;
		ResultSet rSet = null;

		try {
			String sql = "SELECT * FROM ACCOUNTS WHERE id = " + account;
			statement = conn.createStatement();
			rSet = statement.executeQuery(sql);

			double balance = 0;

			while (rSet.next()) {
				balance = rSet.getLong(2);
				balance -= amount;
			}
			sql = "UPDATE ACCOUNTS SET balance = " + balance + " WHERE id = " + account;
			statement.execute(sql);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Updating balance failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, rSet);
		}
	}

	/**
	 * Getting balance of an Account
	 */
	public double getBalance(int account) {
		Statement statement = null;
		ResultSet rSet = null;
		double balance = 0;

		try {
			String sql = "SELECT * FROM ACCOUNTS WHERE ID = " + account;
			statement = conn.createStatement();
			rSet = statement.executeQuery(sql);

			while (rSet.next()) {
				balance = rSet.getDouble(2);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Retrieving  balance failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, rSet);
		}
		return balance;
	}

	/**
	 * Writing transaction info into history table
	 */
	public void writeHistory(List<Transaction> transactions) {
		PreparedStatement stmnt = null;

		for (Transaction t : transactions) {
			String sql = "INSERT INTO HISTORY(id, src, dest, amount, description, status, date_time) VALUES(?, ?, ?, ?, ?, ?, ?)";

			try {
				stmnt = conn.prepareStatement(sql);

				stmnt.setInt(1, 0);
				stmnt.setInt(2, t.getSource());
				stmnt.setInt(3, t.getDestination());
				stmnt.setDouble(4, t.getAmount());
				stmnt.setString(5, t.getDesc());
				stmnt.setString(6, t.getStatus());
				stmnt.setTimestamp(7, null);
				stmnt.executeUpdate();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Inserting data into history table failed due to SQL Exception.",
						"SQL exception", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Adding new instance of Account to DB
	 */
	public void addAccount(double balance, boolean isCredit) {
		PreparedStatement statement = null;

		try {
			String sql = "INSERT INTO ACCOUNTS(id, balance, isCredit) VALUES (?, ?, ?)";
			statement = conn.prepareStatement(sql);

			// Setting parameters
			statement.setInt(1, 0);
			statement.setBigDecimal(2, new BigDecimal(balance));
			statement.setBoolean(3, isCredit);

			// Executing
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Inserting data into accounts table failed due to SQL Exception.",
					"SQL exception", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// there's nothing we can do
			}
		}
	}

	/**
	 * Deleting instance of Account from DB
	 */
	public void deleteAccount(int id) {
		PreparedStatement statement = null;

		try {
			String sql = "DELETE FROM ACCOUNTS WHERE id = (?)";
			statement = conn.prepareStatement(sql);

			// Setting parameters
			statement.setInt(1, id);

			// Executing
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Deleting account failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, null);
		}
	}

	/**
	 * Extracting instance of Account from DB
	 */
	public Account getAccount(int id) {
		Account account = null;
		Statement statement = null;
		ResultSet rSet = null;

		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM ACCOUNTS WHERE id = " + id;
			rSet = statement.executeQuery(sql);

			account = new Account();
			account.setId(id);

			while (rSet.next()) {
				account.setBalance(rSet.getDouble("balance"));
				account.setCredit(rSet.getBoolean("isCredit"));
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Retrieving account info failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, rSet);
		}
		return account;
	}

	/**
	 * Getting all accounts from DB into List
	 */
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();

		Statement statement = null;
		ResultSet rSet = null;

		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM ACCOUNTS";
			rSet = statement.executeQuery(sql);

			// Looping thru the ResultSet
			while (rSet.next()) {
				// Adding a new instance to accounts
				Account acc = new Account();
				acc.setId(rSet.getInt("id"));
				acc.setBalance(rSet.getDouble("balance"));
				acc.setCredit(rSet.getBoolean("isCredit"));
				accounts.add(acc);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Retrieving accounts info failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, rSet);
		}
		return accounts;
	}

	/**
	 * Getting history from DB into List
	 */
	public List<History> getHistory() {
		List<History> history = new ArrayList<>();

		Statement statement = null;
		ResultSet rSet = null;

		try {
			statement = conn.createStatement();
			String sql = "SELECT * FROM HISTORY";
			rSet = statement.executeQuery(sql);

			// Looping thru the ResultSet
			while (rSet.next()) {
				// Adding a new instance to history
				History h = new History();
				Transaction t = new Transaction();
				h.setT(t);

				h.setId(rSet.getInt("id"));
				t.setSource(rSet.getInt("src"));
				t.setDestination(rSet.getInt("dest"));
				t.setAmount(rSet.getDouble("amount"));
				t.setDesc(rSet.getString("description"));
				t.setStatus(rSet.getString("status"));
				h.setTimestamp(rSet.getTimestamp("date_time"));

				history.add(h);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Retrieving history info failed due to SQL Exception.", "SQL exception",
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(statement, rSet);
		}
		return history;
	}

	/**
	 * Closing Connection, Statement and ResultSet
	 */
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		if (myRs != null) {
			try {
				myRs.close();
			} catch (SQLException e) {
				// there's nothing we can do
			}
		}

		if (myStmt != null) {
			try {
				myStmt.close();
			} catch (SQLException e) {
				// there's nothing we can do
			}
		}

		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				// there's nothing we can do
			}
		}
	}

	/**
	 * Closing Statement and ResultSet
	 */
	private void close(Statement myStmt, ResultSet myRs) {
		close(null, myStmt, myRs);
	}

	/**
	 * Closing Connection
	 */
	public void close(Connection myConn) {
		close(myConn, null, null);
	}

	public Connection getConnect() {
		return conn;
	}

}
