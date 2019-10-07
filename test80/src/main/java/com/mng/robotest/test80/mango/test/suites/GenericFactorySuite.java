package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.InputParams;
import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.factoryes.GenericFactory;

public class GenericFactorySuite extends SuiteMaker {

    public GenericFactorySuite(InputParams inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.ofClass(inputParams.getSuiteName(), GenericFactory.class);
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
        switch ((Suites)inputParams.getSuite()) {
        case ListFavoritos: 
        	setThreadCount(1);
        	break;
        default:
        	setThreadCount(3);
        }
    }
}
