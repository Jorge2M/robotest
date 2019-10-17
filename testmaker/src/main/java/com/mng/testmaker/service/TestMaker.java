package com.mng.testmaker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.StateRun;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;
import com.mng.testmaker.jdbc.Connector;
import com.mng.testmaker.service.testreports.ResourcesExtractor;
import com.mng.testmaker.utils.conf.Log4jConfig;

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
	
	public static SuiteTestMaker getSuiteInExecution() {
		return getTestCase().getSuiteParent();
	}
	
	public static InputParamsTestMaker getInputParamsSuite() {
		return getSuiteInExecution().getInputData();
	}
	
	public static TestCaseTestMaker getTestCase() {
		return TestCaseTestMaker.getTestCaseInExecution();
	}
	
    public static WebDriver getDriverTestCase() {
    	return (getTestCase().getWebDriver());
    }
    
    public static StepTestMaker getCurrentStep() {
    	return getTestCase().getCurrentStep();
    }
    
    public static TestRunTestMaker getTestRun() {
    	return getTestCase().getTestRunParent();
    }
    
    public static String getParamTestRun(String id) {
    	return (getTestCase().getTestRunParent().getParameter(id));
    	//ctx.getCurrentXmlTest().getParameter(Constantes.paramUsrmanto)
    }
	
    public static void skipTestsIfSuiteStopped() {
    	if (getTestCase()!=null) {
    		skipTestsIfSuiteStopped(getTestCase().getSuiteParent());
        }
    }
	
    public static void skipTestsIfSuiteStopped(SuiteTestMaker suite) {
        if (suite.getState()==StateRun.Stopping) {
            throw new SkipException("Received Signal for stop TestSuite" + suite.getName());
        }
    }

    private static void runInTestMaker(SuiteTestMaker suite) {
        File path = new File(suite.getPathDirectory());
        path.mkdir();
        Log4jConfig.configLog4java(suite.getPathDirectory());
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
