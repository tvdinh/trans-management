package com.tdinh.interview.codeme;

import java.text.ParseException;
import java.util.Date;

import com.tdinh.interview.codeme.trans.TransactionManager;
import com.tdinh.interview.codeme.trans.util.DateUtils;

/**
 * Transaction Management App that returns relative balance for an account 
 * for a given time period as well as the number of transaction involved
 * 
 * The app expects 4 input
 * (1) path to transaction load file
 * (2) accountId of interest
 * (3) Start date (strictly in "dd/MM/yyyy HH:mm:ss" format)
 * (4) End date (strictly in "dd/MM/yyyy HH:mm:ss" format)
 *
 */
public class TransManagementApp 
{
    public static void main( String[] args )
    {
		if (args.length != 4) {
			System.err.println("Invalid number of arguments. Please supply (i) path/to/logfile (ii) accountId (iii) start date and (iv) end date. Aborting...");
			return;
		}
		
		String logFile = args[0];
		String accountId = args[1];
		Date start = null;
		Date end = null;
		
		try {
			start = DateUtils.fromString(args[2]);
			end = DateUtils.fromString(args[3]);
		} catch (ParseException e) {
			System.err.println("Invalid date format. Please use 'dd/MM/yyyy HH:mm:ss' format");
		}
		
		TransactionManager transManager = new TransactionManager(logFile);
		
		StringBuilder relativeBal = new StringBuilder("Relative balance for account ");
		relativeBal.append(accountId)
				   .append(" from ")
				   .append(args[2])
				   .append(" to ")
				   .append(args[3])
				   .append(" is: ")
				   .append(transManager.getRelativeBalance(accountId, start, end));
		
		System.out.println(relativeBal.toString());
		System.out.println("Number of transactions included is:" + transManager.getAllTransactionByAccount(accountId, start, end).size());
    }
}
