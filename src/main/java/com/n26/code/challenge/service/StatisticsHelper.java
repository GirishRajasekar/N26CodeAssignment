package com.n26.code.challenge.service;

import java.util.DoubleSummaryStatistics;
import java.util.Stack;
import java.util.stream.Collectors;

import com.n26.code.challenge.bean.Statistics;

public class StatisticsHelper {

	
	/**
	 * Method will return the statistics for given transactions
	 * 
	 * @param statsMap
	 * @return stats
	 */
	public static Statistics getTransactionStats(Stack<Double> statsStack){
		
		Statistics stats = new Statistics();
		
		if(statsStack!=null && statsStack.size() > 0){
			
			DoubleSummaryStatistics summaryStatistics = statsStack.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
			
			//set maximum amount in the last 60 seconds transaction
			stats.setMax(summaryStatistics.getMax());
			
			//set minimum amount in the last 60 seconds transaction
			stats.setMin(summaryStatistics.getMin());
			
			//set average amount in the last 60 seconds transaction
			stats.setAvg(summaryStatistics.getAverage());
			
			//set total amount in the last 60 seconds transaction
			stats.setSum(summaryStatistics.getSum());
			
			//set total number of transaction in last 60 seconds
			stats.setCount(summaryStatistics.getCount());
		
		}
		return stats;
	}
}
