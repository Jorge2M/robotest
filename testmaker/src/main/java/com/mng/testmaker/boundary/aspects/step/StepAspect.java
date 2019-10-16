package com.mng.testmaker.boundary.aspects.step;

import java.sql.Date;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.mng.testmaker.annotations.MatcherWithMethodParams;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.State;


@Aspect
public class StepAspect {

    @Pointcut("@annotation(Step)")
    public void annotationStepPointcut() {}
    
    @Pointcut("execution(* *(..))")
    public void atExecution(){}
    
    @Before("annotationStepPointcut() && atExecution()")
    public void before(JoinPoint joinPoint) {
    	InfoStep infoStep = InfoStep.from(joinPoint);
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	TestMaker.skipTestsIfSuiteStopped(testCase.getSuiteParent());
    	StepTestMaker step = infoStep.getDatosStep();
    	testCase.addStep(step);
    	setInitDataStep(infoStep, joinPoint, step);
    }
    
    private void setInitDataStep(InfoStep infoStep, JoinPoint joinPoint, StepTestMaker datosStep) {
        MatcherWithMethodParams matcher = MatcherWithMethodParams.from(joinPoint);
        String stepDescription = infoStep.getStepAnnotation().description();
        String stepResExpected = infoStep.getStepAnnotation().expected();
        datosStep.setDescripcion(matcher.match(stepDescription)); 
        datosStep.setResExpected(matcher.match(stepResExpected));
        datosStep.setHoraInicio(new Date(System.currentTimeMillis()));
    }
    
    @AfterThrowing(
    	pointcut="annotationStepPointcut() && atExecution()", 
    	throwing="ex")
    public void doRecoveryActions(JoinPoint joinPoint, Throwable ex) {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	TestMaker.skipTestsIfSuiteStopped(testCase.getSuiteParent());
    	StepTestMaker currentStep = testCase.getCurrentStep();
    	currentStep.setResultSteps(State.Ok);
    	currentStep.setExcepExists(true); 
    	currentStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
    
    @AfterReturning(
    	pointcut="annotationStepPointcut() && atExecution()")
    public void grabValidationAfter(JoinPoint joinPoint) throws Throwable {
    	TestCaseTestMaker testCase = TestCaseTestMaker.getTestCaseInExecution();
    	TestMaker.skipTestsIfSuiteStopped(testCase.getSuiteParent());
    	StepTestMaker currentStep = testCase.getCurrentStep();
    	currentStep.setExcepExists(false); 
    	currentStep.setHoraFin(new Date(System.currentTimeMillis()));
    }
}
