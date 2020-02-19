package com.mng.testmaker.boundary.listeners;

import org.testng.*;
import org.testng.annotations.*;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.TestCaseParams;
import com.mng.testmaker.domain.suitetree.SuiteTM;

import java.lang.reflect.*;
import java.util.List;


public class MyTransformer implements IAnnotationTransformer {

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		SuiteTM suite = getSuiteOfTest();
		TestCaseParams testCaseData = suite.getInputParams().getTestCaseParams(testMethod.getName());
		if (testCaseData!=null) {
			Integer invocationCount = testCaseData.getInvocationCount(); 
			if (invocationCount!=null) {
				annotation.setInvocationCount(invocationCount);	
			}
			Integer threadPoolSize = testCaseData.getThreadPoolSize();
			if (threadPoolSize!=null) {
				annotation.setThreadPoolSize(threadPoolSize);
			}
		}	
	}
	
	private SuiteTM getSuiteOfTest() {
		Long threadId = Thread.currentThread().getId();
		List<SuiteTM> listSuites = SuitesExecuted.getSuitesExecuted();
		for (SuiteTM suite : listSuites) {
			if (threadId==suite.getThreadId()) {
				return suite;
			}
		}
		Log4jConfig.pLogger.warn("Not found Suite associated");
		return listSuites.get(0);
	}
}
