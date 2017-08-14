package transaction;

import model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionReader {
	// Transaction attributes
	final String SOURCE = "Source";
	final String DESTINATION = "Destination";
	final String AMOUNT = "Amount";
	final String DESC = "Desc";
	final String STATUS = "Status";
	// CSV file header
	final String[] FILE_HEADER = {SOURCE, DESTINATION, AMOUNT, DESC, STATUS};	
	// Create a new list of transactions to be filled by CSV file data
	List<Transaction> transactions = new ArrayList<Transaction>();

	/** 
	 * Reading data from CSV file and populating transactions list
	 */
	public List<Transaction> readCsvFile(String fileName) {

		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader(FILE_HEADER);

		try {
			// initialize FileReader object
			fileReader = new FileReader(fileName);

			// initialize CSVParser object
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			// Read the CSV file records starting from the second record to skip
			// the header
			for (int i = 1; i < csvRecords.size(); i++) {
				
				CSVRecord record = csvRecords.get(i);
				Transaction transaction = new Transaction();
				transaction.setSource(Integer.parseInt(record.get(SOURCE)));
				transaction.setDestination(Integer.parseInt(record.get(DESTINATION)));
				transaction.setAmount(Double.parseDouble(record.get(AMOUNT)));
				transaction.setDesc(record.get(DESC));
				transaction.setStatus(record.get(STATUS));
				// adding transaction to list
				transactions.add(transaction);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occured while trying to read csv file!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error occured while closing fileReader/csvFileParser!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return transactions;
	}
}