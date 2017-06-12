package com.n26.code.challenge.dao;

import java.util.Date;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.n26.code.challenge.bean.Transaction;
import com.n26.code.challenge.bean.TransactionVO;
import com.n26.code.challenge.util.StatisticsUtil;


/**
 * This class is the DAO class which stores the data in memory for this application
 * @author Girish
 *
 */
@Component
public class TransactionDAO {
	
	
	Stack<TransactionVO> transDetailsStack = new Stack<>();
	
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
			
				TransactionVO transactionVO = new TransactionVO();
				
				transactionVO.setAmount(trans.getAmount());
				transactionVO.setTimestamp(transDate);
				
				transDetailsStack.push(transactionVO);
				
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
	 * 
	 * @return transDetailsStack
	 */
	public Stack<TransactionVO> getAllTransactionData(){
		return transDetailsStack;
	}
}
