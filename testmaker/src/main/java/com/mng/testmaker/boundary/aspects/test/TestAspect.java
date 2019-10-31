package com.mng.testmaker.boundary.aspects.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.testng.SkipException;

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
    	if (testCase!=null) {
    		TestMaker.skipTestsIfSuiteStopped(testCase.getSuiteParent());
    	} else {
    		throw new SkipException("TestCase removed");
    	}
    }
}
