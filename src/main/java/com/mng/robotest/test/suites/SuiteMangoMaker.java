package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.test.suites.SuiteMakerResources.isBrowserStack;

import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.data.Constantes;

public abstract class SuiteMangoMaker extends SuiteMaker {

	abstract List<Class<?>> getClasses();
	
	protected SuiteMangoMaker(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(
				inputParams.getSuiteName(), 
				getClasses());
		
		testRun.setStorerErrorStep(new ErrorStorer());
		//testRun.setDriverMaker(new MyDriverMaker());
		addTestRun(testRun);
		
		setParallelMode(ParallelMode.METHODS);
		if (!isBrowserStack(inputParams.getDriver())) {
			setThreadCount(4); 
		} else {
			setThreadCount(Constantes.BSTACK_PARALLEL);
		}
	}
	
//	public static class MyDriverMaker extends DriverMaker {
//	
//	private String driverVersion = "88.0.4324.96";
//	
//	@Override
//	public String getTypeDriver() {
//		return "mychrome";
//	}
//	
//	@Override
//	public void setupDriverVersion(String driverVersion) {
//		//this.driverVersion = driverVersion;
//	}
//	
//	@Override
//	public WebDriver build() {
//		ChromeDriverManager.chromedriver().version(driverVersion).setup();
//		ChromeOptions options = new ChromeOptions();
//		
//		Map<String, Object> mobileEmulation = new HashMap<>();
//		mobileEmulation.put("deviceName", "Nexus 10");
//		options.setExperimentalOption("mobileEmulation", mobileEmulation);
//		
//		//options.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
//		WebDriver driver =  new ChromeDriver(options);
//		return driver;
//	}
//}	

}
