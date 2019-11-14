package com.tdinh.interview.codeme.trans.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.tdinh.interview.codeme.trans.Transaction;
import com.tdinh.interview.codeme.trans.TransactionType;

/**
 * Unit test class for {@link TransactionResolver}
 * 
 * @author Tuan V. Dinh
 *
 */
public class TransactionResolverTest {

	private TransactionResolver resolver = new TransactionResolver();

	@Test
	public void testResolvePaymentTrans() {
		String transLog = "TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT";
		Transaction trans = resolver.fromLogEntry(transLog);
		assertEquals("TX10001", trans.getTransactionId());
		assertEquals("ACC334455", trans.getFromAccount());
		assertEquals("ACC778899", trans.getToAccount());
		assertTrue(trans.getAmount().compareTo(25.00) == 0);
		assertEquals(TransactionType.PAYMENT, trans.getTransType());
		Date date = trans.getCreatedDate();
		assertEquals("Sat Oct 20 12:47:55 AEDT 2018", date.toString());
		assertNull(trans.getRelatedTransaction());
		assertTrue(!trans.isReversed());
	}

	@Test
	public void testResolveReversalTrans() {
		String transLog = "TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL, TX10002";
		Transaction trans = resolver.fromLogEntry(transLog);
		assertEquals("TX10004", trans.getTransactionId());
		assertEquals("ACC334455", trans.getFromAccount());
		assertEquals("ACC998877", trans.getToAccount());
		assertTrue(trans.getAmount().compareTo(10.50) == 0);
		assertEquals(TransactionType.REVERSAL, trans.getTransType());
		Date date = trans.getCreatedDate();
		assertEquals("Sat Oct 20 19:45:00 AEDT 2018", date.toString());
		assertEquals("TX10002", trans.getRelatedTransaction());
		assertTrue(!trans.isReversed());
	}

	@Test(expected = TransactionIOException.class)
	public void testParseWrongFormatReturnExpectedException() {
		String transLog = "TX10004, ACC334455, ACC998877, 20/10/2018-19:45:00.000, 10.50, REVERSAL, TX10002";
		resolver.fromLogEntry(transLog);
	}

	@Test(expected = TransactionIOException.class)
	public void testParseWrongFormat2ReturnExpectedException() {
		String transLog = "TX10004, ACC334455, ACC998877";
		resolver.fromLogEntry(transLog);
	}
}
