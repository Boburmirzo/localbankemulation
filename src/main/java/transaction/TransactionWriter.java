package transaction;

import model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class TransactionWriter {
	// Delimiter used in CSV file
	private final String NEW_LINE_SEPARATOR = "\n";
	// CSV file header
	private final Object[] FILE_HEADER = { "Source", "Destination", "Amount", "Desc", "Status" };
		
	void writeCsvFile(String fileName, List<Transaction> transactions) {
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			// Initialize FileWriter object
			fileWriter = new FileWriter(fileName);
			
			// Initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			
			// Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);
			
			// Write List to file
			for (Transaction transaction : transactions) {
				csvFilePrinter.print(transaction.getSource());
				csvFilePrinter.print(transaction.getDestination());
				csvFilePrinter.print(transaction.getAmount());
				csvFilePrinter.print(transaction.getDesc());
				csvFilePrinter.print(transaction.getStatus());
				csvFilePrinter.println();
			}			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error occured while trying to write to csv file!", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error occured while flushing/closing fileWriter/csvPrinter !!!", "Error", JOptionPane.ERROR_MESSAGE);				
			}
		}
	}
}