package com.n26.code.challenge.util;

import java.util.Date;
import java.util.Stack;

import com.n26.code.challenge.bean.TransactionVO;

public class StatisticsUtil {
	
	
	/**
	 * Method will return transaction within 60 seconds
	 * 
	 * @param unFilteredmap
	 * @return filteredTransMap
	 */
	public static Stack<Double> getTransactionWithInTimeFrame(Stack<TransactionVO> unFilteredStack){
		
		Stack<Double> filteredStack  = new Stack<>();
		
		if(unFilteredStack!=null && unFilteredStack.size() > 0){
			
			//filter the stack which has transaction within 60 seconds
			for(TransactionVO transVO : unFilteredStack){
				
				if(transVO.getTimestamp().after(getCurrentTimeLessThanMinute())){
					
					filteredStack.push(transVO.getAmount());
					
				}else{
					break;
				}
			}
		 }
		
		return filteredStack;
	}
	
	/**
	 * Method will return current time with less than 1 minute
	 * 
	 * @return startTime
	 */
	public static Date getCurrentTimeLessThanMinute(){
		
		//get the current time
		Date now  = new Date();
		
		//minus 1 minute i.e 60 seconds
		Date startTime = new Date(now.getTime() - (1 * 60 * 1000));
		
		return startTime;
	}
}
