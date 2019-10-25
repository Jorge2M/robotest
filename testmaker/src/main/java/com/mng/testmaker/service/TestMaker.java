package com.mng.testmaker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;

public class TestMaker {

	//TODO
	//generateReport <- Esta debe estar disponible coo función de la Suite
	//generateCorreoReport
	
    public static void run(SuiteTM suite) {
    	suite.start();
    	runInTestMaker(suite);
    	runInTestNG(suite);
    }
    
    public static void finishSuite(String idExecution) {
    	finish(getSuite(idExecution));
    }
    
	public static void finish(SuiteTM suite) {
		suite.end();
	}
	
	public static SuiteTM getSuite(String idExecution) {
		return SuitesExecuted.getSuite(idExecution);
	}
	
	public static SuiteTM getSuite(ITestContext ctx) {
		return (SuiteTM)ctx.getSuite().getXmlSuite();
	}
	
	public static List<SuiteTM> getListSuites(String suiteName, Channel channel) {
		List<SuiteTM> listSuites = new ArrayList<>();
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			if (suite.getName().compareTo(suiteName)==0 &&
				suite.getInputParams().getChannel()==channel) {
				listSuites.add(suite);
			}
		}
		return listSuites;
	}
	
	public static InputParamsTM getInputParamsSuite(ITestContext ctx) {
		return (InputParamsTM)getSuite(ctx).getInputParams();
	}
	
	public static TestRunTM getTestRun(ITestContext ctx) {
		return (TestRunTM)ctx.getCurrentXmlTest();
	}
	
	public static TestCaseTM getTestCase() {
		return TestCaseTM.getTestCaseInExecution();
	}
	
    public static WebDriver getDriverTestCase() {
    	return (getTestCase().getWebDriver());
    }
    
    public static StepTM getCurrentStepInExecution() {
    	return getTestCase().getCurrentStepInExecution();
    }
    
    public static StepTM getLastStep() {
    	return getTestCase().getLastStep();
    }
    
    public static String getParamTestRun(String idParam, ITestContext ctx) {
    	return ctx.getCurrentXmlTest().getParameter(idParam);
    }
	
    public static void skipTestsIfSuiteStopped() {
    	if (getTestCase()!=null) {
    		skipTestsIfSuiteStopped(getTestCase().getSuiteParent());
        }
    }
	
    public static void skipTestsIfSuiteStopped(SuiteTM suite) {
        if (suite.getStateExecution()==StateExecution.Stopping) {
            throw new SkipException("Received Signal for stop TestSuite" + suite.getName());
        }
    }

    private static void runInTestMaker(SuiteTM suite) {
        File path = new File(suite.getPathDirectory());
        path.mkdir();
        Log4jConfig.configLog4java(suite.getPathDirectory());
    	SuitesExecuted.add(suite);
    }
    
    private static void runInTestNG(SuiteTM suite) {
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        tng.run();
    }

}
