package com.tdinh.interview.codeme.trans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.tdinh.interview.codeme.trans.io.TransactionResolver;

/**
 * The TransactionManager is the core engine of the application, which loads
 * transactions from a csv file and stores them in a {@link TransactionStorage}
 * for later processing.
 * 
 * It provides capability to calculate the relative balance for an account 
 * for given time period.
 *  
 * @author Tuan V. Dinh
 *
 */
public class TransactionManager {

	private List<Transaction> transStorage;

	private TransactionResolver resolver;

	public TransactionManager(String transactionLogfile) {
		transStorage = new ArrayList<>();
		resolver = new TransactionResolver();
		init(transactionLogfile);
	}

	/**
	 * Return a relative balance for an given accountId in a given period of time.
	 * 
	 * The relative account balance is the sum of funds that were transferred
	 * to/from an account in a given time frame, it does not account for funds that
	 * were in that account prior to the timeframe.
	 * 
	 * @param accountId:
	 *            the account of interest
	 * @param start:
	 *            starting point of the timeframe
	 * @param end:
	 *            ending point of the timeframe
	 * @return relativeBalance
	 */
	public Double getRelativeBalance(String accountId, Date start, Date end) {

		double relativeBalance = 0.00;
		List<Transaction> matchedTrans = getAllTransactionByAccount(accountId, start, end);
		
		for (Transaction transaction : matchedTrans) {
			if (transaction.getFromAccount().equalsIgnoreCase(accountId)) {
				relativeBalance -= transaction.getAmount();
			} else if(transaction.getToAccount().equalsIgnoreCase(accountId)) {
				relativeBalance += transaction.getAmount();
			} 
		}

		return relativeBalance;
	}
	
	
	/**
	 * Return a transaction whose id match a given transaction id.
	 * Return null if found no match
	 * @param transId: transaction id
	 * @return a Transaction
	 */
	public Transaction getTransactionById(String transId) {
		return transStorage
			.stream()
			.filter(t -> t.getTransactionId().equalsIgnoreCase(transId))
			.findAny()
			.orElse(null);
	}
	
	/**
	 * Return the list of all in-reversed transaction that involved an accountId
	 * @param accountId:
	 *            the account of interest
	 * @param start:
	 *            starting point of the timeframe
	 * @param end:
	 *            ending point of the timeframe
	 * @return list of transaction
	 */
	public List<Transaction> getAllTransactionByAccount(String accountId, Date start, Date end) {
		return transStorage.stream()
				.filter(
						t -> (t.getFromAccount().equalsIgnoreCase(accountId) || t.getToAccount().equalsIgnoreCase(accountId))
						&& (t.getCreatedDate().before(end) && t.getCreatedDate().after(start))
						&& (t.getTransType().equals(TransactionType.PAYMENT))
						&& (!t.isReversed()))
				.collect(Collectors.toList());
	}

	private void init(String transactionLogfile) {
		try {
			FileReader fileReader = new FileReader(transactionLogfile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				Transaction trans = resolver.fromLogEntry(line);
				transStorage.add(trans);
				if (isReversal(trans)) {
					reverse(trans.getRelatedTransaction());
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new TransactionProcessingException("Log file " + transactionLogfile + " not found");
		} catch (IOException e) {
			throw new TransactionProcessingException("I/O error procesing " + transactionLogfile);
		}
	}

	private void reverse(String relatedTransaction) {
		transStorage.stream().forEach(t -> {
			if (t.getTransactionId().equalsIgnoreCase(relatedTransaction)) {
				t.setReversed(true);
			}
		});
	}

	private boolean isReversal(Transaction trans) {
		return trans.getTransType().equals(TransactionType.REVERSAL);
	}
}
