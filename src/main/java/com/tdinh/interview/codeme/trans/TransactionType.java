package com.tdinh.interview.codeme.trans;

public enum TransactionType {

	PAYMENT("payment"), REVERSAL("reversal");

	private String type;

	TransactionType(String type) {
		this.type = type;
	}

	public static TransactionType fromType(String type) {
		for (TransactionType t : TransactionType.values()) {
			if (t.type.equalsIgnoreCase(type)) {
				return t;
			}
		}
		return null;
	}
}
