package com.mng.testmaker.testreports.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;

public class GetterTreeList {

    enum TypeLine {TestRun, TestCase, Step, Validation}
    
    private final SuiteTestMaker suite;
    private final List<Integer> mapTree;
    
    public GetterTreeList(SuiteTestMaker suite) {
    	this.suite = suite;
    	this.mapTree = getMapTree();
    }
    
    private List<Integer> getMapTree() {
    	List<Integer> listMapReturn = new ArrayList<>();
    	for (TestRunTestMaker testRun : suite.getListTestRuns()) {
    		listMapReturn.add(0);
    		int posLastTestRun = listMapReturn.size();
    		for (TestCaseTestMaker testCase : testRun.getListTestCases()) {
    			listMapReturn.add(posLastTestRun);
        		int posLastTest = listMapReturn.size();
    			for (StepTestMaker step : testCase.getStepsList()) {
    				listMapReturn.add(posLastTest);
            		int posLastStep = listMapReturn.size();
            		for (ChecksResult validation : step.getListChecksResult()) {
        				listMapReturn.add(posLastStep);
            		}
    			}
    		}
    	}
    	return listMapReturn;
    }
}
