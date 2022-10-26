package com.mng.robotest.test.suites;

import static com.mng.robotest.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.io.Serializable;

import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.ErrorStorer;
import com.mng.robotest.test.factoryes.LineasBannersFactory;

public class PaisIdiomaSuite extends SuiteMaker implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
	public PaisIdiomaSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), LineasBannersFactory.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(3);
	}
}
