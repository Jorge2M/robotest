package com.mng.testmaker.boundary.listeners;

import java.net.HttpURLConnection;
import org.testng.*;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;


public class InvokeListener extends TestListenerAdapter implements ISuiteListener {
	
    HttpURLConnection httpUrlCallBack = null;
  
    @Override //Start Suite 
    public void onStart(ISuite suite) {
    }
    
    @Override //End Suite
    public void onFinish(ISuite suite) {
    	((SuiteTestMaker)suite.getXmlSuite()).end();
    }
    
    @Override //Start TestRun
    public synchronized void onStart(ITestContext testNgContext) {
    	//Inyectamos el ITestContext en el TestRun
    	TestRunTestMaker testRun = (TestRunTestMaker)testNgContext.getCurrentXmlTest();
    	testRun.setTestNgContext(testNgContext);
    }
  
    @Override //End TestRun
    public void onFinish(ITestContext testContext) {
    	TestRunTestMaker testRun = (TestRunTestMaker)testContext.getCurrentXmlTest();
    	testRun.end();
    }

    @Override //Start Method
    public void onTestStart(ITestResult result) {
    	TestRunTestMaker testRun = getTestRun(result);
    	TestCaseTestMaker testCase = new TestCaseTestMaker(result);
    	testRun.addTestCase(testCase);
    }
  
    @Override //End Method Success
    public void onTestSuccess(ITestResult result) {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCase(result);
    	testCase.end();
    }
  
    @Override //End Method Skipped
    public void onTestSkipped(ITestResult result) {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCase(result);
    	testCase.end(State.Skip);
    }
  
    @Override //End Method Failure
    public void onTestFailure(ITestResult result) {
        Log4jConfig.pLogger.error("Exception for TestNG", result.getThrowable());
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCase(result);
    	testCase.end(State.Nok);
    }
    
    private TestRunTestMaker getTestRun(ITestResult result) {
    	return ((TestRunTestMaker)result.getTestContext().getCurrentXmlTest());
    }
}