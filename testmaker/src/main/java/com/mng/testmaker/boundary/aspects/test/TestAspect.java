package com.mng.testmaker.boundary.aspects.test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.ServerSubscribers;
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
		
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		if (!inputParams.isTestExecutingInRemote() && 
			ServerSubscribers.isSome()) {
			ServerSubscribers.sendTestToRemoteServer(testCase, joinPoint.getTarget());
			//TODO si un @Test retorna un valor <> de void tendremos problemas. Se debería serializar el objeto de respuesta
			return null;
		} else {
			return executeTest(testCase, joinPoint);
		}
	}
	
	private Object executeTest(TestCaseTM testCase, ProceedingJoinPoint joinPoint) throws Throwable {
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		Method presentMethod = ((MethodSignature)joinPoint.getSignature()).getMethod();
		List<String> listTestCaseFilter = inputParams.getListTestCasesName();
		if (!inputParams.isTestExecutingInRemote() || 
			listTestCaseFilter.size()==0 ||
			presentMethod.getName().compareTo(listTestCaseFilter.get(0))==0) {
			testCase.makeWebDriver();
			return joinPoint.proceed();
		}
		return null;
	}
	
}
