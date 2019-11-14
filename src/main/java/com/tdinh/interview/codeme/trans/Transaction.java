package com.tdinh.interview.codeme.trans;

import java.util.Date;

/**
 * This class represents a transaction model.
 * 
 * @author Tuan V. Dinh
 *
 */
public class Transaction {

	String transactionId;
	String fromAccount;
	String toAccount;
	TransactionType transType;
	Date createdDate;
	Double amount;
	String relatedTransaction;
	boolean reversed = false;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public TransactionType getTransType() {
		return transType;
	}

	public void setTransType(TransactionType transType) {
		this.transType = transType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getRelatedTransaction() {
		return relatedTransaction;
	}

	public void setRelatedTransaction(String relatedTransaction) {
		this.relatedTransaction = relatedTransaction;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public static class Builder {
		private String transactionId;
		private String fromAccount;
		private String toAccount;
		private TransactionType transType;
		private Date createdDate;
		private Double amount;
		private String relatedTransaction;
		private boolean reversed;

		public Builder transactionId(String transactionId) {
			this.transactionId = transactionId;
			return this;
		}

		public Builder fromAccount(String fromAccount) {
			this.fromAccount = fromAccount;
			return this;
		}

		public Builder toAccount(String toAccount) {
			this.toAccount = toAccount;
			return this;
		}

		public Builder transType(TransactionType transType) {
			this.transType = transType;
			return this;
		}

		public Builder createdDate(Date createdDate) {
			this.createdDate = createdDate;
			return this;
		}

		public Builder amount(Double amount) {
			this.amount = amount;
			return this;
		}

		public Builder relatedTransaction(String relatedTransaction) {
			this.relatedTransaction = relatedTransaction;
			return this;
		}

		public Builder reversed(boolean reversed) {
			this.reversed = reversed;
			return this;
		}

		public Transaction build() {
			return new Transaction(this);
		}
	}

	private Transaction(Builder builder) {
		this.transactionId = builder.transactionId;
		this.fromAccount = builder.fromAccount;
		this.toAccount = builder.toAccount;
		this.transType = builder.transType;
		this.createdDate = builder.createdDate;
		this.amount = builder.amount;
		this.relatedTransaction = builder.relatedTransaction;
		this.reversed = builder.reversed;
	}
}
