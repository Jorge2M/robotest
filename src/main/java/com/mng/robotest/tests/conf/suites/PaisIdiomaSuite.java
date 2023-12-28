package com.mng.robotest.tests.conf.suites;

import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.LineasBannersFactory;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static org.testng.xml.XmlSuite.ParallelMode.METHODS;

public class PaisIdiomaSuite extends SuiteMakerMango {

	public enum VersionPaisSuite implements FlagsNaviationLineas {
		V1(true, false),
		V2(true, true);
		
		boolean testLineas;
		boolean testBanners;
		private VersionPaisSuite(boolean testLineas, boolean testBanners) {
			this.testLineas = testLineas;
			this.testBanners = testBanners;
		}
		
		public boolean testLineas() {return testLineas;}
		public boolean testBanners() {return testBanners;}
	}
	
	public PaisIdiomaSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), 
				LineasBannersFactory.class);
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		setThreadCount(4);
	}
}
