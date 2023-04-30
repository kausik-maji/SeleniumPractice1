package Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import LibraryFiles.ReusableLibraryFile;

public class TestListeners implements ITestListener, ISuiteListener {
	
	public static List<ITestNGMethod> FailedTests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> PassedTests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> SkippedTests = new ArrayList<ITestNGMethod>();
	public static Date startDate;
	public static Date EndDate; 
	//This is just a comment


	public void onTestSuccess(ITestResult result) {
		System.out.println("Testcase passed is :"+result.getName());
		try {
			new ReusableLibraryFile().TakeScreenShot(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		PassedTests.add(result.getMethod());
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Taking failure screenshot");
		try {
			new ReusableLibraryFile().TakeScreenShot(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FailedTests.add(result.getMethod());
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("testcase skipped at screenshot");
		try {
			new ReusableLibraryFile().TakeScreenShot(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SkippedTests.add(result.getMethod());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		EndDate = context.getEndDate();
		
	}
	
	public void onStart(ITestContext context) {
		startDate = context.getStartDate();
		
	}

	public void onStart(ISuite suite) {
	}

	public void onFinish(ISuite suite) {
		//ReusableLibraryFile.driver.quit();
		System.out.println("========Test Execution Completed========");	
	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}
}
