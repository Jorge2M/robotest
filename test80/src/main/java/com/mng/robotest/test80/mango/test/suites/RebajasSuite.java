package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class RebajasSuite extends SuiteMaker {

    public RebajasSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
    	testRun.addGroups(getSpecificGroups());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ListRebajasXPais");
    }
    
    private List<String> getSpecificGroups() {
    	List<String> listReturn = new ArrayList<>();
    	listReturn.add("SupportsFactoryCountrys");
        return listReturn;
    }
}
