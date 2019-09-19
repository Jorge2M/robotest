package com.mng.robotest.test80.mango.test.suites;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.xml.XmlSuite.ParallelMode;

import static com.mng.robotest.test80.mango.test.suites.SuiteMakerResources.getParametersSuiteShop;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.arq.xmlprogram.TestRunMaker;

public class NodosSuite extends SuiteMaker {

    public NodosSuite(InputParams inputParams) throws Exception {
    	super(inputParams);
    	setParameters(getParametersSuiteShop(inputParams));
    	addParameters(getSpecificParameters(inputParams.getUrlBase()));
    	TestRunMaker testRun = TestRunMaker.getNew(inputParams.getSuiteName(), getClasses());
    	addTestRun(testRun);
    	setParallelMode(ParallelMode.METHODS);
    	setThreadCount(3);
    }
    
    private static List<String> getClasses() {
    	return Arrays.asList("com.mng.robotest.test80.mango.test.factoryes.ListAllNodes");
    }
	
    private Map<String,String> getSpecificParameters(String urlBase) throws Exception {
    	Map<String,String> params = new HashMap<>();
    	params.put("url-status", createURLfromBase(urlBase, "/controles/status"));
    	params.put("url-errorpage", createURLfromBase(urlBase, "/errorPage.faces"));
    	params.put("testLinksPie", "false"); 
    	return params;
    }
    
    private String createURLfromBase(String baseURL, String path) throws Exception {
        URI uriBase = new URI(baseURL);
        return uriBase.getScheme() + "://" + uriBase.getHost() + path;
    }
}
