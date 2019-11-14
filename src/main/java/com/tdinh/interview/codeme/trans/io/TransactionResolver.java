package com.tdinh.interview.codeme.trans.io;

import com.tdinh.interview.codeme.trans.Transaction;
import com.tdinh.interview.codeme.trans.TransactionType;
import com.tdinh.interview.codeme.trans.util.DateUtils;

/**
 * The transaction resolver class that resolves a transaction in some form into
 * a {@link Transaction}, e.g: a line of text
 * 
 * @author Tuan V. Dinh
 *
 */
public class TransactionResolver {

	/**
	 * 
	 * @param logEntry
	 * @return
	 */
	public Transaction fromLogEntry(String logEntry) {
		try {
			String[] fields = logEntry.split(",");
			TransactionType transType = TransactionType.fromType(fields[5].trim());
			return new Transaction.Builder()
					.transactionId(fields[0].trim())
					.fromAccount(fields[1].trim())
					.toAccount(fields[2].trim())
					.createdDate(DateUtils.fromString(fields[3].trim()))
					.amount(Double.valueOf(fields[4]))
					.transType(transType)
					.reversed(false)
					.relatedTransaction(transType.equals(TransactionType.REVERSAL) ? fields[6].trim() : null).build();
		} catch (Exception ex) {
			throw new TransactionIOException("Error resolving a transaction from " + logEntry + ": " + ex.getMessage() );
		}
	}

}
