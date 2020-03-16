package com.mng.testmaker.boundary.aspects.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.io.Serializable;

import com.mng.testmaker.boundary.aspects.test.remote.RemoteTest;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.TestMaker;


@Aspect
public class TestAspect {

	@Pointcut("@annotation(org.testng.annotations.Test)")
	public void annotationTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationTestPointcut() && atExecution()")
	public Object aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundTest(joinPoint);
	}
	
	private Object manageAroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		if (testCase!=null) {
			TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		}
		
		Object returnValue = null;
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		if (!inputParams.isRemote()) {
			RemoteTest remoteTest = new RemoteTest();
			remoteTest.execute(testCase, inputParams, (Serializable)joinPoint.getTarget());
		} else {
			testCase.makeWebDriver();
			returnValue = joinPoint.proceed();
		}
		
		return returnValue;
	}
}
