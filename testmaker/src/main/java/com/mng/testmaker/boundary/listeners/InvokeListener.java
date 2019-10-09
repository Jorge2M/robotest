package com.mng.testmaker.boundary.listeners;

import java.net.HttpURLConnection;
import org.testng.*;

import com.mng.testmaker.domain.StateRun;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;
import com.mng.testmaker.utils.controlTest.FmwkTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InvokeListener extends TestListenerAdapter implements ISuiteListener {
	
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);

    HttpURLConnection httpUrlCallBack = null;
  
    @Override //Start Suite 
    public void onStart(ISuite suite) {
    }
    
    @Override //End Suite
    public void onFinish(ISuite suite) {
    	((SuiteTestMaker)suite).end();
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
    	getTestRun(result).setState(StateRun.Finished);
    }
  
    @Override //End Method Skipped
    public void onTestSkipped(ITestResult result) {
    	getTestRun(result).setState(StateRun.Finished);
    }
  
    @Override //End Method Failure
    public void onTestFailure(ITestResult result) {
        pLogger.error("Exception for TestNG", result.getThrowable());
    	getTestRun(result).setState(StateRun.Finished);
    }
    
    private TestRunTestMaker getTestRun(ITestResult result) {
    	return ((TestRunTestMaker)result.getTestContext().getCurrentXmlTest());
    }
}