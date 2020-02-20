package com.mng.testmaker.boundary.aspects.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.testng.SkipException;
import org.testng.annotations.Factory;

import com.mng.testmaker.domain.InputParamsBasic;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.TestMaker;


@Aspect
public class TestAspect {

	@Pointcut("@annotation(org.testng.annotations.Test)")
	public void annotationTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

//	@Before("annotationTestPointcut() && atExecution()")
//	public void before(JoinPoint joinPoint) {
//		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
//		skipTestIfSuiteEnded(testCase, joinPoint);
//		//TODO El COM010 no lo soporta
//		//testCase.makeWebDriver();
//	}

	@Around("annotationTestPointcut() && atExecution()")
	public Object aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Around Before");
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		skipTestIfSuiteEnded(testCase, joinPoint);
		
		Object returnValue = null;
		InputParamsTM inputParams = testCase.getInputParamsSuite();
		if (!inputParams.isRemote()) {
			System.out.println("Prev Calling Service: " + testCase.getResult().hashCode());
			ClientRestApiTM client = new ClientRestApiTM();
			inputParams.setListTestCaseItems(Arrays.asList(testCase.getNameUnique()));
			inputParams.setAsyncExec("false");
			inputParams.setRemote("true");
			client.suiteRun(testCase.getInputParamsSuite());
			System.out.println("Next Calling Service: " + testCase.getResult().hashCode());
		} else {
			testCase.makeWebDriver();
			returnValue = joinPoint.proceed();
		}
		
		System.out.println("Around Afther");
		return returnValue;
	}

	private void skipTestIfSuiteEnded(TestCaseTM testCase, JoinPoint joinPoint) throws SkipException {
		if (testCase!=null) {
			TestMaker.skipTestsIfSuiteEnded(testCase.getSuiteParent());
		} else {
			if (!isFactoryMethod(joinPoint)) {
				throw new SkipException("TestCase removed");
			}
		}
	}
	
	private boolean isFactoryMethod(JoinPoint joinPoint) {
		String testMethod = joinPoint.getSignature().getName();
		Method[] listMethodsOfClass = joinPoint.getSignature().getDeclaringType().getDeclaredMethods();
		for (Method methodOfClass : listMethodsOfClass) {
			if (methodOfClass.getName().compareTo(testMethod)==0) {
				for (Annotation annotationMethod : methodOfClass.getAnnotations()) {
					if (annotationMethod.annotationType()==Factory.class) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
