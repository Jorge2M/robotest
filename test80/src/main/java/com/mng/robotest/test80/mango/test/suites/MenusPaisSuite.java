package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParamsMango;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.mango.conftestmaker.StorerErrorDataStepValidationMango;
import com.mng.robotest.test80.mango.test.factoryes.MenusFactory;

public class MenusPaisSuite extends SuiteMaker {

	public enum VersionMenusPais implements FlagsNaviationLineas {
		V1(true, false, true, false),
		V2(true, false, false, true),
		V3(true, false, true, true);
		
		boolean testLineas;
		boolean testBanners;
		boolean testMenus;
		boolean testOrderAndTranslationMenus;
		private VersionMenusPais(
				boolean testLineas, boolean testBanners, boolean testMenus, boolean testOrderAndTranslationMenus) {
			this.testLineas = testLineas;
			this.testBanners = testBanners;
			this.testMenus = testMenus;
			this.testOrderAndTranslationMenus = testOrderAndTranslationMenus;
		}
		
		public boolean testLineas() {return testLineas;}
		public boolean testBanners() {return testBanners;}
		public boolean testMenus() {return testMenus;}
		public boolean testOrderAndTranslationMenus() {return testOrderAndTranslationMenus;}
	}
	
    public MenusPaisSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), MenusFactory.class);
    	testRun.setStorerErrorStep(new StorerErrorDataStepValidationMango());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}
