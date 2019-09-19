package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class ConsolaVotfSuite extends SuiteMaker {

    public ConsolaVotfSuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	addParameters(getSpecificParameters());
    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(1);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.appshop.ConsolaVotf");
    }
    
    private Map<String,String> getSpecificParameters() {
    	Map<String,String> params = new HashMap<>();
    	params.put("prodDisponible1", "3300049936TN");
    	return params;
    }
}
