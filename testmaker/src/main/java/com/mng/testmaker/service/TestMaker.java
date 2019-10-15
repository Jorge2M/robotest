package com.mng.testmaker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.domain.StateRun;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.jdbc.Connector;
import com.mng.testmaker.listeners.utils.ResourcesExtractor;
import com.mng.testmaker.utils.controlTest.FmwkTest;

public class TestMaker {

	//TODO
	//generateReport <- Esta debe estar disponible coo función de la Suite
	//generateCorreoReport
	
    public static void run(SuiteTestMaker suite) { 
    	suite.setState(StateRun.Started);
    	runInTestMaker(suite);
    	runInTestNG(suite);
    }
    
	public static void finish(SuiteTestMaker suite) {
		suite.end();
	}
	
	public static SuiteTestMaker getSuite(String idExecution) {
		return SuitesExecuted.getSuite(idExecution);
	}
	
    public static void skipTestsIfSuiteStopped() {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInThread();
    	if (testCase!=null) {
    		skipTestsIfSuiteStopped(testCase.getSuiteParent());
        }
    }
	
    public static void skipTestsIfSuiteStopped(SuiteTestMaker suite) {
        if (suite.getState()==StateRun.Stopping) {
            throw new SkipException("Received Signal for stop TestSuite" + suite.getName());
        }
    }
    
    public WebDriver getDriverTestCase() {
    	return (TestCaseTestMaker.getTestCaseInThread().getWebDriver());
    }
	
    private static void runInTestMaker(SuiteTestMaker suite) {
        File path = new File(suite.getDirectory());
        path.mkdir();
        FmwkTest.configLog4java(suite.getDirectory());
        grabSqliteBDifNotExists();
    	SuitesExecuted.add(suite);
    }
    
    private static void runInTestNG(SuiteTestMaker suite) {
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }
    
    private static void grabSqliteBDifNotExists() {
        ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
        File fileSqliteBD = new File(Connector.getSQLiteFilePathAutomaticTestingSchema());
        if (!fileSqliteBD.exists()) {
	        resExtractor.copyDirectoryResources(
	        	"sqlite/", 
	            Connector.getSQLitePathDirectory());
        }
    }
}
