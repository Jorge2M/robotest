package com.mng.robotest.test80.mango.test.suites;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;
import java.util.Arrays;
import java.util.List;
import org.testng.xml.XmlSuite.ParallelMode;

import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class MenusMantoSuite extends SuiteMaker {

    public MenusMantoSuite(ParamsBean params) {
    	super(params.getInputDataTestMaker());
    	setParameters(getParametersSuiteShop(params));
    	TestRunMaker testRun = TestRunMaker.getNew(params.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(4);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ListMenusManto");
    }
}
