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
    	StepTestMaker step = getLastStep();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint);
    	finishValidation(infoValidation, step, true);
    }
    
    @AfterReturning(
    	pointcut="annotationValidationPointcut() && atExecution()", 
    	returning="resultMethod")
    public void grabValidationAfter(JoinPoint joinPoint, Object resultMethod) throws Throwable {
    	StepTestMaker step = getLastStep();
    	InfoValidation infoValidation = InfoValidation.from(joinPoint, resultMethod);
    	finishValidation(infoValidation, step, false);
    }
    
    private StepTestMaker getLastStep() {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	return (testCase.getLastStep());
    }
    
    private void finishValidation(InfoValidation infoValidation, StepTestMaker step, boolean exceptionThrown) {
    	ChecksResult checksResult = infoValidation.getListResultValidation();
    	step.addChecksResult(checksResult);
    	if (exceptionThrown) {
    		checksResult.getStepParent().setNOKstateByDefault();
    	}
    	checksResult.checkValidations();
    	step.storeEvidencies();
    }
}
