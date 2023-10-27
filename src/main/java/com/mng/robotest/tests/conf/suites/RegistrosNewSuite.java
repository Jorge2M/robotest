package com.mng.robotest.tests.conf.suites;

import static com.mng.robotest.tests.conf.suites.SuiteMakerResources.getParametersSuiteShop;
import static org.testng.xml.XmlSuite.ParallelMode.METHODS;

import java.util.ArrayList;
import java.util.List;

import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.factories.ListRegistrosNewXPais;

public class RegistrosNewSuite extends SuiteMakerMango {
	
	public enum VersionRegistroSuite {
		V1(false, false),
		V2(true, false),
		V3(true, true);
		
		boolean register;
		boolean loginAfterRegister;
		private VersionRegistroSuite(boolean register, boolean loginAfterRegister) {
			this.register = register;
			this.loginAfterRegister = loginAfterRegister;
		}
		
		public boolean register() {return register;}
		public boolean loginAfterRegister() {return loginAfterRegister;}
	}
	
	public RegistrosNewSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		var testRun = TestRunMaker.from(inputParams.getSuiteName(), ListRegistrosNewXPais.class);
		testRun.addGroups(getSpecificGroups());
		addTestRunMango(testRun);
		setParallelMode(METHODS);
		setThreadCount(5);
	}
	
	private List<String> getSpecificGroups() {
		List<String> listReturn = new ArrayList<>();
		listReturn.add("SupportsFactoryCountrys");
		return listReturn;
	}
}
