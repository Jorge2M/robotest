package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;
import com.mng.robotest.test80.mango.test.factoryes.LineasBannersFactory;

public class PaisIdiomaSuite extends SuiteMaker {

	public enum VersionPaisSuite implements FlagsNaviationLineas {
		V1(true, false, false, false),
		V2(true, true, false, false),
		V3(true, false, true, false),
		V4(true, false, false, true),
		V5(true, false, true, true);
		
		boolean testLineas;
		boolean testBanners;
		boolean testMenus;
		boolean testOrderAndTranslationMenus;
		private VersionPaisSuite(
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
	
    public PaisIdiomaSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.ofClass(inputParams.getSuiteName(), LineasBannersFactory.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
}
