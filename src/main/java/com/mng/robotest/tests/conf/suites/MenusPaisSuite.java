package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static org.testng.xml.XmlSuite.ParallelMode.*;

import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.MenusFactory;

public class MenusPaisSuite extends SuiteMakerMango {

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
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), MenusFactory.class);
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		setThreadCount(3);
	}
}
