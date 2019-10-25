package com.mng.testmaker.testreports.html;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;

public class GenerateReportsTest {

	@Test
	public void testGetMapTree() {
		//Given
		SuiteTM suite = Mockito.mock(SuiteTM.class);
		TestRunTM testRun1 = Mockito.mock(TestRunTM.class);
		TestCaseTM testCase1 = Mockito.mock(TestCaseTM.class);
		StepTM step1 = Mockito.mock(StepTM.class);
		StepTM step2 = Mockito.mock(StepTM.class);
		ChecksResult val1 = Mockito.mock(ChecksResult.class);
		ChecksResult val2 = Mockito.mock(ChecksResult.class);
		TestCaseTM testCase2 = Mockito.mock(TestCaseTM.class);
		StepTM step3 = Mockito.mock(StepTM.class);
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
