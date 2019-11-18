package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import java.util.HashMap;
import java.util.Map;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParamsMango;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.mango.test.appshop.ConsolaVotf;

public class ConsolaVotfSuite extends SuiteMaker {

    public ConsolaVotfSuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	addParameters(getSpecificParameters());
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), ConsolaVotf.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(1);
    }
    
    private Map<String,String> getSpecificParameters() {
    	Map<String,String> params = new HashMap<>();
    	params.put("prodDisponible1", "3300049936TN");
    	return params;
    }
}
