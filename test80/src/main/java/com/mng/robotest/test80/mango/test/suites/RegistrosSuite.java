package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

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
	
    public RegistrosSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(getParametersSuiteShop(params));
    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
    	testRun.addGroups(getSpecificGroups());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
        setThreadCount(5);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ListRegistrosXPais");
    }
	
    private List<String> getSpecificGroups() {
    	List<String> listReturn = new ArrayList<>();
    	listReturn.add("SupportsFactoryCountrys");
        return listReturn;
    }
}
