package com.n26.code.challenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.n26.code.challenge.bean.Statistics;
import com.n26.code.challenge.bean.Transaction;
import com.n26.code.challenge.dao.TransactionDAO;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {
	
	private static final String URL = "http://localhost:8080/n26/";
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	TransactionDAO transDAO;
	
	Transaction transaction;
	
	@Before
    public void setUp() throws Exception {
		
		transaction = new Transaction();
		transaction.setAmount(20.0);
		transaction.setTimestamp(1497261432000L);
		
    }
	
	@Test
	public void testAddValidTransaction() throws Exception{
		
		when(transDAO.saveTransacation(any(Transaction.class))).thenReturn(true);
		
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL + "transactions")
								  .contentType(MediaType.APPLICATION_JSON_UTF8)
						          .accept(MediaType.APPLICATION_JSON_UTF8)
						          .content(TestUtils.objectToJson(transaction))).andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.CREATED.value(), status);
		
		// verify that service method was called once
		verify(transDAO).saveTransacation(any(Transaction.class));
		
		int res = result.getResponse().getStatus();
		assertNotNull(res);
		assertEquals(201, res);

	}
	
	
	
	@Test
	public void testAddInValidTransaction() throws Exception{
		
		transaction.setTimestamp(1497261431000L);
		when(transDAO.saveTransacation(any(Transaction.class))).thenReturn(false);
		
		// execute
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL + "transactions")
								  .contentType(MediaType.APPLICATION_JSON_UTF8)
						          .accept(MediaType.APPLICATION_JSON_UTF8)
						          .content(TestUtils.objectToJson(transaction))).andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.NO_CONTENT.value(), status);
		
		// verify that service method was called once
		verify(transDAO).saveTransacation(any(Transaction.class));
		
		int res = result.getResponse().getStatus();
		assertNotNull(res);
		assertEquals(204, res);

	}
	
	@Test
	public void testForTransactionStatisticsWithIn60Seconds() throws Exception{
		
		TreeMap<Date,List<Double>> unfilteredList = new TreeMap<>();
		
		Date d1 = new Date();
		List<Double> l1 = new ArrayList<>();
		
		l1.add(10.0);
		l1.add(25.0);
		
		unfilteredList.put(d1, l1);
		
		when(transDAO.getAllTransaction()).thenReturn(unfilteredList);
		
		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "statistics")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.OK.value(), status);
	 
		// verify that service method was called once
		 verify(transDAO).getAllTransaction();
	 
		 Statistics statsitics = TestUtils.jsonToObject(result.getResponse()
												              .getContentAsString(), 
												               Statistics.class);
		 
		 assertNotNull(statsitics);
		 
		 assertEquals(25.0, statsitics.getMax(),0.05);
		 assertEquals(10.0, statsitics.getMin(),0.05);
		 assertEquals(35.0, statsitics.getSum(),0.05);
		 assertEquals(17.5, statsitics.getAvg(),0.05);
		 assertEquals(2, statsitics.getCount(),0.05);
	}
	
	@Test
	public void testForTransactionStatisticsLessThan60Seconds() throws Exception{
		
		TreeMap<Date,List<Double>> unfilteredList = new TreeMap<>();
		
		Date d1 = new Date();
		List<Double> l1 = new ArrayList<>();
		
		l1.add(10.0);
		l1.add(25.0);
		
		Date d2 = new Date(d1.getTime() - 5 * 60 * 1000);
		
		unfilteredList.put(d2, l1);
		
		when(transDAO.getAllTransaction()).thenReturn(unfilteredList);
		
		// execute
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URL + "statistics")
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andReturn();
		
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Response Status", HttpStatus.OK.value(), status);
	 
		// verify that service method was called once
		 verify(transDAO).getAllTransaction();
	 
		 Statistics statsitics = TestUtils.jsonToObject(result.getResponse()
												              .getContentAsString(), 
												               Statistics.class);
		 
		 assertNotNull(statsitics);
		 
		 assertEquals(0.0, statsitics.getMax(),0.05);
		 assertEquals(0.0, statsitics.getMin(),0.05);
		 assertEquals(0.0, statsitics.getSum(),0.05);
		 assertEquals(0.0, statsitics.getAvg(),0.05);
		 assertEquals(0, statsitics.getCount(),0.05);
	}
	

}
