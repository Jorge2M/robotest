package com.mng.testmaker.boundary.aspects.beforeafter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.SuiteTM;


@Aspect
public class BeforeTestAspect {

	@Pointcut("@annotation(org.testng.annotations.BeforeTest)")
	public void annotationBeforeTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationBeforeTestPointcut() && atExecution()")
	public Object aroundBeforeTest(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundBeforeTest(joinPoint);
	}
	
	private Object manageAroundBeforeTest(ProceedingJoinPoint joinPoint) throws Throwable {
		SuiteTM suite = SuiteTM.getSuiteCreatedInPresentThread();
		InputParamsTM inputParams = suite.getInputParams();
		if (inputParams.isTestExecutingInRemote()) {
			return joinPoint.proceed();
		}
		return null;
	}
}
