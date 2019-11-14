package com.tdinh.interview.codeme.trans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.tdinh.interview.codeme.trans.util.DateUtils;

public class TransactionManagerTest {

	private TransactionManager transManager;
	
	@Test
	public void testTransManagerWithDefaulSample() throws ParseException {
		String logFile = getClass().getClassLoader().getResource("translog1.txt").getFile();
		transManager = new TransactionManager(logFile);
		Date start = DateUtils.fromString("20/10/2018 12:00:00");
		Date end = DateUtils.fromString("20/10/2018 19:00:00");
		assertEquals(new Double("-25.00"), transManager.getRelativeBalance("ACC334455", start, end));
		Transaction reversedTrans = transManager.getTransactionById("TX10002");
		assertNotNull(reversedTrans);
		assertTrue(reversedTrans.isReversed());
	}
	
	@Test
	public void testTransManagerWithDefaultSample2() throws ParseException {
		String logFile = getClass().getClassLoader().getResource("translog1.txt").getFile();
		transManager = new TransactionManager(logFile);
		Date start = DateUtils.fromString("20/10/2018 12:00:00");
		Date end = DateUtils.fromString("21/10/2018 09:30:31");
		assertEquals(new Double("-32.25"), transManager.getRelativeBalance("ACC334455", start, end));
	}
	
	@Test
	public void testTransManagerWithDefaultSample3() throws ParseException {
		String logFile = getClass().getClassLoader().getResource("translog1.txt").getFile();
		transManager = new TransactionManager(logFile);
		Date start = DateUtils.fromString("20/10/2018 12:00:00");
		Date end = DateUtils.fromString("21/10/2018 09:29:59");
		assertEquals(new Double("-25.00"), transManager.getRelativeBalance("ACC334455", start, end));
	}
	
	@Test
	public void testTransManagerWithDefaultSample4() throws ParseException {
		String logFile = getClass().getClassLoader().getResource("translog1.txt").getFile();
		transManager = new TransactionManager(logFile);
		Date start = DateUtils.fromString("20/10/2018 12:00:00");
		Date end = DateUtils.fromString("21/10/2018 09:30:30");
		assertEquals(new Double("-5.00"), transManager.getRelativeBalance("ACC998877", start, end));
	}
}
