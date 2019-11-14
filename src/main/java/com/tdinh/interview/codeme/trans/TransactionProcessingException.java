package com.tdinh.interview.codeme.trans;

@SuppressWarnings("serial")
public class TransactionProcessingException extends RuntimeException {
	public TransactionProcessingException(String message) {
		super(message);
	}
}
