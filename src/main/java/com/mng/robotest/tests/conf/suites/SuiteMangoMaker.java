package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.isBrowserStack;
import static org.testng.xml.XmlSuite.ParallelMode.METHODS;
import static com.mng.robotest.testslegacy.data.Constantes.BSTACK_PARALLEL;

import java.util.List;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;

public abstract class SuiteMangoMaker extends SuiteMakerMango {

	abstract List<Class<?>> getClasses();
	
	protected SuiteMangoMaker(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(),	getClasses());
		
		//testRun.setDriverMaker(new MyDriverMaker());
		addTestRunMango(testRun);
		
		setParallelMode(METHODS);
		if (!isBrowserStack(inputParams.getDriver())) {
			setThreadCount(4); 
		} else {
			setThreadCount(BSTACK_PARALLEL);
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
