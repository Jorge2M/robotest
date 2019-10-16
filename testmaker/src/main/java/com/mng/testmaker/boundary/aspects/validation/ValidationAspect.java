package com.mng.testmaker.boundary.aspects.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.service.TestMaker;


@Aspect
public class ValidationAspect {
	
    @Pointcut("@annotation(Validation)")
    public void annotationValidationPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationValidationPointcut()")
    public void before(JoinPoint joinPoint) {
    	TestMaker.skipTestsIfSuiteStopped();
    }
    
    @AfterThrowing(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	StepTestMaker step = testCase.getLastStepFinished();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint);
    	ChecksResult checksResult = infoValidation.getListResultValidation();
    	step.addChecksResult(checksResult);
    	checksResult.getStepParent().setNOKstateByDefault();
    	checksResult.checkValidations();
    }
    
    @AfterReturning(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	returning="resultMethod")
    public void grabValidationAfter(JoinPoint joinPoint, Object resultMethod) throws Throwable {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	StepTestMaker step = testCase.getLastStepFinished();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint, resultMethod);
    	ChecksResult checksResult = infoValidation.getListResultValidation();
    	step.addChecksResult(checksResult);
    	checksResult.checkValidations();
    }
}
