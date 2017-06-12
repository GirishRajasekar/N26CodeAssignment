# N26CodeAssignment

Problem Statement

We would like to have a restful API for our statistics. The main usecase for our API is to calculate

realtime statistic from the last 60 seconds. There will be two APIs, one of them is called everytime a

transaction is made. It is also the sole input of this rest API. The other one returns the statistic

based of the transactions of the last 60 seconds.

Specs

POST /transactions

Everytime a new transaction happened, this endpoint will be called.

Body:

{

"amount": 12.3,

"timestamp": 1478192204000

}

Where: * amount - transaction amount * timestamp - transaction time in epoch in millis in UTC time

zone (this is not current timestamp)

Returns: Empty body with either 201 or 204. * 201 - in case of success * 204 - if transaction is older

than 60 seconds

where:

● amount is a double specifying the amount

● time is a long specifiying unix timeformat in milleseconds

GET /statistics

This is the main endpoint of this task, this endpoint have to execute in constant time and memory

(O(1)). It returns the statistic based on the transactions which happened in the last 60 seconds.

Returns:

{

"sum": 1000,

"avg": 100,

"max": 200,

"min": 50,

"count": 10

}

where:

● sum is a double specifiying the total sum of transaction value in the last 60 seconds

● avg is a double specifiying the average amount of transaction value in the last 60 seconds

● max is a double specifiying single highest transaction value in the last 60 seconds

● min is a double specifiying single lowest transaction value in the last 60 seconds

● count is a long specifiying the total number of transactions happened in the last 60 seconds

Requirements

● Constant time and memory O(1) for both GET /statiscics and POST /transactions.

● Code must be submitted via github or bitbucket.

● The API has to be threadsafe with concurrent requests.

● The API has to function properly, with proper result.

● The project should be buildable, and tests should also complete succesfully. e.g. If maven is used,

then mvn clean install should complete successfully.

● The API should be able to deal with time discrepancy, which means, at any point of time, we could

receive a transaction which have a timestamp in the past.

● We will run automated tests, so please follow the API specification (payloads and endpoints).

Solution

I have created Maven Based Spring Boot restfull services using Java 1.8 and Eclipse IDE.

Application Walkthrough

     1) I have created  StatisticsController Class which holds all the restfull service.
	   2) I have created the TransactionDAO Class whic currently will hold all the data in-memeory.
	   3) I have created Transaction bean which holds the transaction details which wil be added.
     4) I have created Statistics bean which will hold the statistics details of the transaction.
	   5) I have created CodeChallengeApplication class which has the main class for springBoot.
	   6) I have also added JUnit test cases.
	   7) I have added Logger.
   
   
    
    
