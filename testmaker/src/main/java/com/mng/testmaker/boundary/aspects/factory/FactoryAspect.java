package com.mng.testmaker.boundary.aspects.factory;

import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.suitetree.SuiteTM;


@Aspect
public class FactoryAspect {

	@Pointcut("@annotation(org.testng.annotations.Factory)")
	public void annotationFactoryPointcut() {}

	@Pointcut("execution(* *(..))")
	public void atExecution(){}

	@Around("annotationFactoryPointcut() && atExecution()")
	public Object aroundFactory(ProceedingJoinPoint joinPoint) throws Throwable {
		return manageAroundFactory(joinPoint);
	}
	

	private Object manageAroundFactory(ProceedingJoinPoint joinPoint) throws Throwable {
		SuiteTM suite = SuiteTM.getSuiteCreatedInPresentThread();
		InputParamsTM inputParams = suite.getInputParams();
		if (inputParams.isRemote()) {
			return manageAroundRemote(inputParams);
		}
		
		return joinPoint.proceed();
	}
	
	/**
	 * synchronized para evitar que un mismo TestObject sea procesado por varios @Factory
	 */
	private synchronized Object manageAroundRemote(InputParamsTM inputParams) {
		List<Object> listTests = new ArrayList<>();	
		if (inputParams.getTestObject()!=null) {
			listTests.add(
				SerializationUtils.deserialize(Base64.getDecoder().decode(inputParams.getTestObject())));
			inputParams.setTestObject(null);
		}
		return listTests.toArray(new Object[listTests.size()]);
	}
}
