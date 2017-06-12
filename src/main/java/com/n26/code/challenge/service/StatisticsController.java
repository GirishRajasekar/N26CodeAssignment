package com.n26.code.challenge.service;

import java.util.Stack;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.code.challenge.bean.Statistics;
import com.n26.code.challenge.bean.Transaction;
import com.n26.code.challenge.dao.TransactionDAO;
import com.n26.code.challenge.util.StatisticsUtil;

@RestController
@RequestMapping("/n26")

public class StatisticsController {
	
	private static final Logger logger = Logger.getLogger(StatisticsController.class);
	
	
	@Autowired
	private TransactionDAO statsDAO;
	
	
	/**
	 * Method will get the transaction from the user and add to the storage.
	 * 
	 * @return stats
	 */
	@RequestMapping(value="/transactions",method =RequestMethod.POST)
	public void addTransaction(@RequestBody Transaction transaction, HttpServletResponse response){
		
		logger.debug("In StatisticsController.addTransaction() Method -- Start");
		
		//Call method to save the transaction
		boolean saveStatus = statsDAO.saveTransacation(transaction);
		
		//Check if save is sucessfull
		if(saveStatus){
			
			//Setting 201 status 
			response.setStatus(HttpServletResponse.SC_CREATED);
			
		}else{
			
			//Setting 204 status 
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}
		
		logger.debug("In StatisticsController.addTransaction() Method -- End");
	}
	
	
	/**
	 * Method will return last 60 seconds transactions.
	 * 
	 * @return stats
	 */
	@RequestMapping(value="/statistics", 
					method=RequestMethod.GET, 
					produces="application/json")
	
	public Statistics getStatistics(){
		
		logger.debug("In StatisticsController.getStatistics() Method -- Start");
		
		//Filter the map to get last 60 seconds transaction data
		Stack<Double> transAmtInStack = StatisticsUtil.getTransactionWithInTimeFrame(statsDAO.getAllTransactionData());
		
		//Call the method to get the transaction statistics
		Statistics transStatistics = StatisticsHelper.getTransactionStats(transAmtInStack);
		
		logger.debug("In StatisticsController.getStatistics() Method -- End");
		
		return transStatistics;
		
	}
	
}
