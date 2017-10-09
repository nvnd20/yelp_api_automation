
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//Main class which runs test cases
public class YelpAPITestMain {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(YelpSearchAPIPositiveTest.class, YelpSearchAPINegativeTest.class);
		System.out.println("------------- Yelp Search API Test Cases Result -------------");
		System.out.println("Total number of test cases executed:" + result.getRunCount());
		System.out.println("Total number time:" + result.getRunTime()+"ms");
		System.out.println("Total number of test cases failed:" + result.getFailureCount());

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println("This is successfull:" + result.wasSuccessful());
		System.out.println("Total number of test cases ignored:" + result.getIgnoreCount());
		System.out.println("-------------------------------------------------------------");
	}

}
