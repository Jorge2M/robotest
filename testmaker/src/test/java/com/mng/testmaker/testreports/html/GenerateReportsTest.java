package com.mng.testmaker.testreports.html;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;

public class GenerateReportsTest {

	@Test
	public void testGetMapTree() {
		//Given
		SuiteTestMaker suite = Mockito.mock(SuiteTestMaker.class);
		TestRunTestMaker testRun1 = Mockito.mock(TestRunTestMaker.class);
		TestCaseTestMaker testCase1 = Mockito.mock(TestCaseTestMaker.class);
		StepTestMaker step1 = Mockito.mock(StepTestMaker.class);
		StepTestMaker step2 = Mockito.mock(StepTestMaker.class);
		ChecksResult val1 = Mockito.mock(ChecksResult.class);
		ChecksResult val2 = Mockito.mock(ChecksResult.class);
		TestCaseTestMaker testCase2 = Mockito.mock(TestCaseTestMaker.class);
		StepTestMaker step3 = Mockito.mock(StepTestMaker.class);
		ChecksResult val3 = Mockito.mock(ChecksResult.class);
		
		when(suite.getListTestRuns()).thenReturn(Arrays.asList(testRun1));
		when(testRun1.getListTestCases()).thenReturn(Arrays.asList(testCase1,testCase2));
		when(testCase1.getStepsList()).thenReturn(Arrays.asList(step1,step2));
		when(step2.getListChecksResult()).thenReturn(Arrays.asList(val1,val2));
		when(testCase2.getStepsList()).thenReturn(Arrays.asList(step3));
		when(step3.getListChecksResult()).thenReturn(Arrays.asList(val3));
		
		//When
		List<Integer> mapTree = GenerateReports.getMapTree(suite);
		
		//Then
		String arrayStringExpected = "[0, 1, 2, 2, 4, 4, 1, 7, 8]";
		String arrayStringObtained = mapTree.toString();
		assertTrue(
			"The array string obtained " + arrayStringObtained + " is equals to the expected " + arrayStringExpected,
			arrayStringObtained.compareTo(arrayStringExpected)==0);
	}

}
