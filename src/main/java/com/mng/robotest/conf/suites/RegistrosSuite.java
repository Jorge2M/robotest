package com.mng.robotest.conf.suites;

import static com.mng.robotest.conf.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.ArrayList;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.github.jorge2m.testmaker.domain.SuiteMaker;
import com.github.jorge2m.testmaker.domain.TestRunMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conf.ErrorStorer;
import com.mng.robotest.conf.factories.ListRegistrosXPais;

public class RegistrosSuite extends SuiteMaker {
	
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
	
	public RegistrosSuite(InputParamsMango inputParams) {
		super(inputParams);
		setParameters(getParametersSuiteShop(inputParams));
		TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ListRegistrosXPais.class);
		testRun.setStorerErrorStep(new ErrorStorer());
		testRun.addGroups(getSpecificGroups());
		addTestRun(testRun);
		setParallelMode(ParallelMode.METHODS);
		setThreadCount(5);
	}
	
	private List<String> getSpecificGroups() {
		List<String> listReturn = new ArrayList<>();
		listReturn.add("SupportsFactoryCountrys");
		return listReturn;
	}
}
