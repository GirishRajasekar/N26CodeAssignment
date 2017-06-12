package com.n26.code.challenge.service;

import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

import com.n26.code.challenge.bean.Statistics;

public class StatisticsHelper {

	/**
	 * Method will return the statistics for given transactions
	 * 
	 * @param statsMap
	 * @return stats
	 */
	public static Statistics getTransactionStats(SortedMap<Date,List<Double>> statsMap){
		
		Statistics stats = new Statistics();
		
		if(statsMap!=null && statsMap.size() > 0){
			
			//fetch the list of values from the map
			List<Double> transAmtList = statsMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
			
			DoubleSummaryStatistics summaryStatistics = transAmtList.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
			
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
