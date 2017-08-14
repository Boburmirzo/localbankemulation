package transaction;


import dao.AccountDAO;
import model.Account;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionManager {
	private List<Transaction> transactions;
	private final String fileToRead = "transactions.csv";
	private final String fileToWrite = "processed.csv";
	private AccountDAO accountDao;
	private TransactionReader reader;
	private TransactionWriter writer;
	
	private final int MAX_RAND_AMOUNT = 12_999;

	public TransactionManager() {
		accountDao = new AccountDAO();
		reader = new TransactionReader();
		writer = new TransactionWriter();		
	}

	/**
	 * Processing transaction list
	 */	
	public void process() {

		for (Transaction transaction : transactions) {
			if (isValid(transaction)) {
				accountDao.transfer(transaction.getSource(), transaction.getDestination(), transaction.getAmount());
				
				int source = transaction.getSource();
				int dest = transaction.getDestination();
				double sourceBalance = accountDao.getBalance(source);
				double destBalance = accountDao.getBalance(dest);
				
				String status = "Transfer sucessfully completed:";
				status += " id#" + source + " balance = " + sourceBalance;
				status += " id#" + dest + " balance = " + destBalance;
				
				transaction.setStatus(status);
			} else {
				transaction.setStatus("Insufficient funds on source account. Can't proceed.");
			}
		}
		accountDao.writeHistory(transactions);
		writer.writeCsvFile(fileToWrite, transactions);
	}
	
	/**
	 * Validation of transaction
	 */	
	private boolean isValid(Transaction transaction) {

		Account source = null;
		source = accountDao.getAccount(transaction.getSource());
		
		if (source.getBalance() > transaction.getAmount()) {
			return true;
		} else if (source.isCredit()) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Loading transactions from file 
	 */
	public void load() {
		transactions = reader.readCsvFile(fileToRead);
	}
	
	/** 
	 * Random generation of transactions 
	 */
	public void generate() {
		int size = accountDao.getAllAccounts().size();
		int first = accountDao.getAllAccounts().get(0).getId();
		Random rand = new Random();
		int listSize = size + rand.nextInt(size);
		List<Transaction> list = new ArrayList<Transaction>(listSize);		
		
		for (int i = 0 ; i < listSize; i++) {
			int source, dest;
			source = first + rand.nextInt(size);
			
			do {
				dest = first + rand.nextInt(size);
			} while (dest == source);
			
			int amount = rand.nextInt(MAX_RAND_AMOUNT);
			
			Transaction t = new Transaction();
			t.setSource(source);			
			t.setDestination(dest);
			t.setAmount(amount);
			t.setDesc("N/A");
			t.setStatus("Awaiting processing");
			
			list.add(t);
		}
		
		setTransactions(list);
		writer.writeCsvFile(fileToWrite, getTransactions());
	}
	
	public final List<Transaction> getTransactions() {
		return transactions;
	}

	final void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	public final AccountDAO getAccountDao() {
		return accountDao;
	}
}
