package com.mng.testmaker.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.ExecutorSuite;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;

public class TestMaker {
	
	public static void run(SuiteTM suite) {
		run(suite, false);
	}
	public static void runAsync(SuiteTM suite) {
		run(suite, true);
	}
    private static void run(SuiteTM suite, boolean async) {
    	suite.start();
        Log4jConfig.configLog4java(suite.getPathDirectory());
        File path = new File(suite.getPathDirectory());
        path.mkdir();
        if (async) {
        	runInTestNgAsync(suite);
        } else {
        	runInTestNgSync(suite);
        }
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
	
	public static SuiteTM execSuite(ExecutorSuite executorSuite) throws Exception {
		return (executorSuite.execTestSuite());
	}
	
	public static SuiteTM execSuiteAsync(ExecutorSuite executorSuite) throws Exception {
		return executorSuite.execTestSuiteAsync();
	}
	
	public static void stopSuite(String idExecSuite) {
		SuiteTM suite = getSuite(idExecSuite);
		stopSuite(suite);
	}
	public static void stopSuite(SuiteTM suite) {
		boolean stopOk = neatStop(suite);
		if (!stopOk) {
			suite.end();
		}
	}
	private static boolean neatStop(SuiteTM suite) {
		suite.setStateExecution(StateExecution.Stopping);
        List<StateExecution> validStates = Arrays.asList(StateExecution.Stopped, StateExecution.Finished);
        return (waitForSuiteInState(suite, validStates, 15));
	}
	private static boolean waitForSuiteInState(SuiteTM suite, List<StateExecution> validStates, int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			StateExecution suiteState = suite.getStateExecution();
			if (validStates.contains(suiteState)) {
				return true;
			}
			sleep(1000);
		}
		return false;
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
    		skipTestsIfSuiteEnded(getTestCase().getSuiteParent());
        }
    }
	
    public static void skipTestsIfSuiteEnded(SuiteTM suite) {
    	List<StateExecution> statesSuiteEnded = Arrays.asList(
    			StateExecution.Stopping, 
    			StateExecution.Stopped, 
    			StateExecution.Finished);
        if (statesSuiteEnded.contains(suite.getStateExecution())) {
            throw new SkipException("Suite " + suite.getName() + " in state " + suite.getStateExecution());
        }
    }
    
    private static void runInTestNgSync(SuiteTM suite) {
    	TestNG tng = makeTestNG(suite);
        tng.run();
    }
    
    private static void runInTestNgAsync(SuiteTM suite) {
    	TestNG tng = makeTestNG(suite);
    	CompletableFuture.runAsync(() -> {
    		tng.run();
    	});
    }
    
    private static TestNG makeTestNG(SuiteTM suite) {
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);    
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setUseDefaultListeners(false);
        return tng;
    }
    
	private static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
			Log4jConfig.pLogger.warn(e);
		}
	}
}
