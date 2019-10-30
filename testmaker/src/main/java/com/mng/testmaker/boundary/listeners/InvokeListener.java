package com.mng.testmaker.boundary.listeners;

import java.net.HttpURLConnection;
import org.testng.*;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;


public class InvokeListener extends TestListenerAdapter implements ISuiteListener {
	
    HttpURLConnection httpUrlCallBack = null;
  
    @Override //Start Suite 
    public void onStart(ISuite suite) {
    }
    
    @Override //End Suite
    public void onFinish(ISuite suite) {
    	Log4jConfig.pLogger.info("End Suite - INI");
    	((SuiteTM)suite.getXmlSuite()).end();
    	Log4jConfig.pLogger.info("End Suite - FIN");
    }
    
    @Override //Start TestRun
    public synchronized void onStart(ITestContext testNgContext) {
    	//Inyectamos el ITestContext en el TestRun
    	TestRunTM testRun = (TestRunTM)testNgContext.getCurrentXmlTest();
    	testRun.setTestNgContext(testNgContext);
    }
  
    @Override //End TestRun
    public void onFinish(ITestContext testContext) {
    	TestRunTM testRun = (TestRunTM)testContext.getCurrentXmlTest();
    	testRun.end();
    }

    @Override //Start TestCase
    public void onTestStart(ITestResult result) {
    	TestRunTM testRun = getTestRun(result);
    	TestCaseTM testCase = new TestCaseTM(result);
    	testRun.addTestCase(testCase);
    }
  
    @Override //End TestCase Success
    public void onTestSuccess(ITestResult result) {
    	TestCaseTM testCase = TestCaseTM.getTestCase(result);
    	testCase.end();
    }
  
    @Override //End TestCase Skipped
    public void onTestSkipped(ITestResult result) {
    	TestCaseTM testCase = TestCaseTM.getTestCase(result);
    	testCase.end(State.Skip);
    }
  
    @Override //End TestCase Failure
    public void onTestFailure(ITestResult result) {
        Log4jConfig.pLogger.error("Exception for TestNG", result.getThrowable());
    	TestCaseTM testCase = TestCaseTM.getTestCase(result);
    	testCase.end(State.Nok);
    }
    
    private TestRunTM getTestRun(ITestResult result) {
    	return ((TestRunTM)result.getTestContext().getCurrentXmlTest());
    }
}