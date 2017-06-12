package com.n26.code.challenge.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.n26.code.challenge.bean.Transaction;
import com.n26.code.challenge.util.StatisticsUtil;


/**
 * This class is the DAO class which stores the data in memory for this application
 * @author Girish
 *
 */
@Component
public class TransactionDAO {
	
	TreeMap<Date,List<Double>> transactionMap = new TreeMap<>();
	
	/**
	 * Method to add the transaction to the map
	 * 
	 * @param trans
	 */
	public boolean saveTransacation(Transaction trans){
		
		boolean saved = false;
		//Create a lock object
		Lock lock = new ReentrantLock();
		
		//lock for concurrent users
		lock.lock();
		
		if(trans!=null && trans.getTimestamp() > 0){
			
			//Convert epoch timestamp to java date object
			Date transDate = new Date(trans.getTimestamp());
			
			//get the currenttime less than 60 seconds
			Date startTime = StatisticsUtil.getCurrentTimeLessThanMinute();
			
			//check if the transaction date is within 60 seconds
			if(transDate.after(startTime)){
			
				List<Double> transactionAmtList = null;
				
				//check with the same transaction date if vlaue is present
				if(transactionMap.get(transDate)!=null){
					
					//if its already there get the exisiting list
					transactionAmtList = transactionMap.get(transDate);
					
				}else{
					
					//create a new list
					transactionAmtList = new ArrayList<>();
				}
				
				//add amount to list
				transactionAmtList.add(trans.getAmount());
				
				//add to map
				transactionMap.put(transDate, transactionAmtList);
				
				
				saved = true;
			}
		}
		
		//release the lock
		lock.unlock();
		
		//return the status
		return saved;
	}
	
	/**
	 * Method will return the stored the transactions data
	 * @return
	 */
	public TreeMap<Date,List<Double>> getAllTransaction(){
		return transactionMap;
	}
}
