package com.n26.code.challenge.util;

import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class StatisticsUtil {
	
	/**
	 * Method will return transaction within 60 seconds
	 * 
	 * @param unFilteredmap
	 * @return filteredTransMap
	 */
	public static SortedMap<Date,List<Double>> getTimeBasedTransaction(TreeMap<Date,List<Double>> unFilteredmap){
		
		SortedMap<Date,List<Double>> filteredTransMap  = new TreeMap<>();
		
		if(unFilteredmap!=null && unFilteredmap.size() > 0){
			
			filteredTransMap = unFilteredmap.tailMap(getCurrentTimeLessThanMinute());
		}
		
		return filteredTransMap;
	}
	
	/**
	 * Method will return current time with less than 1 minute
	 * 
	 * @return startTime
	 */
	public static Date getCurrentTimeLessThanMinute(){
		
		Date now  = new Date();
		Date startTime = new Date(now.getTime() - (1 * 60 * 1000));
		
		return startTime;
	}
}
