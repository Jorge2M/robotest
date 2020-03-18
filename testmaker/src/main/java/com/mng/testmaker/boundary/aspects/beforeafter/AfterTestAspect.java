package com.mng.testmaker.boundary.aspects.beforeafter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.SuiteTM;


@Aspect
public class AfterTestAspect {

	@Pointcut("@annotation(org.testng.annotations.AfterTest)")
	public void annotationAfterTestPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationAfterTestPointcut() && atExecution()")
	public Object aroundAfterTest(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundAfterTest(joinPoint);
	}
	
	private Object manageAroundAfterTest(ProceedingJoinPoint joinPoint) throws Throwable {
		SuiteTM suite = SuiteTM.getSuiteCreatedInPresentThread();
		InputParamsTM inputParams = suite.getInputParams();
		if (inputParams.isTestExecutingInRemote()) {
			return joinPoint.proceed();
		}
		return null;
	}
}
