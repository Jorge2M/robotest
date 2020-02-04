package com.mng.testmaker.testreports.html;

import java.util.ArrayList;
import java.util.List;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;

public class GetterTreeList {

    enum TypeLine {TestRun, TestCase, Step, Validation}
    
    private final SuiteTM suite;
    private final List<Integer> mapTree;
    
    public GetterTreeList(SuiteTM suite) {
    	this.suite = suite;
    	this.mapTree = getMapTree();
    }
    
    private List<Integer> getMapTree() {
    	List<Integer> listMapReturn = new ArrayList<>();
    	for (TestRunTM testRun : suite.getListTestRuns()) {
    		listMapReturn.add(0);
    		int posLastTestRun = listMapReturn.size();
    		for (TestCaseTM testCase : testRun.getListTestCases()) {
    			listMapReturn.add(posLastTestRun);
        		int posLastTest = listMapReturn.size();
    			for (StepTM step : testCase.getStepsList()) {
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
