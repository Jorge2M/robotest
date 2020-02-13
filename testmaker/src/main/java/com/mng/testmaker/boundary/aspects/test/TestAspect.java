package com.mng.testmaker.boundary.aspects.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.service.TestMaker;


@Aspect
public class TestAspect {

	@Pointcut("@annotation(org.testng.annotations.Test)")
	public void annotationTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Before("annotationTestPointcut() && atExecution()")
	public void before(JoinPoint joinPoint) {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		skipTestIfSuiteEnded(testCase, joinPoint);
		//TODO El COM010 no lo soporta
		//testCase.makeWebDriver();
	}

//	TODO se obtiene un problema en ListPagosEspana porque el @Test retorna un Object[] en lugar de un void
//	@Around("annotationTestPointcut() && atExecution()")
//	public void aroundTest(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.println("Around Before");
//		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
//		skipTestIfSuiteEnded(testCase, joinPoint);
//		ClientRestApiTM client = new ClientRestApiTM();
//		client.suiteRun((InputParamsBasic)testCase.getInputParamsSuite());
//		testCase.makeWebDriver();
//		joinPoint.proceed();
//		System.out.println("Around Afther");
//	}

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
