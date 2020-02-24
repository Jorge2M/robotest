package com.mng.testmaker.boundary.aspects.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.testng.SkipException;
import org.testng.annotations.Factory;

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

//	@Before("annotationTestPointcut() && atExecution()")
//	public void before(JoinPoint joinPoint) {
//		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
//		skipTestIfSuiteEnded(testCase, joinPoint);
//		//TODO El COM010 no lo soporta
//		//testCase.makeWebDriver();
//	}

	@Around("annotationTestPointcut() && atExecution()")
	public Object aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		skipTestIfSuiteEnded(testCase, joinPoint);
		
		Object returnValue = null;
		InputParamsTM inputParams = testCase.getInputParamsSuite();
//		if (!inputParams.isRemote()) {
//			RemoteTest remoteTest = new RemoteTest();
//			remoteTest.execute(testCase, inputParams);
//		} else {
			//TODO El COM010 no lo soporta
			testCase.makeWebDriver();
			returnValue = joinPoint.proceed();
//		}
		
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
