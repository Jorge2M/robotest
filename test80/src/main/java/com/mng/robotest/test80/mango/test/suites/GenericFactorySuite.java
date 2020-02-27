package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.testmaker.domain.SuiteMaker;
import com.mng.testmaker.domain.TestRunMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.ErrorStorer;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.factoryes.GenericFactory;

public class GenericFactorySuite extends SuiteMaker {

    public GenericFactorySuite(InputParamsMango inputParams) {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	TestRunMaker testRun = TestRunMaker.from(inputParams.getSuiteName(), GenericFactory.class);
    	testRun.setStorerErrorStep(new ErrorStorer());
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
