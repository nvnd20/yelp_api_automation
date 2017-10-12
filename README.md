# yelp_api_automation

Automated the basic test cases for the following API
"https://api.yelp.com/v3/businesses/search?"

This project uses okhttp to call rest api. And json library to process response

1.	How to run test case from command line	
You have to execute below command from command line.It is assumed that command prompt/terminal is opened from the directory where ‘yelp_search_api_test.jar’ is kept

	java -jar yelp_search_api_test.jar

2.	How to run it from eclipse
a)	Import the [project as general project in eclipse
b)	To run the test case as java run YelpAPITestMain as java application.
c)	You can run individual test classes (YelpSearchAPINegativeTest and YelpSearchAPIPositiveTest) as junit.

In positive test case, I have mentioned the values which returns the valid response
There are three test case
1)	In this test case I have searched on location.If location is valid then it returns all the business data from that location.
2)	In this test case I have searched for particular type of business in given location so searched using term and location
3)	In this test case I have searched for particular type of business in given location and zip so searched using term, location and zip

All above test cases are asserted on the response.I also asserted on business data from response.

In negative test case, I have mentioned invalid values like empty location,invalid latitude,longitude . For invalid values response is not returning the business data

